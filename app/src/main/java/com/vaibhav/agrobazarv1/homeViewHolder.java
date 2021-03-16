package com.vaibhav.agrobazarv1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class homeViewHolder extends RecyclerView.ViewHolder {
    ImageView prod,st1,st2,st3,st4,st5;
    TextView name,cat,price,stock;
    CardView card;
    public homeViewHolder(@NonNull View itemView) {
        super(itemView);
        prod=itemView.findViewById(R.id.img1);
        name=itemView.findViewById(R.id.headerText);
        cat=itemView.findViewById(R.id.CategoryText);
        price=itemView.findViewById(R.id.prodPrice);
        stock= itemView.findViewById(R.id.inStock);
        card= itemView.findViewById(R.id.homeCardView);
        st1=itemView.findViewById(R.id.star1);
        st2=itemView.findViewById(R.id.star2);
        st3=itemView.findViewById(R.id.star3);
        st4=itemView.findViewById(R.id.star4);
        st5=itemView.findViewById(R.id.star5);

    }
}
