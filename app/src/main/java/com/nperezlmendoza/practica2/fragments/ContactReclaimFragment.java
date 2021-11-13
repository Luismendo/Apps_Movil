package com.nperezlmendoza.practica2.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

public class ContactReclaimFragment extends Fragment implements View.OnClickListener{

    private static int RESULT_LOAD_IMAGE = 1;

    public ContactReclaimFragment() {
        // Required empty public constructor
    }


    View view;
    ImageView imageView;
    Button button;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_reclaim_fragment, container, false);

        imageView = (ImageView) view.findViewById(R.id.image_place);
        Button add_image = (Button) view.findViewById(R.id.button_add_image);
        add_image.setOnClickListener(this);

        ((MainActivity) getActivity()).changeMenu(true);

        //request for camara permission
        if(ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        selectOptionOfPicture();
    }

    //gallery or camara or nothing
    private void selectOptionOfPicture() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);

            } else if (requestCode == 2) {
                super.onActivityResult(requestCode, resultCode, data);
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }

}