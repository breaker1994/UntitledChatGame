package com.trinarr.phonegameconcept.UI.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.trinarr.phonegameconcept.UI.LogManager;

public class DatabaseGame extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "gameDB.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseGame(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public Cursor getChats() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "chats";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, null,null,null,null, null,null);

        c.moveToFirst();
        return c;
    }

    public String getMessage(int lastMessageID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "messages";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, null,"ID" + " = " + lastMessageID,null,null, null,null);

        String answer = null;
        if(c.getCount() > 0) {
            c.moveToFirst();
            answer = c.getString(c.getColumnIndex("text"));

            LogManager.log("answer  " + answer, this.getClass());
        }

        c.close();
        return answer;
    }

    public int getFirstMessageID(int chatID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "chats";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, null,"ID" + " = " + chatID,null,null, null,null);

        int firstMessageID = -1;

        if(c.getCount() > 0) {
            c.moveToFirst();
            firstMessageID = c.getInt(c.getColumnIndex("first_message_ID"));

            LogManager.log("firstMessageID  " + firstMessageID, this.getClass());
        }

        c.close();
        return firstMessageID;
    }

    public String getPeople(int peopleID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "people";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, null,"ID" + " = " + peopleID,null,null, null,null);

        String name = null;
        if(c.getCount() > 0) {
            c.moveToFirst();
            name = c.getString(c.getColumnIndex("name"));

            LogManager.log("name  " + name, this.getClass());
        }

        c.close();
        return name;
    }
}