package com.example.carappketan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class MsgActivity extends AppCompatActivity {
    private String scannedData;
    private String uniqueId;
    //private ZegoSendCallInvitationButton callButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_msg);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        if (intent != null) {
            scannedData = intent.getStringExtra("scannedData");
            //uniqueId = intent.getStringExtra("uniqueId");
            uniqueId = null;
            //uniqueId = extractuniqueIdFromQR(scannedData);

            if (scannedData != null) {
                // Split the scanned data using comma as delimiter
                String[] parts = scannedData.split(", ");

                // Iterate through each part to find the one containing "Unique ID"
                for (String part : parts) {
                    if (part.startsWith("Unique ID: ")) {
                        // Extract the unique ID
                        uniqueId = part.substring("Unique ID: ".length());
                        break;
                    }
                }
            }
            // Log the unique ID
            Log.e("UniqueID", "Unique ID: " + scannedData);

            Log.e("UniqueID", "Unique ID: " + uniqueId);
        }

       // callButton = findViewById(R.id.buttonCall);
        if (uniqueId != null && !uniqueId.isEmpty()) {
            // Perform app-to-app communication using the unique ID
            setVoiceCall(uniqueId);
        } else {
            Toast.makeText(MsgActivity.this, "Unique ID is not available", Toast.LENGTH_SHORT).show();
        }

    }


    void setVoiceCall(String targetUserID) {
        ZegoSendCallInvitationButton callButton = findViewById(R.id.buttonCall);
        if (callButton != null) {
            // Set the call button properties
            callButton.setIsVideoCall(false);
            callButton.setResourceID("zego_uikit_call");
            callButton.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
        } else {
            Log.e("MsgActivity", "Button with ID buttonCall not found");
            Toast.makeText(this, "Button not found", Toast.LENGTH_SHORT).show();
        }
    }

}