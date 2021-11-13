package com.nperezlmendoza.practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.nperezlmendoza.practica2.adapters.TabListsAdapter;
import com.nperezlmendoza.practica2.adapters.TabSwipeAdapter;
import com.nperezlmendoza.practica2.database.GameDataHelper;
import com.nperezlmendoza.practica2.entities.Game;
import com.nperezlmendoza.practica2.fragments.MainMenuSectionsFragment;
import com.nperezlmendoza.practica2.fragments.ContactFragment;
import com.nperezlmendoza.practica2.fragments.MapsFragment;
import com.nperezlmendoza.practica2.fragments.ShoppingCartFragment;
import com.nperezlmendoza.practica2.fragments.TabNovedadesFragment;
import com.nperezlmendoza.practica2.fragments.TabOfertasFragment;
import com.nperezlmendoza.practica2.fragments.TabViewPagerFragment;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    SQLiteDatabase db;
    Menu menu_for_change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GameDataHelper dbHelper = new GameDataHelper(this);
        db = dbHelper.getWritableDatabase();



        //load the menu original first fragment
        MainMenuSectionsFragment menu = new MainMenuSectionsFragment(db);
        FragmentTransaction transition = getSupportFragmentManager().beginTransaction();
        transition.replace(R.id.contenedor_fragment, menu);
        transition.commit();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                0,
                0
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu_for_change = menu;
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //temporal
            case R.id.action_shop:
                loadFragment(R.id.contenedor_fragment, new ShoppingCartFragment(db));
                break;
            case R.id.send_contact_reclaim:
                EditText name = findViewById(R.id.editTextName);
                EditText email = findViewById(R.id.editTextEmail);
                EditText facturaId = findViewById(R.id.editTextIdTax);
                EditText content = findViewById(R.id.editTextContext);
                ImageView picture = findViewById(R.id.image_place);


                Intent intentSend = new Intent(Intent.ACTION_SENDTO);
                intentSend.setData(Uri.parse("mailto:")); // only email apps should handle this
                intentSend.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                intentSend.putExtra(Intent.EXTRA_TEXT,  name.getText().toString() + "\n" + "Factura: " + facturaId.getText().toString() + "\n" + "Context:\n" +content.getText().toString());
                intentSend.putExtra(Intent.EXTRA_SUBJECT, name.getText().toString());

                //Enviar imagen, no consigo obtener el path de la imagen
                //intentSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(picture.getTag().toString()));

                startActivity(Intent.createChooser(intentSend, "Send an Email:"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //load others fragment
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;
        int fragmentId = -1;

        switch (id) {

            case R.id.Novedades:
                fragment = new TabViewPagerFragment(db, 0);
                break;
            case R.id.Ofertas:
                fragment = new TabViewPagerFragment(db, 1);
                break;
            case R.id.PS4:
                fragment = new TabViewPagerFragment(db, 2);
                break;
            case R.id.Xbox:
                fragment = new TabViewPagerFragment(db, 3);
                break;
            case R.id.where:
                fragment = new MapsFragment();
                break;
            case R.id.mi_carrito:
                fragment = new ShoppingCartFragment(db);
                break;
            case R.id.home:
                fragment = new MainMenuSectionsFragment(db);
                break;
            case R.id.Contacto:
                fragment = new ContactFragment(db);
                break;
        }

        if(fragment != null){
            loadFragment(R.id.contenedor_fragment, fragment);
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawerlayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        loadFragment(R.id.contenedor_fragment, fragment);
    }

    public void loadFragment(int resource_id, Fragment fragment) {

        changeMenu(false);
        FragmentManager fragmentManager = getSupportFragmentManager();

        String className = fragment.getClass().getName();
        fragmentManager.popBackStack(className, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentManager.beginTransaction()
                       .replace(resource_id, fragment)
                       .addToBackStack(className)
                       .commit();
    }

    public void changeMenu(boolean change){
        MenuItem itemNew = menu_for_change.findItem(R.id.send_contact_reclaim);
        MenuItem itemOld = menu_for_change.findItem(R.id.action_shop);

        if(change) {
            itemNew.setVisible(true);
            itemOld.setVisible(false);
        }else{
            itemNew.setVisible(false);
            itemOld.setVisible(true);
        }

    }

}
