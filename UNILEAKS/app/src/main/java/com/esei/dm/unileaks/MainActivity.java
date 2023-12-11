package com.esei.dm.unileaks;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private DatabaseHelper databaseHelper;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        databaseHelper = new DatabaseHelper(this);
        if(!databaseHelper.checkUser("admin","esei")){
            databaseHelper.addUser("admin", "esei");
            databaseHelper.setAdmin(1, true);
        }


        // Set a click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (databaseHelper.checkUser(username, password)) {
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    intent.putExtra("username", username); // "username" debe coincidir con la clave utilizada en WelcomeActivity
                    startActivity(intent);
                    showToast("Login successful");
                } else {
                    showToast("Login failed. Please check your credentials.");
                }
            }
        });

        // Set a click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the RegisterActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
