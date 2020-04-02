package com.trinarr.phonegameconcept.UI;

public class ListItemMessage {
    public static final int TYPE_MY = 1;
    public static final int TYPE_PERSON = 2;

    public static final int ACTION_STOP = 0;
    public static final int ACTION_MESSAGE = 1;
    public static final int ACTION_NODE = 2;

    public String message;

    public int actionType, actionID;
    public int type, messageID;
    public int attachmentID = -1, attachmentType = -1;

    public ListItemMessage() {
        this.messageID = -1;
    }
}