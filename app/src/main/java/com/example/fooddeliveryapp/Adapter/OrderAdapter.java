package com.example.fooddeliveryapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.ModelClass.OrderModel;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.OrderedSectionBinding;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<OrderModel>itemList;
    Context context;

    public OrderAdapter(List<OrderModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.ordered_section,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.binding.tvprice.setText(itemList.get(position).getPrice()+" \u20B9");
        holder.binding.itemname.setText(itemList.get(position).getItem_name());
        holder.binding.tvpayment.setText(itemList.get(position).getPayment());
        String encoded = itemList.get(position).getItem_pic();
        byte[] byteArray = Base64.decode(encoded, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.binding.itempic.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        OrderedSectionBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = OrderedSectionBinding.bind(itemView);
        }
    }
}
