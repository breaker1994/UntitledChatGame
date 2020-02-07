package com.trinarr.phonegameconcept.UI.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.trinarr.phonegameconcept.UI.ListItemMessage;

import java.util.ArrayList;
import java.util.List;

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
                        + "text" + " TEXT, "
                        + "type" + " INTEGER NOT NULL, "
                        + "message_ID" + " INTEGER NOT NULL"
                        + ")";

        // create notes table
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + databaseName);

        // Create tables again
        onCreate(db);
    }

    public void addMessage(ListItemMessage message, int messageID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("text", message.message);
        values.put("name", message.name);
        values.put("type", message.type);
        values.put("message_ID", messageID);

        db.insert(databaseName, null, values);
        db.close();
    }

    public ArrayList<ListItemMessage> getAllMessages() {
        ArrayList<ListItemMessage> messages = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + databaseName + " ORDER BY " +
                "ID" + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndex("text"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));

                ListItemMessage message = new ListItemMessage(name, text, type);

                messages.add(message);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return messages;
    }
}