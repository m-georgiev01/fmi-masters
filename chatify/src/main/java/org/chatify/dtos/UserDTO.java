package org.chatify.dtos;

public class UserDTO {
    private int id;
    private String username;
    private boolean isActive;

    public UserDTO(int id, String username, boolean isActive) {
        this.id = id;
        this.username = username;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
