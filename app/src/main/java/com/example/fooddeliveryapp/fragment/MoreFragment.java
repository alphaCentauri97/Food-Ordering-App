package com.example.fooddeliveryapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.Adapter.MoreAdapter;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentMoreBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MoreFragment extends Fragment {

    List<String>itemList;
    FragmentMoreBinding binding;
    MoreAdapter adapter;
    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentMoreBinding.inflate(inflater, container, false);

        itemList = new ArrayList<>();
        itemList.add("Edit Profile");
        itemList.add("About");
        itemList.add("Send Feedback");
        itemList.add("Log Out");

        adapter = new MoreAdapter(getActivity(),itemList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(adapter);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);
        String pic = preferences.getString("pic","");
        String name = preferences.getString("username","");
        String email = preferences.getString("email","");
        Glide.with(this).load(pic).into(Objects.requireNonNull(binding.userImage));
        binding.userName.setText(name);
        binding.userEmail.setText(email);


        return binding.getRoot();
    }
}