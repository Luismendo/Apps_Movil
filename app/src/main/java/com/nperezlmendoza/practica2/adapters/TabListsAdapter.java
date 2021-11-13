package com.nperezlmendoza.practica2.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.entities.Game;
import com.nperezlmendoza.practica2.fragments.MainMenuSectionsFragment;
import com.nperezlmendoza.practica2.fragments.TabViewPagerFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TabListsAdapter extends ArrayAdapter<Game> {
    SQLiteDatabase db;
    TabSwipeAdapter tabSwipeAdapter;

    public TabListsAdapter(Context context, ArrayList<Game> data, SQLiteDatabase db, TabSwipeAdapter tabSwipeAdapter) {
        super(context, 0, data);
        this.db = db;
        this.tabSwipeAdapter = tabSwipeAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_tab_section_game_entry, parent, false);
        }

        Game game = getItem(position);

        TextView gameName = convertView.findViewById(R.id.mainTabListGameName);
        TextView gamePrice = convertView.findViewById(R.id.mainTabListGamePrice);
        DecimalFormat formatter = new DecimalFormat("#0.00€");

        gameName.setText(game.title);
        gamePrice.setText(formatter.format(game.price));

        ImageButton buttonAdd = convertView.findViewById(R.id.mainTabToggleInCart);


        if (! game.in_cart) {
            buttonAdd.setEnabled(true);
            buttonAdd.setAlpha(255);

            buttonAdd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (game.in_cart) return;
                    game.in_cart = !game.in_cart;
                    cupboard().withDatabase(db).put(game);
                    tabSwipeAdapter.updateLists(game);
                    buttonAdd.setAlpha(75);
                    buttonAdd.setEnabled(false);
                    Snackbar.make(parent, "Añadido al carrito!", 2*1000).show();
                }
            });
        }else{
            buttonAdd.setEnabled(false);
            buttonAdd.setAlpha(75);
        }


        return convertView;
    }
}