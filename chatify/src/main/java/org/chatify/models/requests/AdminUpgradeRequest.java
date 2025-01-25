package org.chatify.models.requests;

public class AdminUpgradeRequest {
    private int initiatorId;
    private int targetId;
    private int channelId;

    public AdminUpgradeRequest() {}

    public AdminUpgradeRequest(int initiatorId, int targetId, int channelId) {
        this.initiatorId = initiatorId;
        this.targetId = targetId;
        this.channelId = channelId;
    }

    public int getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(int initiatorId) {
        this.initiatorId = initiatorId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
}
