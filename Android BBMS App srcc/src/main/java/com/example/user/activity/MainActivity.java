package com.example.user.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    Toolbar toolBar;
    Button btnDonate, btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize toolbar and buttons
        toolBar = findViewById(R.id.toolBar);
        btnDonate = findViewById(R.id.btnDonate);
        btnRequest = findViewById(R.id.btnRequest);

        setSupportActionBar(toolBar);

        // Set click listener for the donate button
        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a toast message when the donate button is clicked
                Toast.makeText(MainActivity.this, "Thank you! Visit our blood bank for free blood checkup and donation.", Toast.LENGTH_LONG).show();
            }
        });

        // Set click listener for the request button
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RequestActivity when the request button is clicked
                Intent intent = new Intent(MainActivity.this, RequestActivity.class);
                startActivity(intent);
            }
        });
    }

    // Create options menu in the toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle options menu item selection
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.profile) {
            // Start the DetailsActivity when the profile menu item is selected
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.logout) {

            // Clear login status and finish the activity
            getSharedPreferences("user", MODE_PRIVATE).edit().putBoolean("login_status", false).apply();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
