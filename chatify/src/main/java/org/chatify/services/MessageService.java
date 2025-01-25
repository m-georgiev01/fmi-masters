package org.chatify.services;

import jakarta.transaction.Transactional;
import org.chatify.models.dtos.ChannelDTO;
import org.chatify.models.dtos.MessageDTO;
import org.chatify.models.dtos.UserDTO;
import org.chatify.models.entities.Message;
import org.chatify.models.requests.AddMessageRequest;
import org.chatify.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelService channelService;
    private final UserService userService;

    public MessageService(
            MessageRepository messageRepository,
            ChannelService channelService,
            UserService userService) {
        this.messageRepository = messageRepository;
        this.channelService = channelService;
        this.userService = userService;
    }

    public ArrayList<MessageDTO> getMessages(int channelId) {
        var channel = this.channelService.getChannelById(channelId);
        if (channel == null) {
            throw new IllegalArgumentException("No channel with id " + channelId + " found");
        }

        return this.messageRepository.findByChannelIdOrderBySentAtAsc(channelId)
                .stream()
                .map(msg -> new MessageDTO(
                        msg.getId(),
                        msg.getContent(),
                        new UserDTO(msg.getSender().getId(), msg.getSender().getUsername(), msg.getSender().isActive()),
                        new ChannelDTO(msg.getChannel().getId(), msg.getChannel().getName()),
                        msg.getSentAt()
                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public MessageDTO sendMessage(int channelId, AddMessageRequest request) {
        var sender = this.userService.getUserById(request.getSenderId());

        var channel = this.channelService.getChannelById(channelId);
        if (channel == null) {
            throw new IllegalArgumentException("Channel not found!");
        }

        var message = new Message();
        message.setContent(request.getContent());
        message.setSender(sender);
        message.setChannel(channel);

        Message savedMessage = messageRepository.save(message);

        return new MessageDTO(
                savedMessage.getId(),
                savedMessage.getContent(),
                new UserDTO(savedMessage.getSender().getId(), savedMessage.getSender().getUsername(), savedMessage.getSender().isActive()),
                new ChannelDTO(savedMessage.getChannel().getId(), savedMessage.getChannel().getName()),
                savedMessage.getSentAt()
        );
    }
}
