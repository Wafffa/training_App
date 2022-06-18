package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    EditText ed_mail , ed_password , ed_confirmPassword ,ed_user_name ,  ed_phone_number , ed_rank;
    Button signUp_btn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference users;
    FirebaseFirestore ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_mail = (EditText) findViewById(R.id.ed_mail);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_confirmPassword = (EditText) findViewById(R.id.ed_confirmPassword);
        ed_user_name = (EditText) findViewById(R.id.ed_user_name) ;
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number) ;
        ed_rank = (EditText) findViewById(R.id.ed_rank) ;
        signUp_btn = (Button) findViewById(R.id.signUp_btn);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");//"users" is name a table
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerForAuth();
            }
        });
    }

    private void PerForAuth() {
        String email  = ed_mail.getText().toString();
        String password = ed_password.getText().toString();
        String confirmPassword = ed_confirmPassword.getText().toString();
        String userName = ed_user_name.getText().toString();
        String phoneNumber = ed_phone_number.getText().toString();
        String rank = ed_rank.getText().toString();
        progressDialog= new ProgressDialog(this);

        if(userName.isEmpty() ){
            ed_user_name.setError("Entrez un nom d'utilisateur");
        }else if (!email.matches(emailPattern)){
                ed_mail.setError("Entrez e-mail de connexion");
        }else if(phoneNumber.isEmpty() || phoneNumber.length()<10){
            ed_phone_number.setError("Entrez un numéro de téléphone valide");
        }else if(rank.isEmpty() ){
            ed_rank.setError("Entrez votre niveau ");
        }else if(password.isEmpty() || password.length()<6){
            ed_password.setError("Entrez un mot de passe correct  6 caractères requis");
        }else if(!password.equals(confirmPassword)){
            ed_confirmPassword.setError("Le champ Mot de passe ne correspond pas aux deux");
        }else {
            progressDialog.setMessage("Veuillez patienter l'enregistrement");
            progressDialog.setTitle("Enregistrement");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            User newUser = new User();
            newUser.setName(userName);
            newUser.setPhone(phoneNumber);
            newUser.setNiveau(rank);
            newUser.setEmail(email);

//            users.push().setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    ed_user_name.setText("");
//                    ed_mail.setText("");
//                    ed_phone_number.setText("");
//                    ed_rank.setText("");
//                    Toast.makeText(Register.this, "Données utilisateur enregistrées", Toast.LENGTH_SHORT).show();
//                }
//            });

            Firestore_db firestore = new Firestore_db();
            mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        progressDialog.dismiss();
                        String result = firestore.setUserTodb(newUser,task.getResult().getAdditionalUserInfo().getProviderId());
                        if (result == "success") {
                            sendUserToNextActivity();
                        } else {
                            Toast.makeText(Register.this, "faillure to register", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(Register.this,"Inscription réussie", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(Register.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }


            });

        }
    }


    private void sendUserToNextActivity() {
        Intent intent = new Intent(Register.this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void Sign_in(View view) {
        Intent signIn = new Intent(this,MainActivity.class);
        startActivity(signIn);
    }


}