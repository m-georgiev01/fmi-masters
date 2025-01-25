package org.chatify.models.requests;

public class RemoveFromChannelRequest {
    private int initiatorId;
    private int targetId;

    public RemoveFromChannelRequest() {}

    public RemoveFromChannelRequest(int initiatorId, int targetId) {
        this.initiatorId = initiatorId;
        this.targetId = targetId;
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
}
