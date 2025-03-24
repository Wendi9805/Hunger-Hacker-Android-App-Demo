package com.example.groupproject.p2pFunction;

import java.util.Date;

/**
 * This Class is the instance of our ChatMessage,
 * it contains all the attributes we need,
 * all the attributes also match the names in firebase database
 *
 * @author ${Wendi Fan}
 */

public class ChatMessage {
    public String senderID, receiverId, message, dateTime;
    public Date dateObject;
}
