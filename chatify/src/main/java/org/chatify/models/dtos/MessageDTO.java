package org.chatify.models.dtos;

import java.time.LocalDateTime;

public class MessageDTO {
    private int id;
    private String content;
    private UserDTO sender;
    private ChannelDTO channel;
    private LocalDateTime timestamp;

    public MessageDTO() {}

    public MessageDTO(int id, String content, UserDTO sender, ChannelDTO channel, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.sender = sender;
        this.channel = channel;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
