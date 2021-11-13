package com.nperezlmendoza.practica2.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nperezlmendoza.practica2.R;


public class ContactFormFragment extends Fragment implements View.OnClickListener{

    public ContactFormFragment() {
        // Required empty public constructor
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_form_fragment, container, false);

        Button sendEmail = (Button) view.findViewById(R.id.button_email);
        sendEmail.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        EditText name = view.findViewById(R.id.editTextName);
        EditText email = view.findViewById(R.id.editTextEmail);
        EditText content = view.findViewById(R.id.editTextContext);

        Log.d("Prueba", content.getText().toString());

        Intent intentSend = new Intent(Intent.ACTION_SENDTO);
        intentSend.setData(Uri.parse("mailto:")); // only email apps should handle this
        intentSend.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
        intentSend.putExtra(Intent.EXTRA_TEXT,  name.getText().toString() + "\n" + content.getText().toString());
        intentSend.putExtra(Intent.EXTRA_SUBJECT, "Contacto de " + name.getText().toString());

        startActivity(Intent.createChooser(intentSend, "Send an Email:"));
    }
}