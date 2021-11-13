package com.nperezlmendoza.practica2.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;


public class ContactFragment extends Fragment implements View.OnClickListener {
    View view;
    RadioButton butt_contact;
    RadioButton butt_reclaim;
    private SQLiteDatabase db;

    public ContactFragment(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_fragment, container, false);

        //radio buttons
        butt_contact = view.findViewById(R.id.butt_contact);
        butt_reclaim = view.findViewById(R.id.butt_reclaim);

        Button buttonNext = view.findViewById(R.id.button_contact_next);
        buttonNext.setOnClickListener(this);


        // Inflate the layout for this fragment
        return view;
    }

    //buttonNext
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_contact_next){

            if (butt_contact.isChecked()) {
                ((MainActivity) getActivity()).loadFragment(new ContactFormFragment());
            }
            else if (butt_reclaim.isChecked()) {
                ((MainActivity) getActivity()).loadFragment(new ContactReclaimFragment());
            }
        }
    }
}