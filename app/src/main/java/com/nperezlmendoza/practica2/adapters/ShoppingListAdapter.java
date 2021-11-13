package com.nperezlmendoza.practica2.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.entities.Game;
import com.nperezlmendoza.practica2.fragments.GameDetailsFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class ShoppingListAdapter extends ArrayAdapter<Game> {
    private SQLiteDatabase db;

    public ShoppingListAdapter(Context context, ArrayList<Game> gameList, SQLiteDatabase db) {
        super(context, 0, gameList);
        this.db = db;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_cart_item, parent, false);
        }

        Game game = getItem(position);

        DecimalFormat formatter = new DecimalFormat("#0.00€");
        TextView gameName = convertView.findViewById(R.id.shoppingItemTitle);
        TextView gamePrice = convertView.findViewById(R.id.shoppingItemPrice);

        gameName.setText(game.title);
        gamePrice.setText(formatter.format(game.price));

        ImageButton button = convertView.findViewById(R.id.removeFromCartButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.in_cart = false;
                cupboard().withDatabase(db).put(game);
                remove(game);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
