package com.example.fooddeliveryapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.ModelClass.Vertical_itemModel;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.RoomDatabase.AppDatabse;
import com.example.fooddeliveryapp.RoomDatabase.Cartmodel;


import java.util.ArrayList;
import java.util.List;

public class Vertical_Recycler_Adapter extends RecyclerView.Adapter<Vertical_Recycler_Adapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Vertical_itemModel> productList;
    private ArrayList<Vertical_itemModel> filteredList;


    public Vertical_Recycler_Adapter(Context context, ArrayList<Vertical_itemModel> productList) {
        this.context = context;
        this.productList = productList;
        this.filteredList = new ArrayList<>(productList);
        notifyDataSetChanged();
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

        if (position < filteredList.size()) {
            Vertical_itemModel product = filteredList.get(position);
            // rest of the code
            holder.tvproduct.setText(product.getProductname());
            holder.tvRs.setText(product.getPriceRs()+" \u20B9");
            holder.tvdate.setText(product.getDate());
            holder.productimg.setImageResource(product.getProductimg());
            holder.itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String item_name = product.getProductname();
                    String price = product.getPriceRs();
                    int image = product.getProductimg();

                    saveitem(item_name,price,image);
                    Toast.makeText(context, item_name+" is added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();
                if (query.isEmpty()) {
                    filteredList = new ArrayList<>(productList);
                } else {
                    ArrayList<Vertical_itemModel> tempList = new ArrayList<>();
                    for (Vertical_itemModel product : productList) {
                        if (product.getProductname().toLowerCase().contains(query.toLowerCase())) {
                            tempList.add(product);
                        }
                    }
                    filteredList = tempList.isEmpty() ? new ArrayList<>(productList) : tempList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Vertical_itemModel>)filterResults.values;
                notifyDataSetChanged();
            }
        };
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
