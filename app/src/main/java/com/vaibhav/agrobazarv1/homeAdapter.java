package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class  homeAdapter extends RecyclerView.Adapter<homeViewHolder> {

    HomePageDatum [] data;
    Context context;
    FragmentActivity fa;
    final static String imgURL="https://agrobazarvaibhavpatil.000webhostapp.com/ProductImages/";

    public homeAdapter(HomePageDatum[] data, Context context, FragmentActivity fa) {
        this.data = data;
        this.context = context;
        this.fa=fa;
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
        holder.price.setText("Price: "+datum.getPrice()+" ₹/ "+datum.getUnit());
        if(Integer.parseInt(datum.getStock())==0)
        {
            holder.stock.setText("Out of stock");
            holder.stock.setTextColor(ContextCompat.getColor(context,R.color.red));
        }
        Glide.with(holder.prod.getContext()).load(imgURL+datum.getImg()).into(holder.prod);
        switch (Integer.parseInt(datum.getRating()))
        {
            case 0:
                holder.st1.setVisibility(View.INVISIBLE);
                holder.st2.setVisibility(View.INVISIBLE);
                holder.st3.setVisibility(View.INVISIBLE);
                holder.st4.setVisibility(View.INVISIBLE);
                holder.st5.setVisibility(View.INVISIBLE);
                break;
            case 1:
                holder.st1.setVisibility(View.VISIBLE);
                holder.st2.setVisibility(View.INVISIBLE);
                holder.st3.setVisibility(View.INVISIBLE);
                holder.st4.setVisibility(View.INVISIBLE);
                holder.st5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                holder.st1.setVisibility(View.VISIBLE);
                holder.st2.setVisibility(View.VISIBLE);
                holder.st3.setVisibility(View.INVISIBLE);
                holder.st4.setVisibility(View.INVISIBLE);
                holder.st5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                holder.st1.setVisibility(View.VISIBLE);
                holder.st2.setVisibility(View.VISIBLE);
                holder.st3.setVisibility(View.VISIBLE);
                holder.st4.setVisibility(View.INVISIBLE);
                holder.st5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                holder.st1.setVisibility(View.VISIBLE);
                holder.st2.setVisibility(View.VISIBLE);
                holder.st3.setVisibility(View.VISIBLE);
                holder.st4.setVisibility(View.VISIBLE);
                holder.st5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                holder.st1.setVisibility(View.VISIBLE);
                holder.st2.setVisibility(View.VISIBLE);
                holder.st3.setVisibility(View.VISIBLE);
                holder.st4.setVisibility(View.VISIBLE);
                holder.st5.setVisibility(View.VISIBLE);
                break;
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fa.getSupportFragmentManager().beginTransaction().replace(R.id.container,new singleProduct_Fragment(datum)).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
