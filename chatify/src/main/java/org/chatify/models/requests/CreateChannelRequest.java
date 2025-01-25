package org.chatify.models.requests;

public class CreateChannelRequest {
    private int userId;
    private String channelName;
    private boolean isDM;

    public CreateChannelRequest() { }

    public CreateChannelRequest(int userId, String channelName, boolean isDM) {
        this.userId = userId;
        this.channelName = channelName;
        this.isDM = isDM;
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

    public boolean getDM() {
        return isDM;
    }

    public void setDM(boolean DM) {
        isDM = DM;
    }
}
