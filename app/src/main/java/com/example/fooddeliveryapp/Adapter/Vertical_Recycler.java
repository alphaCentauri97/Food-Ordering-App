package com.example.fooddeliveryapp.Adapter;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.ModelClass.MainModel2;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.RoomDatabase.AppDatabse;
import com.example.fooddeliveryapp.RoomDatabase.Cartmodel;
import com.example.fooddeliveryapp.fragment.CartFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class Vertical_Recycler extends RecyclerView.Adapter<Vertical_Recycler.ViewHolder> {

    Context context;
    ArrayList<MainModel2> arrayList;


    public Vertical_Recycler(Context context, ArrayList<MainModel2> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler2,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvproduct.setText(arrayList.get(position).getProductname());
        holder.tvRs.setText(arrayList.get(position).getPriceRs()+" \u20B9");
        holder.tvdate.setText(arrayList.get(position).getDate());
        holder.productimg.setImageResource(arrayList.get(position).getProductimg());
        holder.itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item_name = arrayList.get(position).getProductname();
                String price = arrayList.get(position).getPriceRs();
                int image = arrayList.get(position).getProductimg();

                saveitem(item_name,price,image);
                Toast.makeText(context, item_name+" is added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvproduct,tvRs,tvdate;
        ImageView productimg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvproduct = itemView.findViewById(R.id.product);
            tvRs = itemView.findViewById(R.id.priceRs);
            tvdate = itemView.findViewById(R.id.date);
            productimg = itemView.findViewById(R.id.productimg);


        }
    }
    public void saveitem(String item_name, String item_price, int item_pic){

        AppDatabse db = AppDatabse.getAppDataBase(context);
        Cartmodel cartmodel = new Cartmodel();
        cartmodel.item_name = item_name;
        cartmodel.item_pic = item_pic;
        cartmodel.price = item_price;
        db.userDao().insert(cartmodel);
    }
}
