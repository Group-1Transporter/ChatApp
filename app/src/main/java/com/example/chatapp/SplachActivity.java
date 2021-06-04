package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chatapp.databinding.ActivitySplachBinding;


public class SplachActivity extends AppCompatActivity {
ActivitySplachBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySplachBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplachActivity.this,SignInActivity.class);
                startActivity(in);
                finish();
            }
        },2000);
    }
}