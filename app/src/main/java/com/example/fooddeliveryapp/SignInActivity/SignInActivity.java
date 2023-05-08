package com.example.fooddeliveryapp.SignInActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.Users;
import com.example.fooddeliveryapp.databinding.ActivitySignInBinding;

import com.example.fooddeliveryapp.fragment.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    GoogleSignInClient mGoogleSignInClient;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = this.getSharedPreferences("MyPref",MODE_PRIVATE);
        preferences.edit().clear();


        database = FirebaseDatabase.getInstance();
            auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Sign In");
        progressDialog.setMessage("Sign In your Account");

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.btsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(binding.etemail.getText().toString().isEmpty() || binding.etpassword.getText().toString().isEmpty())
              {
                  Toast.makeText(SignInActivity.this,"Please fill all the text",Toast.LENGTH_SHORT).show();
              }
              else
              {
                  progressDialog.show();
                  auth.signInWithEmailAndPassword(binding.etemail.getText().toString(),
                          binding.etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          progressDialog.dismiss();
                          try {
                              if(task.isSuccessful())
                              {
                                  //FirebaseUser user = auth.getCurrentUser();
                                  Toast.makeText(SignInActivity.this,"Sign In Successfully",Toast.LENGTH_SHORT).show();
                                  Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                  startActivity(intent);
                                  SignInActivity.this.finish();
                              }
                              else
                              {
                                  progressDialog.dismiss();
                                  Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                              }
                          }catch (Exception e){
                              Toast.makeText(SignInActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                          }

                      }
                  });
              }
            }
        });
        binding.tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.btgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = auth.getCurrentUser();
        if (currentuser != null) {
            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
            startActivity(intent);
            SignInActivity.this.finish();
        }
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.e("TAG", "firebaseAuthWithGoogle:" + account.getId());
                            firebaseAuthWithGoogle(account.getIdToken());
                            SharedPreferences.Editor editor = getApplicationContext()
                                    .getSharedPreferences("MyPref",MODE_PRIVATE)
                                    .edit();
                            editor.putString("username",account.getDisplayName());
                            editor.putString("id",account.getId());
                            editor.putString("pic",account.getPhotoUrl().toString());
                            editor.putString("email",account.getEmail());
                            editor.apply();
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();                        }
                    }
                }
            });

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();

                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                            String name = preferences.getString("username","");
                            String pic = preferences.getString("pic","");
                            String email = preferences.getString("email","");

                            Users userInfo = new Users();
                            userInfo.setUserId(user.getUid());
                            userInfo.setUsername(name);
                            userInfo.setEmail(email);
                            userInfo.setProfilepic(pic);

                            database.getReference().child("User").child(user.getUid()).setValue(userInfo);
                            database.getReference().child("Profile Pic").child(user.getUid()).setValue(pic);

                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignInActivity.this,"Sign In Successfully",Toast.LENGTH_SHORT).show();
                            SignInActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}