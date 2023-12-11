package com.esei.dm.unileaks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Obtén la información del usuario desde la actividad anterior
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");

            // Muestra el nombre de usuario en un TextView u otro elemento de la interfaz
            TextView welcomeText = findViewById(R.id.welcomeText);
            if (welcomeText != null) {
                welcomeText.setText("¡Bienvenido, " + username + "!");
            }
        }
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); // Obtener el ID del elemento del menú seleccionado

        if (itemId == R.id.nav_home) {
            // Lógica para el caso NAV_HOME
        } else if (itemId == R.id.nav_forum) {
            Intent intent = new Intent(WelcomeActivity.this, Forum.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_chat) {
            Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_resources) {
            Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_profile) {
            Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_logout) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_share) {
            Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.nav_rate) {
            Intent intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
        return true;
    }

}
