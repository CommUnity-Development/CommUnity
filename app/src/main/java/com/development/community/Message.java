package com.development.community;

import androidx.annotation.NonNull;

public class Message {
    private String senderUID;
    private String receiverUID;
    private String senderUsername;
    public String receiverUsername;
    private String message;

    public Message(){
        senderUID = "";
        senderUsername = "";
        receiverUID = "";
        receiverUsername = "";
        message = "";
    }
    public Message(String senderUID, String senderUsername, String receiverUID, String receiverUsername, String message){
        this.senderUID = senderUID;
        this.senderUsername = senderUsername;
        this.receiverUID = receiverUID;
        this.receiverUsername = receiverUsername;
        this.message = message;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getReceiverUID() {
        return receiverUID;
    }

    public void setReceiverUID(String receiverUID) {
        this.receiverUID = receiverUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override @NonNull
    public String toString(){
        return message + "Sent by " + senderUID + " to " + receiverUID;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername(){
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername){
        this.receiverUsername = receiverUsername;
    }
}
