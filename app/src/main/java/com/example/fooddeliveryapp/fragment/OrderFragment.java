package com.example.fooddeliveryapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.Adapter.OrderAdapter;
import com.example.fooddeliveryapp.ModelClass.OrderModel;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentOrderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;
    FirebaseAuth auth;
    List<OrderModel> itemList;
    OrderAdapter adapter;
    boolean isProgressbarVisible= false;
    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        itemList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        binding.progressBar2.setVisibility(View.VISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Order").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String name = snapshot.child("item_name").getValue().toString();
                    String pic = snapshot.child("item_img").getValue().toString();
                    String price = snapshot.child("item_price").getValue().toString();
                    String payment = snapshot.child("payment").getValue().toString();
                    OrderModel order = new OrderModel(pic,name,price,payment);
                    itemList.add(order);
                    binding.progressBar2.setVisibility(View.GONE);
                    adapter = new OrderAdapter(itemList,getActivity());
                    binding.recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // handle error
            }
        });
        binding.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), String.valueOf(itemList.size()), Toast.LENGTH_SHORT).show();
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        adapter = new OrderAdapter(itemList,getActivity());
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}