package com.trinarr.phonegameconcept.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trinarr.phonegameconcept.R;

import java.util.ArrayList;

public class ScreenChat extends AppCompatActivity implements ListAdapterMessages.OnItemClickListener, ListAdapterAnswers.OnItemClickListener{
    private ArrayList<ListItemMessage> messages = new ArrayList<>();
    private ArrayList<ListItemAnswer> answers = new ArrayList<>();

    private ListAdapterMessages listAdapter;
    private ListAdapterAnswers listAdapterAnswers;

    private Cursor chats = null;
    private GameDatabase db = null;

    private int chatID, peopleID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_chat);

        BlockHeader blockHeader = new BlockHeader(this);
        LinearLayout parentLayout = findViewById(R.id.parentLayout);
        parentLayout.addView(blockHeader, 0);
        blockHeader.setBackButton();

        Intent intent = getIntent();
        chatID = intent.getIntExtra("chatID", -1);
        peopleID = intent.getIntExtra("peopleID", -1);
        if(chatID == -1 || peopleID == -1) {
            return;
        }

        db = new GameDatabase(this);
        blockHeader.setHeaderText(db.getPeople(peopleID));

        RecyclerView messageList = findViewById(R.id.recycleView);
        listAdapter = new ListAdapterMessages(this, messageList, messages, this);
        messageList.addOnItemTouchListener(listAdapter);
        messageList.setAdapter(listAdapter);

        //messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        messageList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /*messages.add(new ListItemMessage("Ivan S.", "Hey, u!", ListItemMessage.TYPE_PERSON));
        messages.add(new ListItemMessage("Hey, u!", ListItemMessage.TYPE_MY));
        messages.add(new ListItemMessage("Ivan S.", "Hey, u!", ListItemMessage.TYPE_PERSON));
        messages.add(new ListItemMessage( "Hey, u!", ListItemMessage.TYPE_MY));
        messages.add(new ListItemMessage("Ivan S.", "Hey, u!", ListItemMessage.TYPE_PERSON));
        messages.add(new ListItemMessage( "Hey, u!", ListItemMessage.TYPE_MY));
        messages.add(new ListItemMessage("Ivan S.", "Hey, u!", ListItemMessage.TYPE_PERSON));
        messages.add(new ListItemMessage( "Hey, u!", ListItemMessage.TYPE_MY));*/

        RecyclerView answersList = findViewById(R.id.chatVariantsList);
        listAdapterAnswers = new ListAdapterAnswers(this, answersList, answers, this);
        answersList.addOnItemTouchListener(listAdapterAnswers);
        answersList.setAdapter(listAdapterAnswers);

        //messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        answersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //answers.add(new ListItemAnswer("Go home"));
        //answers.add(new ListItemAnswer("Go home"));
        //answers.add(new ListItemAnswer("Go home"));
        //answers.add(new ListItemAnswer("Go home now!"));
    }

    @Override
    public void onItemClick(View view, int position) {
        LogManager.log("onItemClick  "+position, this.getClass());

        //Intent intent = new Intent(this, ScreenChat.class);
        //startActivity(intent);
        // do whatever
    }

    @Override
    public void onLongItemClick(View view, int position) {
        LogManager.log("onLongItemClick  "+position, this.getClass());
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
