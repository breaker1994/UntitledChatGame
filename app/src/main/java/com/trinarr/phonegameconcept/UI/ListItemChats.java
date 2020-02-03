package com.trinarr.phonegameconcept.UI;

public class ListItemChats {
    public String title, description;

    public int chatID, peopleID;

    ListItemChats(int chatID, int peopleID, String title, String description) {
        this.chatID = chatID;
        this.peopleID = peopleID;

        this.title = title;
        this.description = description;
    }
}
