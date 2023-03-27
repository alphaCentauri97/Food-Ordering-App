package com.example.fooddeliveryapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddeliveryapp.ModelClass.MainModel2;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.RoomDatabase.AppDatabse;
import com.example.fooddeliveryapp.RoomDatabase.Cartmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<Cartmodel> itemList;
    Context context;

    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setItemList(List<Cartmodel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_section,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        viewHolder.itempic.setImageResource(itemList.get(i).item_pic);
        viewHolder.itemPrice.setText(itemList.get(i).price);
        viewHolder.itemname.setText(itemList.get(i).item_name);
        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabse db = AppDatabse.getAppDataBase(context);
                db.userDao().delete(itemList.get(i));
                itemList.remove(itemList.get(i));
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemname,itemPrice;
        Button btorder;
        ImageView itempic,cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.itemname);
            itemPrice = itemView.findViewById(R.id.tvprice);
            btorder = itemView.findViewById(R.id.btorder);
            itempic = itemView.findViewById(R.id.itempic);
            cancel = itemView.findViewById(R.id.cancel_action);

        }
    }
}
