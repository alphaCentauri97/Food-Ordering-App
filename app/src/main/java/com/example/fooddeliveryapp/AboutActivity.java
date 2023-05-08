package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.fooddeliveryapp.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.about.setText("Welcome to our food ordering app, your one-stop-shop for ordering delicious food from your favorite restaurants in a hassle-free way. With our app, you can browse through a wide variety of cuisines and dishes, place orders, and track delivery - all from the comfort of your phone.\n" +
                "\n" +
                "Our app is designed to make your food ordering experience easy and enjoyable. We offer a range of features to help you find exactly what you're looking for, including advanced search options, personalized recommendations, and easy-to-use filters. You can browse through menus, read reviews, and even customize your orders to your liking.\n" +
                "\n" +
                "Our team is dedicated to providing you with the best possible experience. We work with top-rated restaurants in your area to ensure that you get the freshest, most delicious food delivered right to your doorstep. We also offer a range of payment options, including secure online payment and cash on delivery, so you can choose the one that works best for you.\n" +
                "\n" +
                "At our core, we believe that food is more than just fuel - it's an experience that brings people together. That's why we're committed to creating a seamless and enjoyable experience for our customers. Whether you're craving your favorite comfort food or trying something new, we're here to help you satisfy your cravings.\n" +
                "\n" +
                "Thank you for choosing our food ordering app. We look forward to serving you and making your food ordering experience a delightful one.");

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}