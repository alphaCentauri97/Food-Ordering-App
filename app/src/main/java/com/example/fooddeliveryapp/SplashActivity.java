package com.example.fooddeliveryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddeliveryapp.SignInActivity.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2600);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this , SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };thread.start();
    }
}