package com.trinarr.phonegameconcept.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trinarr.phonegameconcept.R;

import java.util.ArrayList;

public class TabChats extends Fragment implements ListAdapterChats.OnItemClickListener{
    private ArrayList<ListItemChats> graphlistArray = new ArrayList<>();
    private Context context;

    private OnFragmentInteractionListener mListener;

    public TabChats() {
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
        return inflater.inflate(R.layout.fragment_tab_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        if(context == null) {
            return;
        }

        RecyclerView graphList = view.findViewById(R.id.recycleView);
        ListAdapterChats graphListAdapter = new ListAdapterChats(context, graphList, graphlistArray, this);

        graphList.addOnItemTouchListener(graphListAdapter);
        graphList.setAdapter(graphListAdapter);

        graphList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        graphList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        ListItemChats item = new ListItemChats("Сергей Сергеевич", "Соберемся, ящас хочу чуть отдохнуть, а то вообще че то уже каждый день ебошу аж лететь перестало!)");
        graphlistArray.add(item);
        graphlistArray.add(item);
        graphlistArray.add(item);
        graphlistArray.add(item);

        //graphListAdapter.notifyDataSetChanged();

        LogManager.log("context "+context, this.getClass());
    }

    @Override public void onItemClick(View view, int position) {
        LogManager.log("onItemClick  "+position, this.getClass());

        Intent intent = new Intent(context, ScreenChat.class);
        startActivity(intent);
    }

    @Override public void onLongItemClick(View view, int position) {
        LogManager.log("onLongItemClick  "+position, this.getClass());
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
