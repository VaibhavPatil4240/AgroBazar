package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class addressAdapter extends RecyclerView.Adapter<addressViewHolder> {
    AddressDatum[] data;
    AddressDatum returnAdd=null;
    Context context;
    FragmentActivity nxtFrg;
    address_Fragment fragment;
    boolean rbCheck;
    RadioButton rbSaved=null;

    public addressAdapter(AddressDatum[] data, Context context, FragmentActivity nxtFrg,address_Fragment fragment) {
        this.data = data;
        this.context = context;
        this.nxtFrg = nxtFrg;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public addressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.singleaddresse,parent,false);
        return new addressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull addressViewHolder holder, int position) {
        AddressDatum datum=data[position];
        holder.add.setText("Address: "+datum.getAddress());
        holder.landmark.setText("Landmark: "+datum.getLandMark());
        holder.pincode.setText("Pincode:"+datum.getPincode());
        rbCheck=false;
        holder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbCheck)
                {
                    holder.rb.setChecked(false);
                    rbCheck=false;
                    returnAdd=null;
                }
                else
                {
                    if(rbSaved!=null)
                    {
                        rbSaved.setChecked(false);
                        rbCheck=false;
                    }
                    rbSaved=holder.rb;
                    holder.rb.setChecked(true);
                    rbCheck=true;
                    returnAdd=datum;
                }
            }
        });
        holder.deleteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAddress(datum.getId());
            }
        });
        holder.editAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAddress(datum.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data==null)
            return 0;
        return data.length;
    }
    public void deleteAddress(String id)
    {
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Customer/deleteAdedress.php?t1="+id;
        class deleteRequest extends AsyncTask<String,Void,String>{
            @Override
            protected String doInBackground(String... strings) {
                String furl=strings[0];
                try {
                    URL url=new URL(furl);
                    HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return br.readLine();
                } catch (Exception e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                fragment.setUserVisibleHint(true);
                if (s.equals("1"))
                {
                    Toast.makeText(context,"Address deleted successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Some error occured retry",Toast.LENGTH_SHORT).show();
                }
            }
        }
        deleteRequest delete=new deleteRequest();
        delete.execute(url);
    }
    public void editAddress(String addID) {
        fragment.callByUpdateAddress(true,addID);
    }
    public AddressDatum returnAddressSelected()
    {
        return returnAdd;
    }
}
