package com.trinarr.phonegameconcept.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.trinarr.phonegameconcept.R;
import com.trinarr.phonegameconcept.UI.Database.DatabaseGame;
import com.trinarr.phonegameconcept.UI.Database.DatabaseSaveHelper;

import java.util.ArrayList;

public class ScreenMain extends AppCompatActivity implements ListAdapterChats.OnItemClickListener{
    private ArrayList<ListItemChats> graphlistArray = new ArrayList<>();
    ListAdapterChats graphListAdapter;

    private Cursor chats = null;
    private DatabaseGame db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_tabs);

        BlockHeader blockHeader = new BlockHeader(this);
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.addView(blockHeader, 0);

        blockHeader.setHeaderText("Чаты");
        blockHeader.hideAvatar();

        RecyclerView graphList = findViewById(R.id.recycleView);
        graphListAdapter = new ListAdapterChats(this, graphList, graphlistArray, this);

        graphList.addOnItemTouchListener(graphListAdapter);
        graphList.setAdapter(graphListAdapter);

        graphList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        graphList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getChatsList();
    }

    @Override public void onItemClick(View view, final int position) {
        ViewCompat.postOnAnimationDelayed(view, new Runnable() {
            @Override
            public void run() {
                LogManager.log("onItemClick  " + position, this.getClass());

                ListItemChats item = graphlistArray.get(position);

                Intent intent = new Intent(ScreenMain.this, ScreenChat.class);
                intent.putExtra("chatID", item.chatID);
                intent.putExtra("peopleID", item.peopleID);
                startActivity(intent);
            }
        }, 50);
    }

    @Override public void onLongItemClick(View view, int position) {
        LogManager.log("onLongItemClick  "+position, this.getClass());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.toolbar_settings) {
            //Intent intent = new Intent(ScreenGeofence.this, ScreenGeozone.class);
            //startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_toolbar, menu);

        MenuItem menuItem = menu.findItem(R.id.toolbar_settings);
        menuItem.setVisible(true);

        return true;
    }

    private void getChatsList() {
        LogManager.log("getChatsList", this.getClass());
        graphlistArray.clear();

        db = new DatabaseGame(this);
        chats = db.getChats();

        DatabaseSaveHelper dbSave;
        if (chats.moveToFirst()) {
            do {
                //int lastMessageID = chats.getInt(chats.getColumnIndex("last_message_ID"));
                int peopleID = chats.getInt(chats.getColumnIndex("people_ID"));

                ListItemChats item = new ListItemChats();
                item.chatID = chats.getInt(chats.getColumnIndex("ID"));
                item.peopleID = peopleID;
                item.title = db.getPeople(peopleID);

                dbSave = new DatabaseSaveHelper(this, "messages_"+item.chatID);
                ArrayList<ListItemMessage> messages = dbSave.getAllMessages();
                if(messages.size()>0) {
                    ListItemMessage listItemMessage = messages.get(messages.size()-1);
                    if(listItemMessage.type == ListItemMessage.TYPE_MY) {
                        item.description = "Вы: "+listItemMessage.message;
                    }
                    else {
                        item.description = listItemMessage.message;
                    }
                }
                else {
                    int lastMessageID = chats.getInt(chats.getColumnIndex("first_message_ID"));

                    ListItemMessage itemMessage = db.getMessage(lastMessageID);
                    item.description = itemMessage.message;
                }

                graphlistArray.add(item);
            }
            while (chats.moveToNext());
        }

        graphListAdapter.notifyDataSetChanged();
        chats.close();
    }

    @Override
    public void onResume() {
        LogManager.log("onResume", this.getClass());
        super.onResume();

        getChatsList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(chats != null) {
            chats.close();
        }

        if(db != null) {
            db.close();
        }
    }
}
