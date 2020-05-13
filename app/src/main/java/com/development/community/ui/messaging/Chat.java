package com.development.community.ui.messaging;

import androidx.annotation.NonNull;

public class Chat {
    public String senderUID;
    public String receiverUID;
    public String senderUsername;
    public String receiverUsername;
    public String message;

    public Chat(){
        senderUID = "";
        senderUsername = "";
        receiverUID = "";
        receiverUsername = "";
        message = "";
    }
    public Chat(String senderUID, String senderUsername, String receiverUID, String receiverUsername, String message){
        this.senderUID = senderUID;
        this.senderUsername = senderUsername;
        this.receiverUID = receiverUID;
        this.receiverUsername = receiverUsername;
        this.message = message;
    }



    @Override @NonNull
    public String toString(){
        return message + "; Sent by " + senderUID + " to " + receiverUID;
    }


    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderUID(){
        return senderUID;
    }
    public void setSenderUID(String senderUID){
        this.senderUID = senderUID;
    }

    public String getReceiverUID(){
        return receiverUID;
    }

    public void setReceiverUID(String receiverUID){
        this.receiverUID = receiverUID;
    }}
