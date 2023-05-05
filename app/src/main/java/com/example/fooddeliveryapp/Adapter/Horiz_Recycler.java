package com.example.fooddeliveryapp.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.ModelClass.MainModel;
import com.example.fooddeliveryapp.ModelClass.MainModel2;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.UpdateReclerview;
;

import java.util.ArrayList;

public class Horiz_Recycler extends RecyclerView.Adapter<Horiz_Recycler.ViewHolder> {

    UpdateReclerview updateReclerview;

    Context context;
    ArrayList<MainModel> arrayList;

    boolean check = true;
    boolean select = true;
    int row_index = -1;

    public Horiz_Recycler(UpdateReclerview updateReclerview, Context context, ArrayList<MainModel> arrayList) {
        this.updateReclerview = updateReclerview;
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_model, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.picname.setText(arrayList.get(position).getImgname());
        holder.imageView.setImageResource(arrayList.get(position).getImg());

        if(check) {
            ArrayList<MainModel2> list = new ArrayList<>();
            list.add(new MainModel2("Burger","200","9:00AM - 12PM",R.drawable.burgerpic,
                    R.drawable.time_icon));
            list.add(new MainModel2("Special Burger","200","9:00AM - 12PM",R.drawable.burgerpic,
                    R.drawable.time_icon));

            list.add(new MainModel2("Veg Burger","200","9:00AM - 12PM",R.drawable.burgerpic,
                    R.drawable.time_icon));
            updateReclerview.callback(position, list);
        }

            check = false;
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    row_index = position;
                    notifyDataSetChanged();

                    try {
                        if (position == 0){
                            ArrayList<MainModel2> list = new ArrayList<>();
                            list.add(new MainModel2("Burger","200","9:00AM - 12PM",R.drawable.burgerpic,
                                    R.drawable.time_icon));
                            list.add(new MainModel2("Special Burger","200","9:00AM - 12PM",R.drawable.burgerpic,
                                    R.drawable.time_icon));

                            list.add(new MainModel2("Veg Burger","200","9:00AM - 12PM",R.drawable.burgerpic,
                                    R.drawable.time_icon));
                            updateReclerview.callback(position,list);
                        }
                        else if(position == 1)
                        {
                            ArrayList<MainModel2> list = new ArrayList<>();
                            list.add(new MainModel2("La Pino Pizza","200","9:00AM - 12PM",R.drawable.pizza1,
                                    R.drawable.time_icon));
                            list.add(new MainModel2("Cheezy Pizza","300","9:00AM - 12PM",R.drawable.pizza2,
                                    R.drawable.time_icon));

                            list.add(new MainModel2("Mushroom Pizza","500","9:00AM - 12PM",R.drawable.pizza3,
                                    R.drawable.time_icon));
                            list.add(new MainModel2("Onion Pizza","240","9:00AM - 12PM",R.drawable.pizza4,
                                    R.drawable.time_icon));

                            updateReclerview.callback(position,list);
                        }
                        else if(position == 2)
                        {
                            ArrayList<MainModel2> list = new ArrayList<>();
                            list.add(new MainModel2("Pasta","200","9:00AM - 12PM",R.drawable.pasta1,
                                    R.drawable.time_icon));
                            list.add(new MainModel2("Cheezy Pasta","300","9:00AM - 12PM",R.drawable.pasta2,
                                    R.drawable.time_icon));

                            list.add(new MainModel2("Pasta3","500","9:00AM - 12PM",R.drawable.pasta3,
                                    R.drawable.time_icon));
                            updateReclerview.callback(position,list);
                        }
                        else if(position == 3)
                        {
                            ArrayList<MainModel2> list = new ArrayList<>();
                            list.add(new MainModel2("Noodles1","200","9:00AM - 12PM",R.drawable.noodles1,
                                    R.drawable.time_icon));
                            list.add(new MainModel2("Cheezy Noodles","300","9:00AM - 12PM",R.drawable.noodles2,
                                    R.drawable.time_icon));

                            list.add(new MainModel2("Chinese Noodle","500","9:00AM - 12PM",R.drawable.noodles3,
                                    R.drawable.time_icon));

                            updateReclerview.callback(position,list);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            if(select)
            {
                if(position ==0)
                {
                    holder.cardView.setBackgroundResource(R.drawable.backround_red);
                    select = false;
                }
            }
            else
            {
                if(row_index == position)
                {
                    holder.cardView.setBackgroundResource(R.drawable.backround_red);
                }
                else {
                    holder.cardView.setBackgroundResource(R.drawable.default_b);
                }
            }

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView picname;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picname = itemView.findViewById(R.id.picname);
            imageView = itemView.findViewById(R.id.pic);
            cardView = itemView.findViewById(R.id.Cardmenu);
        }
    }

}