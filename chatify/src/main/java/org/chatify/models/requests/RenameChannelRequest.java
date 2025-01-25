package org.chatify.models.requests;

public class RenameChannelRequest {
    private int channelId;
    private int userId;
    private String channelName;

    public RenameChannelRequest() {}

    public RenameChannelRequest(int channelId, int userId, String channelName) {
        this.channelId = channelId;
        this.userId = userId;
        this.channelName = channelName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
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
