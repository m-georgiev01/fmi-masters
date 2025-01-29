package org.chatify.services;

import jakarta.transaction.Transactional;
import org.chatify.models.dtos.ChannelDTO;
import org.chatify.models.entities.Friend;
import org.chatify.models.entities.User;
import org.chatify.models.requests.AddFriendRequest;
import org.chatify.repositories.FriendRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserService userService;
    private final ChannelService channelService;

    public FriendService(
            FriendRepository friendRepository,
            UserService userService,
            ChannelService channelService) {
        this.friendRepository = friendRepository;
        this.userService = userService;
        this.channelService = channelService;
    }

    @Transactional
    public ChannelDTO addFriend(AddFriendRequest request) {
        var sender = this.userService.getUserById(request.getSenderId());
        var receiver = this.userService.getUserById(request.getReceiverId());

        if (checkAlreadyFriend(sender, receiver)) {
            throw new IllegalArgumentException("Users are already friends");
        }

        var friendship = new Friend();
        friendship.setFirstUser(sender);
        friendship.setSecondUser(receiver);

        this.friendRepository.save(friendship);
        return this.channelService.createDmChannel(sender, receiver);
    }

    private boolean checkAlreadyFriend(User sender, User receiver) {
        return this.friendRepository.existsByFirstUserAndSecondUser(sender, receiver) ||
                this.friendRepository.existsByFirstUserAndSecondUser(receiver, sender);
    }
}
