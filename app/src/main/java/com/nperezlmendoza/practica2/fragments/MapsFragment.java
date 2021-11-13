package com.nperezlmendoza.practica2.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.nperezlmendoza.practica2.MainActivity;
import com.nperezlmendoza.practica2.R;


import java.text.DecimalFormat;
import java.util.concurrent.Executor;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MapsFragment extends Fragment {

    View view;
    private FusedLocationProviderClient fusedLocationClient;
    double LatUSer;
    double LongUser;
    GoogleMap googleMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap1) {

            googleMap = googleMap1;
            LatLng U_Tad = new LatLng(40.53976580808336, -3.892979623201989);
            //LatLng User = new LatLng(40.406596, -3.704697);
            requestPermission();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
            Button add_image = (Button) view.findViewById(R.id.mapMe);
            add_image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        return;
                    }
                    fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location!=null){

                                LatUSer = location.getLatitude();
                                LongUser = location.getLongitude();
                                LatLng User = new LatLng(LatUSer, LongUser);
                                googleMap.addMarker(new MarkerOptions().position(User).title("You")
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(User));
                                CalculationByDistance(U_Tad);
                            }else{
                                TextView editDistance = view.findViewById(R.id.textDistance);
                                editDistance.setText("Tu dispositivo no tiene ningun registro de ubicacion reciente");
                            }

                        }
                    });
                }
            });
            googleMap.addMarker(new MarkerOptions().position(U_Tad).title("Marker in UTAD_Practica2"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(U_Tad));

            //googleMap.addMarker(new MarkerOptions().position(User).title("YOU"));
            //CalculationByDistance(U_Tad, User);
        }
    };
    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maps_fragment, container, false);

        return view;
    }

    public void onLocationChanged(Location location) {
        TextView editDistance = view.findViewById(R.id.textDistance);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        editDistance.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    public double CalculationByDistance(LatLng StartP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = LatUSer;
        double lon1 = StartP.longitude;
        double lon2 = LongUser;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));

        TextView editDistance = view.findViewById(R.id.textDistance);
        editDistance.setText("Distancia a la tienda: " + kmInDec + " km con " + meterInDec + " m");


        Log.d("Prueba", String.valueOf(Radius * c));
        return Radius * c;
    }
}