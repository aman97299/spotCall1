
package com.example.carappketan;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.carappketan.fragment.profile;
import com.example.carappketan.fragment.qr;
import com.example.carappketan.fragment.scanner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Display the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new profile()).commit();
    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    if (item.getItemId() == R.id.profile1) {
                        //selectedFragment = profile.newInstance("param1", "param2");
                        selectedFragment = new profile();
                    } else if (item.getItemId() == R.id.qr1) {
                        selectedFragment = new qr();
                    } else if (item.getItemId() == R.id.scaner1) {
                        selectedFragment = new scanner(); // Create an instance of the scanner fragment
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,
                            selectedFragment).commit();

                    return true;
                }
            };
}
