package com.example.lostnexus.models;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class Message {
    private String messageId, message, senderId, imageUrl;
    private String  timestamp;
    private int feeling = -1;

    public Message(String messageId, String message, String senderId, String  timestamp) {
        this.messageId = messageId;
        this.message = message;
        this.senderId = senderId;
   this.timestamp = timestamp;
    }

    public Message() {
    }



    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }





    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
@BindingAdapter("date")
    public static void getDate(TextView textView , String timestamp){
String[] s = timestamp.split("\\s+");
textView.setText(s[0]);
}

    @BindingAdapter("time")
    public static void getTime(TextView textView , String timestamp){
        String[] s = timestamp.split("\\s+");
        textView.setText(s[1].substring(0 , s[1].length()-3));
    }
}
