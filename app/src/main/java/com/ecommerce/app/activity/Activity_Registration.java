package com.ecommerce.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ecommerce.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_Registration extends AppCompatActivity {

    EditText email, username, pass;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            Intent i = new Intent(Activity_Registration.this, MainActivity.class);
            startActivity(i);
        }

        email = findViewById(R.id.Email);
        username = findViewById(R.id.UserName);
        pass = findViewById(R.id.Password);
    }

    public void functionSignup(View view) {

        String userEmail = email.getText().toString();
        String userName = username.getText().toString();
        String userPassword = pass.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter First Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length() < 5 ){
            Toast.makeText(this, "Password atleast conatains 5 charecters!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(Activity_Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Activity_Registration.this,"Successfully registered!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Activity_Registration.this, MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Activity_Registration.this,"Registration failed!"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void functionGotoSignIn(View view) {
        Intent i = new Intent(Activity_Registration.this, Activity_Login.class);
        startActivity(i);
    }
}