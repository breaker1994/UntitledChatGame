package com.trinarr.phonegameconcept.UI.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trinarr.phonegameconcept.UI.ListItemMessage;

import java.util.ArrayList;

public class DatabaseSaveHelper extends SQLiteOpenHelper {
    private String databaseName;

    // Database Version
    private static final int DATABASE_VERSION = 1;


    public DatabaseSaveHelper(Context context, String databaseName) {
        super(context, databaseName, null, DATABASE_VERSION);

        this.databaseName = databaseName;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
                String CREATE_TABLE =
                "CREATE TABLE " + databaseName + "("
                        + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "message_id" + " INTEGER NOT NULL, "
                        + "text" + " TEXT, "
                        + "name" + " TEXT, "
                        + "type" + " INTEGER NOT NULL, "
                        + "action_type" + " INTEGER NOT NULL, "
                        + "action_id" + " INTEGER NOT NULL, "
                        + "attachment_id" + " INTEGER, "
                        + "attachment_type" + " INTEGER"
                        + ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + databaseName);

        onCreate(db);
    }

    public void addMessage(ListItemMessage message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("text", message.message);
        values.put("type", message.type);
        values.put("action_type", message.actionType);
        values.put("action_id", message.actionID);
        values.put("message_id", message.messageID);
        if(message.attachmentID != -1) {
            values.put("attachment_id", message.attachmentID);
        }
        if(message.attachmentType != -1) {
            values.put("attachment_type", message.attachmentType);
        }

        db.insert(databaseName, null, values);
        db.close();
    }

    public ArrayList<ListItemMessage> getAllMessages() {
        ArrayList<ListItemMessage> messages = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + databaseName + " ORDER BY " +
                "ID" + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ListItemMessage message = new ListItemMessage();
                message.message = cursor.getString(cursor.getColumnIndex("text"));
                message.type = cursor.getInt(cursor.getColumnIndex("type"));

                message.actionType = cursor.getInt(cursor.getColumnIndex("action_type"));
                message.actionID = cursor.getInt(cursor.getColumnIndex("action_id"));

                message.messageID = cursor.getInt(cursor.getColumnIndex("message_id"));

                int index = cursor.getColumnIndex("attachment_id");
                if(!cursor.isNull(index)) {
                    message.attachmentID = cursor.getInt(index);
                }

                index = cursor.getColumnIndex("attachment_type");
                if(!cursor.isNull(index)) {
                    message.attachmentType = cursor.getInt(index);
                }

                messages.add(message);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return messages;
    }
}