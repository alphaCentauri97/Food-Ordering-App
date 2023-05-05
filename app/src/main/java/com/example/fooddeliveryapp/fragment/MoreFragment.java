package com.example.fooddeliveryapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.databinding.FragmentMoreBinding;


public class MoreFragment extends Fragment {

    FragmentMoreBinding binding;
    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentMoreBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }
}