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

public class ListAdapterNews extends RecyclerView.Adapter<ListAdapterNews.GraphViewHolder> implements RecyclerView.OnItemTouchListener {
    private LayoutInflater inflater;
    private ArrayList<ListItemNews> objects;
    private OnItemClickListener mListener;

    GestureDetector mGestureDetector;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

    ListAdapterNews(Context ctx, final RecyclerView recyclerView, ArrayList<ListItemNews> history, OnItemClickListener listener) {
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
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}

    @Override
    public ListAdapterNews.GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_news, parent, false);

        return new GraphViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public void onBindViewHolder(ListAdapterNews.GraphViewHolder holder, int position) {
        ListItemNews item = objects.get(position);

        /*holder.tagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.isActive = !item.isActive;

                if(item.isActive) {
                    holder.bookmarkItem.setColorFilter(Color.parseColor("#FFFFFF"));
                    holder.title.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.tagLayout.getBackground().setColorFilter(Utils.getAttributeColor(holder.ctx, R.attr.colorAccent), PorterDuff.Mode.SRC_ATOP);
                }
                else {
                    holder.bookmarkItem.setColorFilter(Color.parseColor("#000000"));
                    holder.title.setTextColor(Color.parseColor("#000000"));
                    holder.tagLayout.getBackground().setColorFilter(Utils.getAttributeColor(holder.ctx, R.attr.card_view_back_pressed), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });*/

        /*if(item.title == null) {
            holder.title.setVisibility(View.GONE);
            holder.bookmarkItem.setVisibility(View.VISIBLE  );
        }
        else {
            holder.bookmarkItem.setVisibility(View.GONE);
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(item.title);
        }

        if(item.isActive) {
            holder.bookmarkItem.setColorFilter(Color.parseColor("#FFFFFF"));
            holder.title.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tagLayout.getBackground().setColorFilter(Utils.getAttributeColor(holder.ctx, R.attr.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            holder.bookmarkItem.setColorFilter(Color.parseColor("#000000"));
            holder.title.setTextColor(Color.parseColor("#000000"));
            holder.tagLayout.getBackground().setColorFilter(Utils.getAttributeColor(holder.ctx, R.attr.card_view_back_pressed), PorterDuff.Mode.SRC_ATOP);
        }*/
    }

    class GraphViewHolder extends RecyclerView.ViewHolder{
        /*TextView title;
        ImageView bookmarkItem;
        FrameLayout tagLayout;*/
        Context ctx;

        public GraphViewHolder(View itemView) {
            super(itemView);

            /*title = itemView.findViewById(R.id.title);
            bookmarkItem = itemView.findViewById(R.id.bookmarkItem);
            tagLayout = itemView.findViewById(R.id.tagLayout);*/
            ctx = itemView.getContext();
        }


    }
}
