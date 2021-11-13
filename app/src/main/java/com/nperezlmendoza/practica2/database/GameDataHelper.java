package com.nperezlmendoza.practica2.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;
import com.nperezlmendoza.practica2.entities.Game;

import java.util.ArrayList;

public class GameDataHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "practica2";
    private static final int DB_VERSION = 2;

    public GameDataHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    static {
        // register our models
        cupboard().register(Game.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        createGames(db);
        Log.d("DB created", DB_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB upgrade to version", Integer.toString(newVersion));
        cupboard().withDatabase(db).upgradeTables();

        if (oldVersion == 1) {
            ContentValues cv = new ContentValues();
            cv.put("in_cart", 0);
            cupboard().withDatabase(db).update(Game.class, cv);
            oldVersion++;
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB downgrade to version", Integer.toString(newVersion));
    }

    protected void createGames(SQLiteDatabase db) {
        for(Game game: Game.getGames()){
            cupboard().withDatabase(db).put(game);
        }
    }
}
