package org.chatify.models.requests;

public class DeleteChannelRequest {
    private int channelId;
    private int userId;

    public DeleteChannelRequest() {}

    public DeleteChannelRequest(int channelId, int userId) {
        this.channelId = channelId;
        this.userId = userId;
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
}
