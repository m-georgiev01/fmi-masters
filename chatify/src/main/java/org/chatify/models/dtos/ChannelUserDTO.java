package org.chatify.models.dtos;

import org.chatify.models.entities.Role;

public class ChannelUserDTO {
    private int id;
    private ChannelDTO channel;
    private UserDTO user;
    private Role role;

    public ChannelUserDTO() {}

    public ChannelUserDTO(int id, ChannelDTO channel, UserDTO user, Role role) {
        this.id = id;
        this.channel = channel;
        this.user = user;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChannelDTO getChannel() {
        return channel;
    }

    public void setChannel(ChannelDTO channel) {
        this.channel = channel;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
