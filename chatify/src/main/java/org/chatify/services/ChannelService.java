package org.chatify.services;

import jakarta.transaction.Transactional;
import org.chatify.models.dtos.ChannelDTO;
import org.chatify.models.entities.Channel;
import org.chatify.models.entities.ChannelUser;
import org.chatify.models.entities.User;
import org.chatify.models.requests.*;
import org.chatify.repositories.ChannelRepository;
import org.chatify.repositories.ChannelUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelUserRepository channelUserRepository;
    private final UserService userService;
    private final RolesService rolesService;

    public ChannelService(
            ChannelRepository channelRepository,
            ChannelUserRepository channelUserRepository,
            UserService userService,
            RolesService rolesService) {
        this.channelRepository = channelRepository;
        this.channelUserRepository = channelUserRepository;
        this.userService = userService;
        this.rolesService = rolesService;
    }

    public Channel getChannelById(int id){
        return this.channelRepository.findByIdAndIsActiveTrue(id);
    }

    public ArrayList<ChannelDTO> getDMChannelsByUserId(String userId) {
        var userIdInt = Integer.parseInt(userId);
        return this.channelRepository.findDirectMessageChannelsByUserId(userIdInt);
    }

    public ArrayList<ChannelDTO> getGroupChannelsByUserId(String userId) {
        var userIdInt = Integer.parseInt(userId);
        return this.channelRepository.findGroupChannelsByUserId(userIdInt);
    }

    @Transactional
    public Channel createChannel(CreateChannelRequest request) {
        var user = this.userService.getUserById(request.getUserId());
        var ownerRole = this.rolesService.getRole("Owner");

        Channel channel = new Channel();
        channel.setName(request.getChannelName());
        channel.setDirectMessage(request.getDM());
        channel.setActive(true);

        var savedChannel = this.channelRepository.save(channel);

        ChannelUser cu = new ChannelUser();
        cu.setUser(user);
        cu.setRole(ownerRole);
        cu.setChannel(savedChannel);

        this.channelUserRepository.save(cu);

        return savedChannel;
    }

    @Transactional
    public void createDmChannel(User firstUser, User secondUser) {
        var channel = new Channel();
        channel.setName(String.format("%s-%s DM", firstUser.getUsername(), secondUser.getUsername()));
        channel.setDirectMessage(true);
        channel.setActive(true);

        var createdChannel = this.channelRepository.save(channel);

        var ownerRole = this.rolesService.getRole("Owner");
        ChannelUser cuOne = new ChannelUser();
        cuOne.setUser(firstUser);
        cuOne.setRole(ownerRole);
        cuOne.setChannel(createdChannel);

        ChannelUser cuTwo = new ChannelUser();
        cuTwo.setUser(secondUser);
        cuTwo.setRole(ownerRole);
        cuTwo.setChannel(createdChannel);

        this.channelUserRepository.save(cuOne);
        this.channelUserRepository.save(cuTwo);
    }

    @Transactional
    public void deleteChannel(DeleteChannelRequest request){
        var user = this.userService.getUserById(request.getUserId());

        var channel = this.channelRepository.findByIdAndIsActiveTrue(request.getChannelId());
        if (channel == null){
            throw new IllegalArgumentException("Channel not found!");
        }

        var channelUser = getChannelUser(user.getId(), channel.getId(), "User");

        var ownerRole = this.rolesService.getRole("Owner");
        if (channelUser.getRole() != ownerRole){
            throw new IllegalArgumentException("Only owner can delete channel!");
        }

        channel.setActive(false);
        this.channelRepository.save(channel);
    }

    @Transactional
    public void upgradeToAdmin(AdminUpgradeRequest request) {
        var initiator = this.userService.getUserById(request.getInitiatorId());
        var targetUser = this.userService.getUserById(request.getTargetId());

        var channel = this.channelRepository.findByIdAndIsActiveTrue(request.getChannelId());
        if (channel == null){
            throw new IllegalArgumentException("Channel not found!");
        }

        var initiatorChannelUser = getChannelUser(initiator.getId(), channel.getId(), "Initiator");

        var ownerRole = this.rolesService.getRole("Owner");
        if (initiatorChannelUser.getRole() != ownerRole){
            throw new IllegalArgumentException("Only owner can give admin rights!");
        }

        var targetChannelUser = getChannelUser(targetUser.getId(), channel.getId(), "Target");

        var amdinRole = this.rolesService.getRole("Admin");

        targetChannelUser.setRole(amdinRole);
        channelUserRepository.save(targetChannelUser);
    }

    @Transactional
    public void addUserToChannel(int channelId, AddChannelMemberRequest request) {
        var initiator = this.userService.getUserById(request.getInitiatorId());
        var newUser = this.userService.getUserById(request.getNewUserId());

        var channel = this.channelRepository.findByIdAndIsActiveTrue(channelId);
        if (channel == null){
            throw new IllegalArgumentException("Channel not found!");
        }

        var initiatorChannelUser = getChannelUser(initiator.getId(), channel.getId(), "Initiator");

        var ownerRole = this.rolesService.getRole("Owner");
        var adminRole = this.rolesService.getRole("Admin");

        if (!initiatorChannelUser.getRole().equals(ownerRole) && !initiatorChannelUser.getRole().equals(adminRole)) {
            throw new IllegalArgumentException("Only owners and admins can add users");
        }

        var newUserChannelUser = this.channelUserRepository.findByUserIdAndChannelId(newUser.getId(), channel.getId());
        if (newUserChannelUser != null){
            throw new IllegalArgumentException("User is already in the channel!");
        }

        var memberRole = this.rolesService.getRole("Member");

        var newChannelUser = new ChannelUser();
        newChannelUser.setUser(newUser);
        newChannelUser.setChannel(channel);
        newChannelUser.setRole(memberRole);

        channelUserRepository.save(newChannelUser);
    }

    @Transactional
    public ChannelDTO renameChannel(RenameChannelRequest request){
        var user = this.userService.getUserById(request.getUserId());

        var channel = this.channelRepository.findByIdAndIsActiveTrue(request.getChannelId());
        if (channel == null){
            throw new IllegalArgumentException("Channel not found!");
        }

        var channelUser = getChannelUser(user.getId(), channel.getId(), "User");

        var memberRole = this.rolesService.getRole("Member");
        if (channelUser.getRole() == memberRole){
            throw new IllegalArgumentException("Members can't rename channels!");
        }

        channel.setName(request.getChannelName());
        var updatedChannel = this.channelRepository.save(channel);
        return new ChannelDTO(updatedChannel.getId(), updatedChannel.getName());
    }

    @Transactional
    public void removeMemberFromChannel(int channelId, RemoveFromChannelRequest request) {
        var initiator = this.userService.getUserById(request.getInitiatorId());
        var target = this.userService.getUserById(request.getTargetId());

        var channel = this.channelRepository.findByIdAndIsActiveTrue(channelId);
        if (channel == null){
            throw new IllegalArgumentException("Channel not found!");
        }

        var initiatorChannelUser = getChannelUser(initiator.getId(), channel.getId(), "Initiator");
        var ownerRole = this.rolesService.getRole("Owner");
        if (initiatorChannelUser.getRole() != ownerRole) {
            throw new IllegalArgumentException("Only owners can remove from channels!");
        }

        var targetChannelUser = getChannelUser(target.getId(), channel.getId(), "Target");
        var memberRole = this.rolesService.getRole("Member");
        if (targetChannelUser.getRole() != memberRole){
            throw new IllegalArgumentException("Only members can be removed from channels!");
        }

        channelUserRepository.delete(targetChannelUser);
    }

    private ChannelUser getChannelUser(int userId, int channelId, String userName) {
        var channelUser = this.channelUserRepository.findByUserIdAndChannelId(userId, channelId);
        if (channelUser == null){
            throw new IllegalArgumentException(String.format("%s is not in the channel!", userName));
        }

        return channelUser;
    }
}