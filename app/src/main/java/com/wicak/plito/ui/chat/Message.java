package com.wicak.plito.ui.chat;

public class Message {

    private String sender;
    private String senderName;
    private String receiver;
    private String message;
    private String imageURL;




    public Message(String sender, String senderName, String receiver, String message) {
        this.sender = sender;
        this.senderName = senderName;
        this.receiver = receiver;
        this.message = message;
    }

    public Message(){

    }


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
