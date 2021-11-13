package com.nperezlmendoza.practica2.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.adapters.MainMenuSectionListAdapter;
import com.nperezlmendoza.practica2.adapters.TabListsAdapter;
import com.nperezlmendoza.practica2.adapters.TabSwipeAdapter;
import com.nperezlmendoza.practica2.entities.Game;

import java.util.ArrayList;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TabPs4Fragment extends TabFragmentBase {
    private SQLiteDatabase db;

    public TabPs4Fragment(TabSwipeAdapter adapter, SQLiteDatabase db) {
        this.swipeAdapter = adapter;
        this.db = db;
    }

    public TabPs4Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_ps4_fragment, container, false);

        loadPlatform("PS4");
        ListView listview;
        thisAdapter = new TabListsAdapter(view.getContext(), games, db, swipeAdapter);
        listview = view.findViewById(R.id.TabPs4);
        listview.setAdapter(thisAdapter);

        setOnClickListener(listview);

        // Inflate the layout for this fragment
        return view;
    }

    private void setOnClickListener(ListView listview) {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Game item = (Game) adapter.getItemAtPosition(position);
                ((MainActivity) getActivity()).loadFragment(R.id.contenedor_fragment, new GameDetailsFragment(db, item));
            }
        });
    }

    protected void loadPlatform(String platform) {
        games.clear();

        Cursor gameCursor = cupboard().withDatabase(db).query(Game.class)
                .withSelection( "platform = ?", platform)
                .getCursor();

        try {
            QueryResultIterable<Game> itr = cupboard().withCursor(gameCursor).iterate(Game.class);
            for (Game game : itr) {
                games.add(game);
            }
        } finally {
            gameCursor.close();
        }
    }
}