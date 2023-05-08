package com.example.fooddeliveryapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.Adapter.CartAdapter;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.RoomDatabase.AppDatabse;
import com.example.fooddeliveryapp.RoomDatabase.Cartmodel;


import java.util.Collections;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));


        cartAdapter = new CartAdapter(getActivity());
        recyclerView.setAdapter(cartAdapter);
        getAllData();


        return view;
    }

    public void getAllData(){
        AppDatabse db = AppDatabse.getAppDataBase(getContext());
        List<Cartmodel> items = db.userDao().getAllItems();
        Collections.reverse(items);
        cartAdapter.setItemList(items);
        cartAdapter.notifyDataSetChanged();
    }
}