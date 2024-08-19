package com.example.carappketan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        // Retrieve data from Firebase Realtime Database
        retrieveUserData();

        // Find the Save button and set a click listener
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData();
            }
        });
    }

    private void retrieveUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Retrieve user data and update UI
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("firstName").getValue(String.class);
                        String last = dataSnapshot.child("lastName").getValue(String.class);
                        String phone = dataSnapshot.child("mobileNumber").getValue(String.class);
                        String vehicle = dataSnapshot.child("vehicleNumber").getValue(String.class);
                        String vehiclename = dataSnapshot.child("vehicleName").getValue(String.class);


                        // Update UI with retrieved data
                        updateUI(name,last, phone, vehicle,vehiclename);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

    private void updateUI(String name,String last, String phone, String vehicle,String vehiclename) {
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextName1 = findViewById(R.id.editTextName1);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextVehicle = findViewById(R.id.editTextVehicle);
        EditText editTextVehicleName = findViewById(R.id.editTextvname);


        editTextName.setText(name);
        editTextName1.setText(last);
        editTextPhone.setText(phone);
        editTextVehicle.setText(vehicle);
        editTextVehicleName.setText(vehiclename);
    }

    private void updateUserData() {
        // Retrieve the text from EditText fields
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        String last = ((EditText) findViewById(R.id.editTextName1)).getText().toString();
        String phone = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
        String vehicle = ((EditText) findViewById(R.id.editTextVehicle)).getText().toString();
        String vehiclename = ((EditText) findViewById(R.id.editTextvname)).getText().toString();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            // Update the corresponding fields in the Firebase Realtime Database
            mDatabase.child(userId).child("firstName").setValue(name);
            mDatabase.child(userId).child("lastName").setValue(last);
            mDatabase.child(userId).child("mobileNumber").setValue(phone);
            mDatabase.child(userId).child("vehicleNumber").setValue(vehicle);
            mDatabase.child(userId).child("vehicleName").setValue(vehiclename);

            // Display a toast message indicating successful update
            Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
