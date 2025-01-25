package org.chatify.models.requests;

public class AddFriendRequest {
    private int senderId;
    private int receiverId;

    public AddFriendRequest() {}

    public AddFriendRequest(int senderId, int receiverId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
