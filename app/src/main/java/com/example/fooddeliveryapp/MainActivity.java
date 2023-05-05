package com.example.fooddeliveryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.SignInActivity.SignInActivity;
import com.example.fooddeliveryapp.databinding.ActivityMainBinding;
import com.example.fooddeliveryapp.fragment.CartFragment;
import com.example.fooddeliveryapp.fragment.HomeFragment;
import com.example.fooddeliveryapp.fragment.MoreFragment;
import com.example.fooddeliveryapp.fragment.OrderFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentTransaction homeTransaction  = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.content,new HomeFragment());
        homeTransaction.commit();
        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                    @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        FragmentTransaction homeTransaction  = getSupportFragmentManager().beginTransaction();
                        homeTransaction.replace(R.id.content,new HomeFragment());
                        homeTransaction.commit();
                        break;
                    case R.id.cart:
                        FragmentTransaction picTransaction  = getSupportFragmentManager().beginTransaction();
                        picTransaction.replace(R.id.content,new CartFragment());
                        picTransaction.commit();
                        break;
                    case R.id.orders:
                        FragmentTransaction camTransaction  = getSupportFragmentManager().beginTransaction();
                        camTransaction.replace(R.id.content,new OrderFragment());
                        camTransaction.commit();
                        break;
                    case R.id.more:
                        FragmentTransaction moreTransaction  = getSupportFragmentManager().beginTransaction();
                        moreTransaction.replace(R.id.content,new MoreFragment());
                        moreTransaction.commit();
                        break;
                }
                return true;
            }
        });

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//
//            case R.id.settings:
//                Toast.makeText(MainActivity.this, "Settings Option Selected", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.logout:
//                auth.getInstance().signOut();
//                mGoogleSignInClient.signOut();
//
//                finish();
//        }
//        return true;
//    }


}