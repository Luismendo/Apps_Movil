package com.nperezlmendoza.practica2.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;
import com.nperezlmendoza.practica2.adapters.TabSwipeAdapter;


public class TabViewPagerFragment extends Fragment {
    private SQLiteDatabase db;
    private int tabId;

    public TabViewPagerFragment(SQLiteDatabase db, int tabId) {

        this.tabId = tabId;
        this.db = db;
    }

    public TabViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    ViewPager viewpager;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_view_pager_fragment, container, false);
        //Set to the ViewPager Adapter TapSwipeAdapter
        TabSwipeAdapter tabSwipeAdapter = new TabSwipeAdapter(getChildFragmentManager(), db);
        viewpager = view.findViewById(R.id.viewpager);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewpager);

        viewpager.setAdapter(tabSwipeAdapter);
        viewpager.setCurrentItem(1);

        switch (tabId)
        {
            case 0:
                viewpager.setCurrentItem(0);
                break;
            case 1:
                viewpager.setCurrentItem(1);
                break;
            case 2:
                viewpager.setCurrentItem(2);
                break;
            case 3:
                viewpager.setCurrentItem(3);
                break;
            default:
                viewpager.setCurrentItem(0);
                break;
        }

        // Inflate the layout for this fragment
        return view;
    }
}