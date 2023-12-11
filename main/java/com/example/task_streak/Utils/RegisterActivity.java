package com.example.task_streak.Utils;// RegisterActivity.java

// ... other imports ...

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_streak.R;


public class RegisterActivity extends AppCompatActivity {

    private EditText EmailEditText, passwordEditText;
    private Button registerButton;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize database handler
        db = new DatabaseHandler(this);

        EmailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String Email = EmailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input if needed

        // Register the user in the database
        db.registerUser(Email, password);

        // You can navigate to the main activity or another activity upon successful registration
        // For example:
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);

        // Comment the next line if you don't want to finish the RegisterActivity immediately
        finish();
    }
}