package com.nperezlmendoza.practica2.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.adapters.MainMenuSectionListAdapter;
import com.nperezlmendoza.practica2.database.GameDataHelper;
import com.nperezlmendoza.practica2.entities.Game;

import java.util.ArrayList;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainMenuSectionsFragment extends Fragment {
    private SQLiteDatabase db;

    public MainMenuSectionsFragment() {
        // Required empty constructor
    }

    public MainMenuSectionsFragment(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_menu_sections_fragment, container, false);

        MainMenuSectionListAdapter adapter;
        ListView listview;

        ArrayList<Game> novedades = getNovedades();
        adapter = new MainMenuSectionListAdapter(view.getContext(), novedades, db);
        listview = view.findViewById(R.id.mainMenuNovedades);
        listview.setAdapter(adapter);
        setOnClickListener(listview);

        ArrayList<Game> ofertas = getOfertas();
        adapter = new MainMenuSectionListAdapter(view.getContext(), ofertas, db);
        listview = view.findViewById(R.id.mainMenuOfertas);
        listview.setAdapter(adapter);
        setOnClickListener(listview);

        ArrayList<Game> ps4 = getPlatform("PS4");
        adapter = new MainMenuSectionListAdapter(view.getContext(), ps4, db);
        listview = view.findViewById(R.id.mainMenuPS4);
        listview.setAdapter(adapter);
        setOnClickListener(listview);

        ArrayList<Game> xbox = getPlatform("Xbox");
        adapter = new MainMenuSectionListAdapter(view.getContext(), xbox, db);
        listview = view.findViewById(R.id.mainMenuXBox);
        listview.setAdapter(adapter);
        setOnClickListener(listview);

        return view;
    }

    protected ArrayList<Game> getNovedades() {
        ArrayList<Game> list = new ArrayList<>();
        Cursor games = cupboard().withDatabase(db).query(Game.class)
                       .withSelection( "date(entry_date) < date('now','-30 days')")
                       .getCursor();

        try {
            QueryResultIterable<Game> itr = cupboard().withCursor(games).iterate(Game.class);
            int added = 0;
            for (Game game : itr) {
                list.add(game);
                if (++added == 3) break;
            }
        } finally {
            games.close();
        }

        return list;
    }

    protected ArrayList<Game> getOfertas() {
        ArrayList<Game> list = new ArrayList<>();
        Cursor games = cupboard().withDatabase(db).query(Game.class)
                .withSelection( "has_offer = 1")
                .getCursor();

        try {
            QueryResultIterable<Game> itr = cupboard().withCursor(games).iterate(Game.class);
            int added = 0;
            for (Game game : itr) {
                list.add(game);
                if (++added == 3) break;
            }
        } finally {
            games.close();
        }

        return list;
    }

    protected ArrayList<Game> getPlatform(String platform) {
        ArrayList<Game> list = new ArrayList<>();
        Cursor games = cupboard().withDatabase(db).query(Game.class)
                .withSelection( "platform = ?", platform)
                .getCursor();

        try {
            QueryResultIterable<Game> itr = cupboard().withCursor(games).iterate(Game.class);
            int added = 0;
            for (Game game : itr) {
                list.add(game);
                if (++added == 3) break;
            }
        } finally {
            games.close();
        }

        return list;
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
}
