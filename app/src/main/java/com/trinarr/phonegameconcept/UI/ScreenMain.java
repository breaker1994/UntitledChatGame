package com.trinarr.phonegameconcept.UI;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;

public class ScreenMain extends AppCompatActivity implements ListAdapterChats.OnItemClickListener{
    private ArrayList<ListItemChats> graphlistArray = new ArrayList<>();

    private Cursor chats = null;
    private GameDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_tabs);

        BlockHeader blockHeader = new BlockHeader(this);
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.addView(blockHeader, 0);

        blockHeader.setHeaderText("Чаты");

        RecyclerView graphList = findViewById(R.id.recycleView);
        ListAdapterChats graphListAdapter = new ListAdapterChats(this, graphList, graphlistArray, this);

        graphList.addOnItemTouchListener(graphListAdapter);
        graphList.setAdapter(graphListAdapter);

        graphList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        graphList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getChatsList();
    }

    @Override public void onItemClick(View view, int position) {
        LogManager.log("onItemClick  "+position, this.getClass());

        ListItemChats item = graphlistArray.get(position);

        Intent intent = new Intent(this, ScreenChat.class);
        intent.putExtra("chatID", item.chatID);
        intent.putExtra("peopleID", item.peopleID);
        startActivity(intent);
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
        db = new GameDatabase(this);
        chats = db.getChats();

        if(chats.getCount()>0) {
            do {
                int lastMessageID = chats.getInt(chats.getColumnIndex("last_message_ID"));
                int peopleID = chats.getInt(chats.getColumnIndex("people_ID"));
                int chatID = chats.getInt(chats.getColumnIndex("ID"));

                String name = db.getPeople(peopleID);
                String lastMessage = db.getLastMessage(lastMessageID);

                ListItemChats item = new ListItemChats(chatID, peopleID, name, lastMessage);
                graphlistArray.add(item);
            }
            while (chats.moveToNext());
        }
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
