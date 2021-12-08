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

public class Activity_Login extends AppCompatActivity {

    EditText email, pass;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            Intent i = new Intent(Activity_Login.this, MainActivity.class);
            startActivity(i);
        }


        email = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPass);

    }

    public void functionGotoSignUp(View view) {
        Intent i = new Intent(Activity_Login.this, Activity_Registration.class);
        startActivity(i);
    }

    public void functionSignIn(View view) {
        String userEmail = email.getText().toString();
        String userPassword = pass.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(Activity_Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Activity_Login.this,"Successfully logged in!",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Activity_Login.this, MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(Activity_Login.this,"Login failed!"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}