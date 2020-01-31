package com.trinarr.phonegameconcept.UI;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.trinarr.phonegameconcept.R;

import java.util.ArrayList;

public class ListAdapterMessages extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerView.OnItemTouchListener  {
    private LayoutInflater inflater;
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

        inflater = LayoutInflater.from(context);
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

        return item.type;
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == ListItemMessage.TYPE_MY) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_my, parent, false);
            return new ViewHolderMessageMy(view);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_person, parent, false);
            return new ViewHolderMessagePerson(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemMessage item = objects.get(position);

        if(item.type == ListItemMessage.TYPE_MY) {
            ViewHolderMessageMy holderV = (ViewHolderMessageMy) holder;
            holderV.message.setText(item.message);
        }
        else {
            ViewHolderMessagePerson holderV = (ViewHolderMessagePerson) holder;
            holderV.name.setText(item.name);
            holderV.message.setText(item.message);
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}

    static class ViewHolderMessagePerson extends RecyclerView.ViewHolder {
        TextView name, message;

        public ViewHolderMessagePerson(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
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
