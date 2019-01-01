package com.example.mandeep.firebaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    Button logout;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        if (firebaseAuth.getCurrentUser() == null) {
//
//            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
//            finish();
//        }

        logout = findViewById(R.id.logout);
        firebaseAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                Toast.makeText(ProfileActivity.this, "User logged out successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
