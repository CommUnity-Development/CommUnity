package com.development.community;

import androidx.annotation.NonNull;

/**
 * A class that stores data for messages that are sent/received
 */
public class Message {
    private String senderUID;
    private String receiverUID;
    private String senderUsername;
    public String receiverUsername;
    private String message;

    /**
     * Default Constructor, initializes all strings to empty
     */
    public Message(){
        senderUID = "";
        senderUsername = "";
        receiverUID = "";
        receiverUsername = "";
        message = "";
    }

    /**
     * Parameter Constructor
     * @param senderUID The User ID of the user who sent the message
     * @param senderUsername The Username of the user who sent the message
     * @param receiverUID The User ID of the user who received the message
     * @param receiverUsername The Username of the user who received the message
     * @param message The message
     */
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
