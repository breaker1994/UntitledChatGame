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
    private String myName = "Я";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogManager.log("onCreate", this.getClass());

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
        answersList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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

        String idName = getResources().getResourceName(view.getId());

        if(idName.contains("blockMessage")) {
            LogManager.log("click message "+idName, this.getClass());


        }
        else {
            LogManager.log("click answer "+idName, this.getClass());

            ListItemAnswer itemAnswer = answers.get(position);

            ListItemMessage itemMessage = new ListItemMessage();
            itemMessage.type = ListItemMessage.TYPE_MY;
            //(itemMessage.name = myName;
            itemMessage.message = itemAnswer.message;
            itemMessage.actionType = ListItemMessage.ACTION_NEXT_MESSAGE;
            itemMessage.actionID = itemAnswer.actionID;

            dbSave.addMessage(itemMessage);
            messages.add(itemMessage);

            getNextMessage(itemAnswer.actionID);

            answers.clear();
            listAdapterAnswers.notifyDataSetChanged();
        }
    }

    @Override
    public void onLongItemClick(View view, int position) {
        LogManager.log("onLongItemClick  "+position, this.getClass());
    }

    private void getNextMessage(int messageID) {
        ListItemMessage itemMessage = db.getMessage(messageID);
        itemMessage.type = ListItemMessage.TYPE_PERSON;
        itemMessage.name = peopleName;

        dbSave.addMessage(itemMessage);
        messages.add(itemMessage);

        listAdapterMessages.notifyDataSetChanged();
    }

    private void forceChat() {
        ListItemMessage item = messages.get(messages.size()-1);

        LogManager.log("forceChat messages.size "+messages.size(), this.getClass());
        LogManager.log("forceChat "+item.messageID +" "+item.actionID+" "+item.message, this.getClass());

        if (item.messageID == item.actionID) {
            LogManager.log("last message!", this.getClass());
            return;
        }


        if(item.actionType == ListItemMessage.ACTION_NEXT_MESSAGE) {
            getNextMessage(item.actionID);
            forceChat();
        }
        else {
            createAnswers(item.actionID);
        }
    }

    public void createAnswers(int actionID) {
        LogManager.log("createAnswers", this.getClass());

        answers = db.getAnswers(actionID);

        RecyclerView answersList = findViewById(R.id.chatVariantsList);
        listAdapterAnswers = new ListAdapterAnswers(this, answersList, answers, this);
        answersList.addOnItemTouchListener(listAdapterAnswers);
        answersList.setAdapter(listAdapterAnswers);
        listAdapterAnswers.notifyDataSetChanged();
    }

    public void initDialog() {
        if(messages.size() == 0) {
            LogManager.log("get First Message", this.getClass());
            getNextMessage(db.getFirstMessageID(chatID));
        }
        else {
            LogManager.log("Not first time! "+messages.size(), this.getClass());
        }

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
