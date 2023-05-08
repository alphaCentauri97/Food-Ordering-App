package com.example.fooddeliveryapp.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.fooddeliveryapp.OrderPaymentActivity;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.RoomDatabase.AppDatabse;
import com.example.fooddeliveryapp.RoomDatabase.Cartmodel;
import java.io.ByteArrayOutputStream;

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
        View view = LayoutInflater.from(context).inflate(R.layout.cart_section, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        viewHolder.itempic.setImageResource(itemList.get(i).item_pic);
        viewHolder.itemPrice.setText(itemList.get(i).price+" \u20B9");
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
        viewHolder.btorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderPaymentActivity.class);
                intent.putExtra("product",itemList.get(i).item_name);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), itemList.get(i).item_pic);

// Convert the bitmap to a string using Base64 encoding
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

// Save the encoded string to shared preferences
                SharedPreferences.Editor editor = context.getSharedPreferences("my_prefs", MODE_PRIVATE).edit();
                editor.putString("image", encoded);
                editor.apply();
                intent.putExtra("price",itemList.get(i).price);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemname, itemPrice;
        Button btorder;
        ImageView itempic, cancel;

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
