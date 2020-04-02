package com.trinarr.phonegameconcept.UI;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.trinarr.phonegameconcept.R;

import java.util.ArrayList;

public class ListAdapterMessages extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerView.OnItemTouchListener  {
    private static final int TYPE_MY = 1;
    private static final int TYPE_PERSON = 2;
    private static final int TYPE_NEWS = 3;

    private OnItemClickListener mListener;
    GestureDetector mGestureDetector;

    private Context context;

    private ArrayList<ListItemMessage> objects;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    ListAdapterMessages(Context context, final RecyclerView recyclerView, ArrayList<ListItemMessage> history, OnItemClickListener listener) {
        this.context = context;

        mListener = listener;
        objects = history;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        ListItemMessage item = objects.get(position);

        LogManager.log("item: " + item+" "+item.type, this.getClass());
        if(item.attachmentID != -1) {
            return TYPE_NEWS;
        }

        return item.type;
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return true;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch(viewType) {
            case TYPE_MY: {
                view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_my, parent, false);
                return new ViewHolderMessageMy(view);
            }
            case TYPE_PERSON: default: {
                view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_person, parent, false);
                return new ViewHolderMessagePerson(view);
            }
            case TYPE_NEWS: {
                view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_person_news, parent, false);
                return new ViewHolderMessageNews(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemMessage item = objects.get(position);

        if(item.attachmentID != -1) {
            ViewHolderMessageNews holderV = (ViewHolderMessageNews) holder;
            holderV.message.setText(item.message);
            return;
        }

        if(item.type == ListItemMessage.TYPE_MY) {
            ViewHolderMessageMy holderV = (ViewHolderMessageMy) holder;
            holderV.message.setText(item.message);
            return;
        }

        ViewHolderMessagePerson holderV = (ViewHolderMessagePerson) holder;
        holderV.message.setText(item.message);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}

    static class ViewHolderMessagePerson extends RecyclerView.ViewHolder {
        TextView message;

        public ViewHolderMessagePerson(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);
        }
    }

    static class ViewHolderMessageNews extends RecyclerView.ViewHolder {
        TextView message;
        LinearLayout newsBlock;

        public ViewHolderMessageNews(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);
            newsBlock = itemView.findViewById(R.id.newsBlock);
        }
    }

    static class ViewHolderMessageMy extends RecyclerView.ViewHolder {
        TextView message;

        public ViewHolderMessageMy(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);
        }
    }
}
