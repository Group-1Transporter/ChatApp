package com.example.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.Models.User;
import com.example.chatapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    DatabaseReference database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance().getReference("Users");
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating An Account");
        progressDialog.setMessage("Welcome in Tech Chat");

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            User user = new User(binding.userName.getText().toString(),binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                            //User user = new User("Ruhil","Tanveer","Shadab");
                            String id = task.getResult().getUser().getUid();
                            //String id = FirebaseAuth.getInstance().getUid();
                            database.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Task S", Toast.LENGTH_SHORT).show();
                                        Log.e("sign", "sssssssssssssssssss");
                                        finish();
                                    }
                                    else{
                                        Log.e("ssds","__________________________>"+task.getException());

                                    }
                                }
                            });

                            //Toast.makeText(SignUpActivity.this,"User Creatd Sucessfulley",Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(SignUpActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });
        binding.tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();


            }
        });

    }
}
