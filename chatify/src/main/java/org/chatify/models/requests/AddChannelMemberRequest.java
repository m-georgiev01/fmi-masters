package org.chatify.models.requests;

public class AddChannelMemberRequest {
    private int initiatorId;
    private int newUserId;

    public AddChannelMemberRequest() {}

    public AddChannelMemberRequest(int initiatorId, int newUserId) {
        this.initiatorId = initiatorId;
        this.newUserId = newUserId;
    }

    public int getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(int initiatorId) {
        this.initiatorId = initiatorId;
    }

    public int getNewUserId() {
        return newUserId;
    }

    public void setNewUserId(int newUserId) {
        this.newUserId = newUserId;
    }
}