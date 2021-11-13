package com.nperezlmendoza.practica2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.nperezlmendoza.practica2.R;


public class ShoppingFinalFragment extends Fragment  implements View.OnClickListener{


    public ShoppingFinalFragment() {
        // Required empty public constructor
    }



    View view;
    RadioButton butt_method1;
    RadioButton butt_method2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shopping_final_fragment, container, false);

        //radio buttons
        butt_method1 = view.findViewById(R.id.radioButton_method_pay1);
        butt_method2 = view.findViewById(R.id.radioButton_method_pay2);

        Button sendEmailBuy = (Button) view.findViewById(R.id.button_finish_buy);
        sendEmailBuy.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    RadioButton methodPay;
    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.button_finish_buy){

            EditText name = view.findViewById(R.id.editTextName);
            EditText email = view.findViewById(R.id.editTextEmail);
            EditText phone = view.findViewById(R.id.editTextPhone);
            EditText dir = view.findViewById(R.id.editTextAddress);
            methodPay = null;

            if (butt_method1.isChecked()) {
                methodPay = view.findViewById(R.id.radioButton_method_pay1);

            }else if (butt_method2.isChecked()) {
                methodPay = view.findViewById(R.id.radioButton_method_pay2);

            }

            if(methodPay != null){

                Intent intentSend = new Intent(Intent.ACTION_SENDTO);
                intentSend.setData(Uri.parse("mailto:")); // only email apps should handle this
                intentSend.putExtra(Intent.EXTRA_EMAIL,new String[]{email.getText().toString()});
                intentSend.putExtra(Intent.EXTRA_TEXT,
                        "Nombre: " + name.getText().toString() + "\n"
                        + "Email: " + email.getText().toString() + "\n" +
                        "Numero de teléfono: " + phone.getText().toString() + "\n" +
                        "Dirección: " + dir.getText().toString() + "\n" +
                        "Método de pago: " + methodPay.getText().toString());

                intentSend.putExtra(Intent.EXTRA_SUBJECT, "Confirmación de compra");

                startActivity(Intent.createChooser(intentSend, "Send an Email:"));
            }
        }

    }
}