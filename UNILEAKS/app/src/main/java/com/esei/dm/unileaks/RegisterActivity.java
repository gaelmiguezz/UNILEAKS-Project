package com.esei.dm.unileaks;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private Button backToLoginButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);
        databaseHelper = new DatabaseHelper(this);

        // Set a click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    showToast("Username and password are required.");
                } else if (databaseHelper.checkUsername(username)) {
                    showToast("El nombre de usuario ya existe.");
                } else {
                    databaseHelper.addUser(username, password);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    showToast("Registration successful");
                }
            }
        });
        // Set a click listener for the "Back to Login" button
        backToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to the main login activity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
