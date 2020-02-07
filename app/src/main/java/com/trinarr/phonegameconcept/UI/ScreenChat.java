package com.trinarr.phonegameconcept.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trinarr.phonegameconcept.R;
import com.trinarr.phonegameconcept.UI.Database.DatabaseGame;
import com.trinarr.phonegameconcept.UI.Database.DatabaseSaveHelper;

import java.util.ArrayList;

public class ScreenChat extends AppCompatActivity implements ListAdapterMessages.OnItemClickListener, ListAdapterAnswers.OnItemClickListener{
    private ArrayList<ListItemMessage> messages = new ArrayList<>();
    private ArrayList<ListItemAnswer> answers = new ArrayList<>();

    private ListAdapterMessages listAdapterMessages;
    private ListAdapterAnswers listAdapterAnswers;

    private Cursor chats = null;
    private DatabaseGame db = null;
    private DatabaseSaveHelper dbSave = null;

    private int chatID, peopleID;

    private String peopleName = null;

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

        db = new DatabaseGame(this);
        peopleName = db.getPeople(peopleID);
        blockHeader.setHeaderText(peopleName);

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

        dbSave = new DatabaseSaveHelper(this, "messages_"+chatID);
        messages = dbSave.getAllMessages();

        RecyclerView messageList = findViewById(R.id.recycleView);
        listAdapterMessages = new ListAdapterMessages(this, messageList, messages, this);
        messageList.addOnItemTouchListener(listAdapterMessages);
        messageList.setAdapter(listAdapterMessages);

        //messageList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        messageList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        initDialog();
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

    private void getStartMessage() {
        int firstMessageID = db.getFirstMessageID(chatID);

        ListItemMessage itemMessage = db.getMessage(firstMessageID);
        itemMessage.type = ListItemMessage.TYPE_PERSON;
        itemMessage.name = peopleName;

        dbSave.addMessage(itemMessage);
        messages.add(itemMessage);
    }

    private void forceChat() {
        LogManager.log("forceChat", this.getClass());

        ListItemMessage item = messages.get(messages.size()-1);


    }

    public void initDialog() {
        if(messages.size() == 0) {
            getStartMessage();
        }
        else {
            LogManager.log("Not first time! "+messages.size(), this.getClass());
        }
        listAdapterMessages.notifyDataSetChanged();

        forceChat();
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

        if(dbSave != null) {
            dbSave.close();
        }
    }
}
