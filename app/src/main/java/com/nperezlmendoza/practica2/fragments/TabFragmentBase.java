package com.nperezlmendoza.practica2.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.Fragment;

import com.nperezlmendoza.practica2.adapters.TabListsAdapter;
import com.nperezlmendoza.practica2.adapters.TabSwipeAdapter;
import com.nperezlmendoza.practica2.entities.Game;

import java.util.ArrayList;


abstract public class TabFragmentBase extends Fragment {
    protected SQLiteDatabase db;
    protected TabSwipeAdapter swipeAdapter;
    protected TabListsAdapter thisAdapter;
    final protected ArrayList<Game> games = new ArrayList<>();

    public void updateLists(Game game) {
        for (Game my_game : games) {
            if (my_game._id == game._id) {
                my_game.in_cart = game.in_cart;
                thisAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
