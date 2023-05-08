package com.example.fooddeliveryapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.Adapter.Horiz_Recycler;
import com.example.fooddeliveryapp.Adapter.Vertical_Recycler;

import com.example.fooddeliveryapp.ModelClass.MainModel;
import com.example.fooddeliveryapp.ModelClass.MainModel2;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.UpdateReclerview;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateReclerview {

    RecyclerView h_recycler,v_recycler;
    ArrayList<MainModel> arrayList;
    Horiz_Recycler horiz_recycler;
    Vertical_Recycler vertical_recycler;
    ArrayList<MainModel2> mainModel2;
    public HomeFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView username = view.findViewById(R.id.tvuser);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);
        String pic = preferences.getString("pic","");
        String name = preferences.getString("username","");
//        Glide.with(this).load(pic).into(imageView);
        username.setText("Welcome "+name);

        //******************* Horizontal RecylerView *********************************//

        arrayList = new ArrayList<>();
        h_recycler = view.findViewById(R.id.horiz_recycler);
        v_recycler = view.findViewById(R.id.verti_recycler);

        arrayList.add(new MainModel(R.drawable.burgur,"Burrger"));
        arrayList.add(new MainModel(R.drawable.pizza,"Pizza"));
        arrayList.add(new MainModel(R.drawable.pasta,"Pasta"));
        arrayList.add(new MainModel(R.drawable.noodle,"Noodle"));
        arrayList.add(new MainModel(R.drawable.icecrream,"Ice-Cream"));

        horiz_recycler = new Horiz_Recycler(this,getActivity(),arrayList);
        h_recycler.setAdapter(horiz_recycler);
        h_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        // ********************* Vertical Recycler View ***************************//

        mainModel2 = new ArrayList<>();

        vertical_recycler = new Vertical_Recycler(getActivity(),mainModel2);
        v_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        v_recycler.setAdapter(vertical_recycler);
        v_recycler.setHasFixedSize(true);

        return view;


    }

    @Override
    public void callback(int position, ArrayList<MainModel2> list) {

        vertical_recycler = new Vertical_Recycler(getContext(),list);
        vertical_recycler.notifyDataSetChanged();
        v_recycler.setAdapter(vertical_recycler);

    }
}