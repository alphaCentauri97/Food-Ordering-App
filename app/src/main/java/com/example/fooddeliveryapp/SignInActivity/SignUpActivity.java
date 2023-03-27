package com.example.fooddeliveryapp.SignInActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.Users;
import com.example.fooddeliveryapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're Creating your Account");

        binding.btsignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(binding.etname.getText().toString().isEmpty() || binding.etemail.getText().toString().isEmpty()
                        || binding.etpassword.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this,"Please fill all the text",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   progressDialog.show();
                   auth.createUserWithEmailAndPassword(binding.etemail.getText().toString(),
                           binding.etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               progressDialog.dismiss();
                               Users user = new Users(binding.etname.getText().toString(),
                                       binding.etemail.getText().toString(),binding.etpassword.getText().toString());

                               String id = task.getResult().getUser().getUid();

                               database.getReference().child("User").child(id).setValue(user);
                               Toast.makeText(SignUpActivity.this,"User Created Successfully",Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                               startActivity(intent);

                           }
                           else
                           {
                               progressDialog.dismiss();
                               Toast.makeText(SignUpActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }
            }
        });
        binding.tvalready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}