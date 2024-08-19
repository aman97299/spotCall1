package com.example.carappketan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InputVehicleNumberActivity extends AppCompatActivity {

    private TextView vehicleNumberTextView;
    private EditText vehicleNumberEditView;
    private String scannedData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_vehicle_number);

        // Retrieve scanned data from intent
        scannedData = getIntent().getStringExtra("scannedData");

        // Initialize views
        vehicleNumberTextView = findViewById(R.id.textViewVehicleNumber);
        vehicleNumberEditView = findViewById(R.id.editTextVehicleNumber); // Initialize vehicleNumberEditView

        Button submit = findViewById(R.id.buttonSubmit);

        submit.setOnClickListener(v -> {
            // Ensure views are properly initialized
            if (vehicleNumberEditView == null || vehicleNumberTextView == null) {
                Log.e("Error", "Views are not properly initialized");
                return;
            }

            String enteredVehicleNumber = vehicleNumberEditView.getText().toString();

            if (enteredVehicleNumber.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a vehicle number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (scannedData != null && !scannedData.isEmpty()) {
                // Extract vehicle number from scanned data
                String scannedVehicleNumber = extractVehicleNumberFromQR(scannedData);
                if (scannedVehicleNumber != null && scannedVehicleNumber.equalsIgnoreCase(enteredVehicleNumber)) {
                    // Display the scanned vehicle number in the TextView
                    //vehicleNumberTextView.setText(scannedVehicleNumber);
                    Toast.makeText(getApplicationContext(), "Match", Toast.LENGTH_SHORT).show();
                    //go to MsgActivity after match no
                    Intent intent = new Intent(InputVehicleNumberActivity.this, MsgActivity.class);
                    intent.putExtra("scannedData", scannedData);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Entered vehicle number does not match with scanned data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Scanned data is empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String extractVehicleNumberFromQR(String scannedData) {
        // Check if the scanned data is not null or empty
        if (scannedData != null && !scannedData.isEmpty()) {
            // Split the scanned data by comma and space to get individual parts
            String[] parts = scannedData.split(", ");
            // Iterate through the parts to find the one containing the vehicle number
            for (String part : parts) {
                // Check if the part starts with "Vehicle No: "
                if (part.startsWith("Vehicle No: ")) {
                    // Extract the vehicle number by removing "Vehicle No: " from the part
                    return part.substring("Vehicle No: ".length());
                }
            }
        }
        // If the vehicle number is not found or the scanned data is empty, return null
        return null;
    }

}
