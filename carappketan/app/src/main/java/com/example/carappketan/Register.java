
package com.example.carappketan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, mobileNumberEditText,
            vehicleNoEditText, emailEditText, vehicleNameEditText, passwordEditText,
            confirmPasswordEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        vehicleNoEditText = findViewById(R.id.vehicleNoEditText);
        emailEditText = findViewById(R.id.emailEditText);
        vehicleNameEditText = findViewById(R.id.vehicleNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String firstName = firstNameEditText.getText().toString().trim();
        final String lastName = lastNameEditText.getText().toString().trim();
        final String mobileNumber = mobileNumberEditText.getText().toString().trim();
        final String vehicleNumber = vehicleNoEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String vehicleName = vehicleNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Please check password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserData(firstName, lastName, mobileNumber, vehicleNumber, email, vehicleName,password );
                        }
                    } else {
                        Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserData(String firstName, String lastName, String mobileNumber,
                              String vehicleNumber, String email, String vehicleName,String password) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserRef = mDatabase.child(userId);
        currentUserRef.child("firstName").setValue(firstName);
        currentUserRef.child("lastName").setValue(lastName);
        currentUserRef.child("mobileNumber").setValue(mobileNumber);
        currentUserRef.child("vehicleNumber").setValue(vehicleNumber);
        currentUserRef.child("email").setValue(email);
        currentUserRef.child("vehicleName").setValue(vehicleName);
        currentUserRef.child("password").setValue(password);

        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}
