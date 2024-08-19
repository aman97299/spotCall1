package com.example.carappketan.fragment;

/*import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.carappketan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
/*public class profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
  /*  public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}*/

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.carappketan.EditProfile;
import com.example.carappketan.Login;
import com.example.carappketan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends Fragment {

    private TextView vehicleNumberTextView;
    private TextView vehicleNameTextView;
    private TextView phoneNumberTextView;
    private TextView emailTextView;
    private TextView username;
    //private ImageView imageView;
    private Button logout, edit;
    private DatabaseReference databaseReference;

    public profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize TextViews
        vehicleNumberTextView = view.findViewById(R.id.text_vehicleNumber);
        vehicleNameTextView = view.findViewById(R.id.text_vehicleName);
        phoneNumberTextView = view.findViewById(R.id.text_phoneNumber);
        emailTextView = view.findViewById(R.id.text_email);
        username = view.findViewById(R.id.username);
        logout =view.findViewById(R.id.log_out);
        edit =view.findViewById(R.id.button_edit);
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // Sign out the user
            Intent intent = new Intent(getActivity(), Login.class); // Create intent to navigate to the login activity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack and create a new task
            startActivity(intent); // Start the login activity
            getActivity().finish(); // Finish the current activity
        });
        edit.setOnClickListener(v -> {
// Create an intent to start the EditProfileActivity
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        });
     //   imageView = view.findViewById(R.id.imageView);

        // Set OnClickListener for the ImageView
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });*/
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Retrieve data from Firebase Realtime Database
        retrieveUserData();

        return view;
    }

    private void retrieveUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Retrieve data from Firebase Realtime Database
            databaseReference.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve data from dataSnapshot
                        String vehicleNumber = dataSnapshot.child("vehicleNumber").getValue(String.class);
                        String vehicleName = dataSnapshot.child("vehicleName").getValue(String.class);
                        String phoneNumber = dataSnapshot.child("mobileNumber").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String username1 = dataSnapshot.child("firstName").getValue(String.class);
                        String username2 = dataSnapshot.child("lastName").getValue(String.class);
                        String user = username1 + " " + username2;
                        // Populate TextViews with user data
                        vehicleNumberTextView.setText(vehicleNumber);
                        vehicleNameTextView.setText(vehicleName);
                        phoneNumberTextView.setText(phoneNumber);
                        emailTextView.setText(email);
                        username.setText(user);
                    } else {
                        Toast.makeText(getContext(), "User data not found in database", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled event
                }
            });
        }
    }
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK && data != null) {
            // Get the selected image URI
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            // Set the selected image to the ImageView
            imageView.setImageBitmap(bitmap);
        }
    }*/
}
