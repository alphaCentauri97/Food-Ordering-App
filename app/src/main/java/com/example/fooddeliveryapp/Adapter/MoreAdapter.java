package com.example.fooddeliveryapp.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.AboutActivity;
import com.example.fooddeliveryapp.FeedbackActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.SignInActivity.SignInActivity;
import com.example.fooddeliveryapp.databinding.TextviewLayoutBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder> {
    Context context;
    List<String> itemList;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;

    public MoreAdapter(Context context, List<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.textview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MoreAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.textView.setText(itemList.get(position));
        holder.binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemList.get(position).equals("Log Out")) {
                    auth = FirebaseAuth.getInstance();
                    GoogleSignInOptions gso = new GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(context.getString(com.firebase.ui.auth.R.string.default_web_client_id))
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
                    auth.getInstance().signOut();
                    mGoogleSignInClient.signOut();
                    SharedPreferences.Editor preferences = context.getSharedPreferences("MyPref",MODE_PRIVATE).edit();
                    preferences.clear();
                    Intent intent = new Intent(context, SignInActivity.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
                else if (itemList.get(position).equals("About")) {
                    Intent intent = new Intent(context, AboutActivity.class);
                    context.startActivity(intent);
                }
                else if (itemList.get(position).equals("Send Feedback")) {
                    Intent intent = new Intent(context, FeedbackActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextviewLayoutBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TextviewLayoutBinding.bind(itemView);
        }
    }
}
