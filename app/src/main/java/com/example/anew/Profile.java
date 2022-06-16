package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;

public class Profile extends AppCompatActivity {
    private TextView name_tv , email_tv , phone_tv , rank_tv;
    private String name , email , phone , rank;
     FirebaseDatabase database;
     DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        name_tv = findViewById(R.id.name_tv);
        email_tv = findViewById(R.id.email_tv);
        phone_tv = findViewById(R.id.phone_tv);
        rank_tv = findViewById(R.id.rank_tv);

        users = FirebaseDatabase.getInstance().getReference().child("user");
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot dataSnapshot = null;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("email").getValue().equals(email)){
                        name_tv.setText(ds.child("fullName").getValue(String.class));
                        email_tv.setText(email);
                        phone_tv.setText(ds.child("phone number").getValue(String.class));
                        rank_tv.setText(ds.child("rank").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}