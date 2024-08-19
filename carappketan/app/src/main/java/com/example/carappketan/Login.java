package com.example.carappketan;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

public class Login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;//new
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        // Check if user is already logged in
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();
            Log.e("userId", userId);


            // If user is already logged in, start MainActivity and finish LoginActivity
            startActivity(new Intent(Login.this, MainActivity.class));
            startService1(userId);
            finish();
        }
        // Check if auto-login preference is set
       emailEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        findViewById(R.id.loginButton).setOnClickListener(view -> {
            email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            loginUser(email, password);
        });

        findViewById(R.id.signupButton).setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private void startService1(String userId1) {
        Application application = getApplication(); // Android's application context
        long appID = 651187214;   // yourAppID
        String appSign ="80610775518bdc3c55a543964e1f4fa1b19dfd55e958dc54b7a3bef11230a949";  // yourAppSign
        String userID =userId1; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName =userId1;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

        ZegoUIKitPrebuiltCallService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }

    private void loginUser(final String email, final String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // User is authenticated, fetch user data
                            fetchUserData(user.getUid(), password);

                            // Subscribe to FCM topic here
                            FirebaseMessaging.getInstance().subscribeToTopic("all");
                        }
                    } else {
                        // Log the authentication error
                        Toast.makeText(Login.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void fetchUserData(String userId, final String enteredPassword) {
        DatabaseReference userRef = mDatabase.child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               try {
                   if (dataSnapshot.exists()) {
                       // User data found in the database
                       String storedPassword = dataSnapshot.child("password").getValue(String.class);

                       if (storedPassword != null && storedPassword.equals(enteredPassword)) {


                           //for call feature integratin start form there---------------------
                           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                           if (user != null) {
                               String userId = user.getUid();
                               startService(userId);

                               Log.d("UniqueID", "User Unique ID: " + userId);
                               // Proceed with your logic using the user's unique ID
                           } else {
                               // No user is currently authenticated
                               Log.d("UniqueID", "No user is currently authenticated");
                           }
                           //end there----------------------------------------------------
                           // Passwords match, login successful
                           Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(Login.this, MainActivity.class);
                           startActivity(intent);
                           // Proceed with your login logic, such as navigating to the main activity
                       } else {
                           // Passwords don't match, show error message
                           Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                           // Set error indication to the password EditText
                           passwordEditText.setError("Incorrect password");
                       }
                   } else {
                       // User data not found
                       Toast.makeText(Login.this, "User not found in database", Toast.LENGTH_SHORT).show();
                   }
               } catch (Exception e) {
                   // Handle any exceptions
                   Toast.makeText(Login.this, "Failed to fetch user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while fetching user data
                Toast.makeText(Login.this, "Failed to fetch user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
            protected void startService(String userId1){
                Application application = getApplication(); // Android's application context
                long appID = 651187214;   // yourAppID
                String appSign ="80610775518bdc3c55a543964e1f4fa1b19dfd55e958dc54b7a3bef11230a949";  // yourAppSign
                String userID =userId1; // yourUserID, userID should only contain numbers, English characters, and '_'.
                String userName =userId1;   // yourUserName

                ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

                ZegoUIKitPrebuiltCallService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
            }
            protected void onDestroy(){
                Login.super.onDestroy();
                ZegoUIKitPrebuiltCallService.unInit();
            }
        });

    }


}
