package com.trinarr.phonegameconcept.UI;

public class ListItemMessage {
    public static final int TYPE_MY = 1;
    public static final int TYPE_PERSON = 2;

    public static final int ACTION_NEXT_MESSAGE = 1;
    public static final int ACTION_NEXT_NODE = 2;

    public String name, message;

    public int type, actionType, actionID, messageID;

    public ListItemMessage() {
        this.messageID = -1;
    }
}