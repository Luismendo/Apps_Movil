package com.nperezlmendoza.practica2.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.adapters.MainMenuSectionListAdapter;
import com.nperezlmendoza.practica2.entities.Game;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class GameDetailsFragment extends Fragment {
    private SQLiteDatabase db;
    private Game game;

    public GameDetailsFragment() {
        // Required empty constructor
    }

    public GameDetailsFragment(SQLiteDatabase db, Game game) {
        this.db = db;
        this.game = game;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.game_details_fragment, container, false);

        ((TextView) view.findViewById(R.id.gameDetailsTitle)).setText(game.title);
        ((TextView) view.findViewById(R.id.gameDetailsDescription)).setText(game.description);

        DecimalFormat formatter = new DecimalFormat("#0.00€");
        ((TextView) view.findViewById(R.id.gameDetailsPrice)).setText(formatter.format(game.price));

        FloatingActionButton fab = view.findViewById(R.id.addToCartButton);
        if (! game.in_cart) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (game.in_cart) return;
                    game.in_cart = true;
                    cupboard().withDatabase(db).put(game);
                    fab.setEnabled(false);
                    Snackbar.make(view, "Añadido al carrito!", 3 * 1000).show();
                }
            });
        }
        else fab.setEnabled(false);

        return view;
    }
}
