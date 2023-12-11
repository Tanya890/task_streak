package com.example.task_streak.Utils;// LoginActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_streak.R;

public class LoginActivity extends AppCompatActivity {
    private EditText EmailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database handler
        db = new DatabaseHandler(this);

        EmailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);


        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the registration activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }

        });
    }

    private void loginUser() {
        db.openDatabase();
        String Email = EmailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Check if fields are not empty
        if (Email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both Email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check user credentials in the database
        if (db.checkUserCredentials(Email, password)) {
            // Successful login
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // You can navigate to the main activity or another activity upon successful login
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Invalid credentials
            Toast.makeText(this, "Invalid Email or password", Toast.LENGTH_SHORT).show();
        }
        db.closeDatabase();
    }
}