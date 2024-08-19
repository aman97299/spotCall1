package com.example.carappketan.fragment;

/*import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carappketan.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link qr#newInstance} factory method to
 * create an instance of this fragment.
 */
/*public class qr extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public qr() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment qr.
     */
    // TODO: Rename and change types and number of parameters
 /*   public static qr newInstance(String param1, String param2) {
        qr fragment = new qr();
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
        return inflater.inflate(R.layout.fragment_qr, container, false);
    }
}*/
/*import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carappketan.InputVehicleNumberActivity;
import com.example.carappketan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.firebase.auth.FirebaseAuth;

public class qr extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Retrieve registration data from Firebase Realtime Database
        databaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve registration data from dataSnapshot
                    String vehicleNumber = dataSnapshot.child("vehicleNumber").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("mobileNumber").getValue(String.class);

                    // Concatenate registration data into a single string
                    String registrationData = "Vehicle No: " + vehicleNumber + ", Phone No: " + phoneNumber;

                    // Generate QR code for registration data
                    Bitmap bitmap = generateQRCode(registrationData);

                    // Display QR code
                    ImageView imageView = view.findViewById(R.id.qrCodeImageView);
                    imageView.setImageBitmap(bitmap);

                    // Check if the scanned vehicle number matches the one in the QR code
                    String scannedVehicleNumber = extractVehicleNumberFromQR(registrationData);
                    if (scannedVehicleNumber != null && scannedVehicleNumber.equals(vehicleNumber)) {
                        // Navigate to the activity where the user inputs the vehicle number
                        Intent intent = new Intent(getActivity(), InputVehicleNumberActivity.class);
                        intent.putExtra("vehicleNumber", vehicleNumber);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Vehicle number does not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "User data not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String extractVehicleNumberFromQR(String registrationData) {
        // Split the registrationData string to extract the vehicle number
        String[] parts = registrationData.split(", ");
        for (String part : parts) {
            if (part.startsWith("Vehicle No: ")) {
                // Extract the vehicle number by removing "Vehicle No: " from the string
                return part.substring("Vehicle No: ".length());
            }
        }
        // If the vehicle number is not found, return null or an empty string as per your requirement
        return null;
    }

    private Bitmap generateQRCode(String data) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error generating QR code", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}*/
/*import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carappketan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class qr extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);

        // Initialize Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Retrieve registration data from Firebase Realtime Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve registration data from dataSnapshot
                    String vehicleNumber = dataSnapshot.child("vehicleNumber").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("mobileNumber").getValue(String.class);

                    // Concatenate registration data into a single string
                    String registrationData = "Vehicle No: " + vehicleNumber + ", Phone No: " + phoneNumber;

                    // Generate QR code for registration data
                    Bitmap bitmap = generateQRCode(registrationData);

                    // Display QR code
                    ImageView imageView = view.findViewById(R.id.qrCodeImageView);
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getContext(), "User data not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private Bitmap generateQRCode(String data) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error generating QR code", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}*/

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carappketan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class qr extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr, container, false);
        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        // Retrieve registration data from Firebase Realtime Database
        databaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve registration data from dataSnapshot
                    String vehicleNumber = dataSnapshot.child("vehicleNumber").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("mobileNumber").getValue(String.class);
                    String uniqueId = dataSnapshot.getKey(); // Get the unique ID

                    // Concatenate registration data into a single string
                   // String registrationData = "Vehicle No: " + vehicleNumber + ", Phone No: " + phoneNumber;
                    String registrationData = "Unique ID: " + uniqueId + ", Vehicle No: " + vehicleNumber + ", Phone No: " + phoneNumber;
                    // Generate QR code for registration data
                    Bitmap bitmap = generateQRCode(registrationData);

                    // Display QR code
                    ImageView imageView = view.findViewById(R.id.qrCodeImageView);
                    imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getContext(), "User data not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to fetch user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private Bitmap generateQRCode(String data) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error generating QR code", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}


