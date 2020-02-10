package com.trinarr.phonegameconcept.UI.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.trinarr.phonegameconcept.UI.ListItemAnswer;
import com.trinarr.phonegameconcept.UI.ListItemMessage;
import com.trinarr.phonegameconcept.UI.LogManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

    public ArrayList<ListItemAnswer> getAnswers(int nodeID) {
        ArrayList<ListItemAnswer> answers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "nodes";
        qb.setTables(sqlTables);

        Cursor c = qb.query(db, null,"ID" + " = " + nodeID,null,null, null,null);
        if(c.getCount() == 0) {
            return answers;
        }

        c.moveToFirst();
        String[] sAnswersIDs = c.getString(c.getColumnIndex("answer_IDs")).split(",");
        LogManager.log("sAnswersIDs " + Arrays.toString(sAnswersIDs), this.getClass());

        for(int i=0; i<sAnswersIDs.length; ++i) {
            int answerID = Integer.parseInt(sAnswersIDs[i]);

            ListItemAnswer itemAnswer = getAnswer(answerID);
            LogManager.log("itemAnswer " + itemAnswer.message, this.getClass());
            answers.add(itemAnswer);
        }

        c.close();
        return answers;
    }

    public ListItemAnswer getAnswer(int answerID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "answers";
        qb.setTables(sqlTables);

        Cursor c = qb.query(db, null,"ID" + " = " + answerID,null,null, null,null);

        ListItemAnswer item = new ListItemAnswer();

        if(c.getCount() == 0) {
            return item;
        }

        c.moveToFirst();

        item.message = c.getString(c.getColumnIndex("text"));
        item.actionID = c.getInt(c.getColumnIndex("next_message_ID"));

        c.close();
        return item;
    }

    public ListItemMessage getMessage(int lastMessageID) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String sqlTables = "messages";
        qb.setTables(sqlTables);

        Cursor c = qb.query(db, null,"ID" + " = " + lastMessageID,null,null, null,null);

        ListItemMessage item = new ListItemMessage();

        if(c.getCount() > 0) {
            c.moveToFirst();

            item.message = c.getString(c.getColumnIndex("text"));
            item.actionType = c.getInt(c.getColumnIndex("action_type"));
            item.actionID = c.getInt(c.getColumnIndex("action_ID"));
            item.messageID = lastMessageID;
        }

        c.close();
        return item;
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