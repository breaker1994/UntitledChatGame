package com.trinarr.phonegameconcept.UI;

public class ListItemMessage {
    public static final int TYPE_MY = 1;
    public static final int TYPE_PERSON = 2;

    public String name, message;

    public int type;

    public ListItemMessage(String name, String message, int type) {
        this.name = name;
        this.message = message;
        this.type = type;
    }
}
