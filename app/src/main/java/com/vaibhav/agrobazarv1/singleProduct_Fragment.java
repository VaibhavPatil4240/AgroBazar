package com.vaibhav.agrobazarv1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link singleProduct_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class singleProduct_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String s;
    boolean stockFlag=true;
    TextView name,dileveryExp,disc,price;
    ImageView prodImg,st1,st2,st3,st4,st5;
    final static String imgURL="https://agrobazarvaibhavpatil.000webhostapp.com/ProductImages/";
    public HomePageDatum data;
    public singleProduct_Fragment() {
        // Required empty public constructor
    }
    public singleProduct_Fragment(HomePageDatum data){
        this.data=data;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment singleProduct_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static singleProduct_Fragment newInstance(String param1, String param2) {
        singleProduct_Fragment fragment = new singleProduct_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view=inflater.inflate(R.layout.fragment_single_product, container, false);
        name=view.findViewById(R.id.prodName);
        name.setText(data.getName());
        price=view.findViewById(R.id.price);
        price.setText("Price :"+data.getPrice()+" ₹/ "+data.getUnit());
        disc=view.findViewById(R.id.disc);
        disc.setText(data.getDescription());
        prodImg=view.findViewById(R.id.prodImg);
        Glide.with(prodImg.getContext()).load(imgURL+data.getImg()).into(prodImg);
        dileveryExp=view.findViewById(R.id.dilveryExp);
        st1=view.findViewById(R.id.star1);
        st2=view.findViewById(R.id.star2);
        st3=view.findViewById(R.id.star3);
        st4=view.findViewById(R.id.star4);
        st5=view.findViewById(R.id.star5);
        switch (Integer.parseInt(data.getRating()))
        {
            case 0:
                st1.setVisibility(View.INVISIBLE);
                st2.setVisibility(View.INVISIBLE);
                st3.setVisibility(View.INVISIBLE);
                st4.setVisibility(View.INVISIBLE);
                st5.setVisibility(View.INVISIBLE);
                break;
            case 1:
                st1.setVisibility(View.VISIBLE);
                st2.setVisibility(View.INVISIBLE);
                st3.setVisibility(View.INVISIBLE);
                st4.setVisibility(View.INVISIBLE);
                st5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                st1.setVisibility(View.VISIBLE);
                st2.setVisibility(View.VISIBLE);
                st3.setVisibility(View.INVISIBLE);
                st4.setVisibility(View.INVISIBLE);
                st5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                st1.setVisibility(View.VISIBLE);
                st2.setVisibility(View.VISIBLE);
                st3.setVisibility(View.VISIBLE);
                st4.setVisibility(View.INVISIBLE);
                st5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                st1.setVisibility(View.VISIBLE);
                st2.setVisibility(View.VISIBLE);
                st3.setVisibility(View.VISIBLE);
                st4.setVisibility(View.VISIBLE);
                st5.setVisibility(View.INVISIBLE);
                break;
            case 5:
                st1.setVisibility(View.VISIBLE);
                st2.setVisibility(View.VISIBLE);
                st3.setVisibility(View.VISIBLE);
                st4.setVisibility(View.VISIBLE);
                st5.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        dateTime(view);
        return view;
    }
    void dateTime(View v)
    {
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Orders/SystemDateTime.php";
        StringRequest request= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setDisc(response,v);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setDisc("",v);
            }
        });
        RequestQueue queue= new Volley().newRequestQueue(getContext());
        queue.add(request);
    }


    void setDisc(String s,View view)
    {
        String d[]=s.split("&");
        String dt=d[0];
        String time[]=d[1].split(":");
        String hr,min,sec,ampm;
        hr=time[0];
        min=time[1];
        sec=time[2];
        ampm=sec.substring(2);
        if(Integer.parseInt(data.getStock())==0)
        {
            dileveryExp.setText("Out of Stock! Unable to dilever the product");
            dileveryExp.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
            stockFlag=false;
        }
        else
        {
            dileveryExp.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
            if(Integer.parseInt(hr)>6 && (Integer.parseInt(hr)<15) )
            {
                Log.d("slot","hr : "+hr);
                dileveryExp.setText("In stock\nDelivery expected by 5-5:30 PM Today");
            }
            else
            {
                dileveryExp.setText("In stock\n Delivery expected by 8-8:30 AM Tomorrow");
            }
        }


    }
}