package org.chatify.models.requests;

public class CreateChannelRequest {
    private int userId;
    private String channelName;

    public CreateChannelRequest() { }

    public CreateChannelRequest(int userId, String channelName) {
        this.userId = userId;
        this.channelName = channelName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
