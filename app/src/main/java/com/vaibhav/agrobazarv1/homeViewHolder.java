package com.vaibhav.agrobazarv1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class homeViewHolder extends RecyclerView.ViewHolder {
    ImageView prod;
    TextView name,cat,price;
    public homeViewHolder(@NonNull View itemView) {
        super(itemView);
        prod=itemView.findViewById(R.id.img1);
        name=itemView.findViewById(R.id.headerText);
        cat=itemView.findViewById(R.id.CategoryText);
        price=itemView.findViewById(R.id.prodPrice);

    }
}
