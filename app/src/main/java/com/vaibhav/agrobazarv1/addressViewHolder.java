package com.vaibhav.agrobazarv1;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class addressViewHolder extends RecyclerView.ViewHolder {
    TextView add,landmark,pincode;
    RadioButton rb;
    Button editAdd,deleteAdd;
    public addressViewHolder(@NonNull View itemView) {
        super(itemView);
        add=itemView.findViewById(R.id.address);
        landmark=itemView.findViewById(R.id.landmark);
        pincode=itemView.findViewById(R.id.pincode);
        rb=itemView.findViewById(R.id.radioButton2);
        editAdd=itemView.findViewById(R.id.editAdd);
        deleteAdd=itemView.findViewById(R.id.deleteAdd);
    }
}
