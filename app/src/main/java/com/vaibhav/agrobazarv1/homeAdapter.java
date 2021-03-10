package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class homeAdapter extends RecyclerView.Adapter<homeViewHolder> {

    HomePageDatum [] data;
    Context context;
    final static String imgURL="https://agrobazarvaibhavpatil.000webhostapp.com/ProductImages/";

    public homeAdapter(HomePageDatum[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.homeproduct,parent,false);
        return new homeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {
        HomePageDatum datum=data[position];
        holder.name.setText(datum.getName());
        holder.cat.setText("Category: "+datum.getCategory());
        holder.price.setText("Price: "+datum.getPrice()+"RS/"+datum.getUnit());
        Glide.with(holder.prod.getContext()).load(imgURL+datum.getImg()).into(holder.prod);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
