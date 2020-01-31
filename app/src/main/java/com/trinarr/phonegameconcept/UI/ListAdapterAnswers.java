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

public class ListAdapterAnswers extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerView.OnItemTouchListener  {
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    GestureDetector mGestureDetector;

    private Context context;

    private ArrayList<ListItemAnswer> objects;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    ListAdapterAnswers(Context context, final RecyclerView recyclerView, ArrayList<ListItemAnswer> history, OnItemClickListener listener) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_chat_answer, parent, false);;

        return new ViewHolderAnswer(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListItemAnswer item = objects.get(position);

        ViewHolderAnswer holderV = (ViewHolderAnswer) holder;
        holderV.message.setText(item.message);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}

    static class ViewHolderAnswer extends RecyclerView.ViewHolder {
        TextView message;

        public ViewHolderAnswer(View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.message);
        }
    }
}
