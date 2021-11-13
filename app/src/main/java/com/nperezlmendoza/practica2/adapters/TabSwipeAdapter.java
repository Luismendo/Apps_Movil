package com.nperezlmendoza.practica2.adapters;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nperezlmendoza.practica2.entities.Game;
import com.nperezlmendoza.practica2.fragments.TabFragmentBase;
import com.nperezlmendoza.practica2.fragments.TabNovedadesFragment;
import com.nperezlmendoza.practica2.fragments.TabOfertasFragment;
import com.nperezlmendoza.practica2.fragments.TabPs4Fragment;
import com.nperezlmendoza.practica2.fragments.TabXboxFragment;

import java.util.ArrayList;

public class TabSwipeAdapter extends FragmentPagerAdapter {
    final private ArrayList<TabFragmentBase> fragments = new ArrayList<>();

    public TabSwipeAdapter(FragmentManager fragmentManager, SQLiteDatabase db) {
        super(fragmentManager);
        this.fragments.add(new TabNovedadesFragment(this, db));
        this.fragments.add(new TabOfertasFragment(this, db));
        this.fragments.add(new TabPs4Fragment(this, db));
        this.fragments.add(new TabXboxFragment(this, db));
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Novedad";
            case 1:
                return "Ofertas";
            case 2:
                return "PS4";
            case 3:
                return "XBOX";
            default:
                return "NULL";
        }
    }

    public void updateLists(Game game) {
        for (TabFragmentBase fragment : fragments) {
            fragment.updateLists(game);
        }
    }

}
