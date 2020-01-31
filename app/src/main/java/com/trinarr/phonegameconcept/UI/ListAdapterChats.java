package com.trinarr.phonegameconcept.UI;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.trinarr.phonegameconcept.R;

import java.util.ArrayList;

public class ListAdapterChats extends RecyclerView.Adapter<ListAdapterChats.ViewHolder> implements RecyclerView.OnItemTouchListener  {
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    GestureDetector mGestureDetector;

    private ArrayList<ListItemChats> objects;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    ListAdapterChats(Context ctx, final RecyclerView recyclerView, ArrayList<ListItemChats> history, OnItemClickListener listener) {
        inflater = LayoutInflater.from(ctx);
        mListener = listener;
        objects = history;

        mGestureDetector = new GestureDetector(ctx, new GestureDetector.SimpleOnGestureListener() {
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
    public ListAdapterChats.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_chats, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapterChats.ViewHolder holder, int position) {
        ListItemChats item = objects.get(position);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}

    static class ViewHolder extends RecyclerView.ViewHolder {
        /*TextView title;
        ImageView bookmarkItem;
        FrameLayout tagLayout;*/
        //Context ctx;

        public ViewHolder(View itemView) {
            super(itemView);

            /*title = itemView.findViewById(R.id.title);
            bookmarkItem = itemView.findViewById(R.id.bookmarkItem);
            tagLayout = itemView.findViewById(R.id.tagLayout);*/
            //ctx = itemView.getContext();
        }
    }
}
