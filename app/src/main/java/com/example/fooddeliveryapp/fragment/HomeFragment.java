package com.example.fooddeliveryapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.Adapter.Horiz_Recycler_Adapter;
import com.example.fooddeliveryapp.Adapter.Vertical_Recycler_Adapter;

import com.example.fooddeliveryapp.ModelClass.Horizontal_itemModel;
import com.example.fooddeliveryapp.ModelClass.Vertical_itemModel;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.UpdateReclerview;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements UpdateReclerview,SearchView.OnQueryTextListener {

    RecyclerView h_recycler,v_recycler;
    ArrayList<Horizontal_itemModel> arrayList;
    Horiz_Recycler_Adapter horiz_recycler;
    Vertical_Recycler_Adapter vertical_recycler;
    ArrayList<Vertical_itemModel> mainModel2;
    SearchView searchView;
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

        arrayList.add(new Horizontal_itemModel(R.drawable.burgur,"Burrger"));
        arrayList.add(new Horizontal_itemModel(R.drawable.pizza,"Pizza"));
        arrayList.add(new Horizontal_itemModel(R.drawable.pasta,"Pasta"));
        arrayList.add(new Horizontal_itemModel(R.drawable.noodle,"Noodle"));
        arrayList.add(new Horizontal_itemModel(R.drawable.icecrream,"Ice-Cream"));

        horiz_recycler = new Horiz_Recycler_Adapter(this,getActivity(),arrayList);
        h_recycler.setAdapter(horiz_recycler);
        h_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));


        // ********************* Vertical Recycler View ***************************//

        mainModel2 = new ArrayList<>();

        vertical_recycler = new Vertical_Recycler_Adapter(getActivity(),mainModel2);
        v_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        v_recycler.setAdapter(vertical_recycler);
        v_recycler.setHasFixedSize(true);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        return view;


    }

    @Override
    public void callback(int position, ArrayList<Vertical_itemModel> list) {

        vertical_recycler = new Vertical_Recycler_Adapter(getContext(),list);
        vertical_recycler.notifyDataSetChanged();
        v_recycler.setAdapter(vertical_recycler);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        vertical_recycler.getFilter().filter(text);
        return false;
    }
}