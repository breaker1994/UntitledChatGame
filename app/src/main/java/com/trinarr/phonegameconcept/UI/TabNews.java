package com.trinarr.phonegameconcept.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trinarr.phonegameconcept.R;

import java.util.ArrayList;

public class TabNews extends Fragment implements ListAdapterNews.OnItemClickListener{
    private ArrayList<ListItemNews> graphlistArray = new ArrayList<>();
    private Context context;

    private OnFragmentInteractionListener mListener;

    public TabNews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_news, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        if(context == null) {
            return;
        }
        RecyclerView graphList = view.findViewById(R.id.recycleView);
        ListAdapterNews graphListAdapter = new ListAdapterNews(context, graphList, graphlistArray, this);

        graphList.setAdapter(graphListAdapter);
        graphList.addOnItemTouchListener(graphListAdapter);

        graphList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        graphList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        ListItemNews item = new ListItemNews("Booking.com стал отменять бронирование китайских туристов", "Крупнейший агрегатор отелей Booking.com стал отменять бронирование китайских туристов в России и других странах. В сети появились скриншоты сообщений, которые получают отельеры.");
        graphlistArray.add(item);
        graphlistArray.add(item);
        graphlistArray.add(item);
        graphlistArray.add(item);

        graphListAdapter.notifyDataSetChanged();

        LogManager.log("context "+context, this.getClass());
    }

    @Override public void onItemClick(View view, int position) {
        LogManager.log("onItemClick  "+position, this.getClass());

        Intent intent = new Intent(context, ScreenNews.class);
        startActivity(intent);
    }

    @Override public void onLongItemClick(View view, int position) {
        LogManager.log("onLongItemClick  "+position, this.getClass());
    }

        @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
