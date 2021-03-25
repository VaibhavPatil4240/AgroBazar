package com.vaibhav.agrobazarv1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link buyNow_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buyNow_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    HomePageDatum product;
    AddressDatum address;
    String Amount;
    int tORt=0;
    int firstORsecond=0;

    public buyNow_Fragment() {
        // Required empty public constructor
    }
    public buyNow_Fragment(HomePageDatum product,AddressDatum address,String amount)
    {
        this.address=address;
        this.Amount=amount;
        this.product=product;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buyNow_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static buyNow_Fragment newInstance(String param1, String param2) {
        buyNow_Fragment fragment = new buyNow_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    TextView slot1,slot2,receipt,Address;
    RadioButton slot1Rb,slot2Rb;
    Button placeOrderButton;
    boolean slot1Selected=false,slot2selected=false,oneSelected=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_buy_now, container, false);
        dateTime();
        slot1=v.findViewById(R.id.slot1);
        slot2=v.findViewById(R.id.slot2);
        slot1Rb=v.findViewById(R.id.slot1Button);
        slot2Rb=v.findViewById(R.id.slot2Button);
        Address=v.findViewById(R.id.finalAddress);
        receipt=v.findViewById(R.id.receipt);
        placeOrderButton=v.findViewById(R.id.placeOrderButton);
        Address.setText(address.getAddress()+"\n"+address.getLandMark()+"\n"+address.getPincode());
        receipt.setText(product.getName()+"     "+product.getPrice()+" ₹/ "+product.getUnit()+"\n"+"Total      "+Integer.parseInt(Amount)*Integer.parseInt(product.getPrice())+"₹");
        slot1Rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(slot1Selected)
                {
                    slot1Rb.setChecked(false);
                    slot1Selected=false;
                }
                else
                {
                    if(slot2selected)
                    {
                        slot2Rb.setChecked(false);
                        slot2selected=false;
                    }
                    slot1Rb.setChecked(true);
                    slot1Selected=true;
                }
            }
        });
        slot2Rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(slot2selected)
                {
                    slot2Rb.setChecked(false);
                    slot2selected=false;
                }
                else
                {
                    if(slot1Selected)
                    {
                        slot1Rb.setChecked(false);
                        slot1Selected=false;
                    }
                    slot2Rb.setChecked(true);
                    slot2selected=true;
                }

            }
        });
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(slot2selected || slot1Selected)
                {
                    placeOrder();
                }
                else
                {
                    Toast.makeText(getContext(),"Please select delivery time",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public void placeOrder() {
        String t1,t2,t3,t4,t5,t6,t7;
        t1=product.getSellerID();
        t2=address.getCustomerID();
        t3=product.getProdID();
        t4=address.getAddress()+"-"+address.getLandMark()+"-"+address.getPincode();
        t5=product.getPrice()+"/"+product.getUnit();
        t6=Amount+" "+product.getUnit();
        if(slot1Selected)
        {
            if(tORt==0)
             firstORsecond=1;
            else
                firstORsecond=0;
        }
        else
        {
            if(tORt==0)
                firstORsecond=0;
            else
                firstORsecond=1;
        }
        t7=String.valueOf(tORt)+"-"+String.valueOf(firstORsecond);
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Orders/placeOrder.php";
        String qry="?t1="+t1+"&t2="+t2+"&t3="+t3+"&t4="+t4+"&t5="+t5+"&t6="+t6+"&t7="+t7;
        Log.d("placeOrder",url+qry);
        class dbprocess extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("1")){
                    Toast.makeText(getContext(),"Order Placed successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Some Error occurred",Toast.LENGTH_SHORT).show();
                }
            }

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
        }
        dbprocess dp=new dbprocess();
        dp.execute(url+qry);
    }

    void dateTime()
    {
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Orders/SystemDateTime.php";
        StringRequest request= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String d[]=response.split("&");
                String dt=d[0];
                String time[]=d[1].split(":");
                String hr,min,sec,ampm;
                hr=time[0];
                min=time[1];
                sec=time[2];
                ampm=sec.substring(2);
                int Hr=Integer.parseInt(hr);
                Log.d("Time",ampm);
                if(ampm.equals("pm"))
                {
                    Hr=Hr+12;
                }
                slot1.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
                slot2.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
                if(Hr>6 && Hr<16)
                {
                    tORt=0;
                    slot1.setText("Deliver on 5-5:30 PM Today");
                    slot2.setText("Deliver on 8-8:30 AM Tomorrow");
                }
                else
                {
                    tORt=1;
                    slot2.setText("Deliver on 5-5:30 PM Tomorrow");
                    slot1.setText("Deliver on 8-8:30 AM Tomorrow");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Weak Internet connection",Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= new Volley().newRequestQueue(getContext());
        queue.add(request);
    }
}