package org.chatify.models.requests;

public class AddMessageRequest {
    private int senderId;
    private String content;

    public AddMessageRequest() {}

    public AddMessageRequest(int senderId, String content) {
        this.senderId = senderId;
        this.content = content;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
