package com.development.community.ui.messaging;

import androidx.annotation.NonNull;

/**
 * A class that stores information about chats
 */
public class Chat {
    public String senderUID;
    public String receiverUID;
    public String senderUsername;
    public String receiverUsername;
    public String message;


    /**
     * the default constructor with no data passed
     */
    public Chat(){
        senderUID = "";
        senderUsername = "";
        receiverUID = "";
        receiverUsername = "";
        message = "";
    }

    /**
     * The constructor that passes in all data required
     * @param senderUID the User ID of the sender
     * @param senderUsername the name of the sender
     * @param receiverUID the User ID of the receiver
     * @param receiverUsername the name of the receiver
     * @param message the message desired to be sent
     */
    public Chat(String senderUID, String senderUsername, String receiverUID, String receiverUsername, String message){
        this.senderUID = senderUID;
        this.senderUsername = senderUsername;
        this.receiverUID = receiverUID;
        this.receiverUsername = receiverUsername;
        this.message = message;
    }


    /**
     * the toString of the message, makes it easier to debug while logging
     * @return the String form of the message
     */
    @Override @NonNull
    public String toString(){
        return message + "; Sent by " + senderUID + " to " + receiverUID;
    }

    /**
     * Getter for the receiver name
     * @return the name of the receiver
     */
    public String getReceiverUsername() {
        return receiverUsername;
    }

    /**
     * Setter for the receiver name
     * @param receiverUsername
     */
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
