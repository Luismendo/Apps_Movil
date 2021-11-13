package com.nperezlmendoza.practica2.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.adapters.MainMenuSectionListAdapter;
import com.nperezlmendoza.practica2.adapters.ShoppingListAdapter;
import com.nperezlmendoza.practica2.entities.Game;

import java.util.ArrayList;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class ShoppingCartFragment extends Fragment {
    private SQLiteDatabase db;

    public ShoppingCartFragment() {
        // Required empty constructor
    }

    public ShoppingCartFragment(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shopping_cart_fragment, container, false);

        ArrayList<Game> gameList = getGamesInCart();
        ShoppingListAdapter adapter = new ShoppingListAdapter(view.getContext(), gameList, db);
        ListView listview = view.findViewById(R.id.shoppingCartList);
        listview.setAdapter(adapter);
        setOnClickListener(listview);

        Button sendEmailBuy = (Button) view.findViewById(R.id.completeShoppingButton);
        sendEmailBuy.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.completeShoppingButton){
                    ((MainActivity) getActivity()).loadFragment(new ShoppingFinalFragment());
                }
            }
        });

        return view;
    }

    private ArrayList<Game> getGamesInCart() {
        ArrayList<Game> list = new ArrayList<>();

        Cursor games = cupboard().withDatabase(db).query(Game.class)
                .withSelection( "in_cart = 1")
                .getCursor();

        try {
            QueryResultIterable<Game> itr = cupboard().withCursor(games).iterate(Game.class);
            for (Game game : itr) {
                list.add(game);
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
                Game game = (Game) adapter.getItemAtPosition(position);
                ((MainActivity) getActivity()).loadFragment(R.id.contenedor_fragment, new GameDetailsFragment(db, game));
            }
        });
    }
}
