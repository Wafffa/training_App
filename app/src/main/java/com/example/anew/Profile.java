package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anew.Firestore_db;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.TransactionOptions;

public class Profile extends AppCompatActivity {
    private TextView name_tv , email_tv , phone_tv , rank_tv;
    private String name , email , phone , rank;
    FirebaseAuth mAuth;
    private ImageView imageView_profile;
     FirebaseDatabase database;
     DatabaseReference users;
     private ProgressBar progressBar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getSupportActionBar().setTitle("Home");

        Intent intent = getIntent();
        name_tv = findViewById(R.id.name_tv);
        email_tv = findViewById(R.id.email_tv);
        phone_tv = findViewById(R.id.phone_tv);
        rank_tv = findViewById(R.id.rank_tv);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Firestore_db firestore = new Firestore_db();
        if (firebaseUser == null){
            Toast.makeText(this, "Something went wrong ! User's details are not available at the moment ", Toast.LENGTH_SHORT).show();
        }else {
            progressBar.setVisibility(View.VISIBLE);
            Firestore_db.Userdd userInf = firestore.getUserFromDb(firebaseUser.getUid());
            name_tv.setText(userInf.getName());
        }
        //

       // users = FirebaseDatabase.getInstance().getReference().child("user");
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        /*users.addValueEventListener(new ValueEventListener() {
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
        });*/

    }

//    private void showUserProfile(FirebaseUser firebaseUser) {
//        String userID = firebaseUser.getUid();
//
//        //Extracting user Reference from Database for "Registered Users "
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
//        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        referenceProfile.child(userID).get()
//    }
}