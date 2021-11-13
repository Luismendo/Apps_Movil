package com.nperezlmendoza.practica2.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.entities.Game;
import com.nperezlmendoza.practica2.fragments.MainMenuSectionsFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainMenuSectionListAdapter extends ArrayAdapter<Game> {
    private SQLiteDatabase db;

    public MainMenuSectionListAdapter(Context context, ArrayList<Game> data, SQLiteDatabase db) {
        super(context, 0, data);
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_menu_section_game_entry, parent, false);
        }

        Game game = getItem(position);

        TextView gameName = convertView.findViewById(R.id.mainMenuListGameName);
        TextView gamePrice = convertView.findViewById(R.id.mainMenuListGamePrice);
        DecimalFormat formatter = new DecimalFormat("#0.00€");

        gameName.setText(game.title);
        gamePrice.setText(formatter.format(game.price));
/**
        ImageButton button = convertView.findViewById(R.id.mainMenuToggleInCart);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (game.in_cart) return;
                game.in_cart = true;
                cupboard().withDatabase(db).put(game);
                Snackbar.make(parent, "Añadido al carrito!", 3*1000).show();
            }
        });
**/
        return convertView;
    }
}
