package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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
    int ratings=0;
    boolean stockFlag=true;
    View bkView;
    TextView name,dileveryExp,disc,price,cancel,rateSeller2;
    ImageView prodImg,st1,st2,st3,st4,st5,bkImg,rst1,rst2,rst3,rst4,rst5;
    boolean s1,s2,s3,s4,s5;
    Button rateSeller;
    final static String imgURL="https://agrobazarvaibhavpatil.000webhostapp.com/ProductImages/";
    public HomePageDatum data;
    singleMainListner smListner;
    interface singleMainListner
    {
        String getUser();
    }
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
        s1=s2=s3=s4=s5=false;
        name=view.findViewById(R.id.prodName);
        name.setText(data.getName());
        price=view.findViewById(R.id.price);
        price.setText("Price :"+data.getPrice()+" ₹/ "+data.getUnit());
        disc=view.findViewById(R.id.disc);
        disc.setText(data.getDescription());
        prodImg=view.findViewById(R.id.prodImg);
        Glide.with(prodImg.getContext()).load(imgURL+data.getImg()).into(prodImg);
        dileveryExp=view.findViewById(R.id.dilveryExp);
        bkImg=view.findViewById(R.id.BackgroundImg);
        bkView=view.findViewById(R.id.bkView);
        cancel=view.findViewById(R.id.cancel);
        rst1=view.findViewById(R.id.rStar1);
        rst2=view.findViewById(R.id.rStar2);
        rst3=view.findViewById(R.id.rStar3);
        rst4=view.findViewById(R.id.rStar4);
        rst5=view.findViewById(R.id.rStar5);
        rateSeller=view.findViewById(R.id.rateSeller);
        rateSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateSeller(true,false);
            }
        });
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
        dateTime();
        rateSeller2=view.findViewById(R.id.rateSeller1);
        rateSeller2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateSeller(false,true);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateSeller(false,false);
            }
        });
        rst1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSrcRstar(1,s1);
            }
        });
        rst2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSrcRstar(2,s2);
            }
        });
        rst3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSrcRstar(3,s3);
            }
        });
        rst4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSrcRstar(4,s4);

            }
        });
        rst5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSrcRstar(5,s5);
            }
        });
        RateSeller(false,false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof singleMainListner){
            smListner=(singleMainListner) context;
        }else {
            throw new RuntimeException(context.toString()+"Must implement fragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        smListner=null;
    }

    void dateTime()
    {
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/Orders/SystemDateTime.php";
        StringRequest request= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setDisc(response);
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


    void setDisc(String s)
    {
        String d[]=s.split("&");
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
        if(Integer.parseInt(data.getStock())==0)
        {
            dileveryExp.setTextColor(ContextCompat.getColor(getContext(),R.color.red));
            dileveryExp.setText("Out of Stock! Unable to dilever the product");
            stockFlag=false;
        }
        else
        {
            Log.d("Time", String.valueOf(Hr));
            dileveryExp.setTextColor(ContextCompat.getColor(getContext(),R.color.green));
            if(Hr>6 && Hr<16)
            {
                dileveryExp.setText("In stock\nDelivery expected by 5-5:30 PM Today");
            }
            else
            {
                dileveryExp.setText("In stock\nDelivery expected by 8-8:30 AM Tomorrow");
            }
        }


    }
    void RateSeller(boolean b,boolean c)
    {
        if(b)
        {
            bkView.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            bkImg.setVisibility(View.VISIBLE);
            rateSeller2.setVisibility(View.VISIBLE);
            rateSeller2.bringToFront();
            rst1.setVisibility(View.VISIBLE);
            rst2.setVisibility(View.VISIBLE);
            rst3.setVisibility(View.VISIBLE);
            rst4.setVisibility(View.VISIBLE);
            rst5.setVisibility(View.VISIBLE);
        }
        else
        {
            bkView.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            bkImg.setVisibility(View.INVISIBLE);
            rateSeller2.setVisibility(View.GONE);
            rst1.setVisibility(View.INVISIBLE);
            rst2.setVisibility(View.INVISIBLE);
            rst3.setVisibility(View.INVISIBLE);
            rst4.setVisibility(View.INVISIBLE);
            rst5.setVisibility(View.INVISIBLE);
            if(c) {
                if (s5) {
                    ratings = 5;
                } else if (s4) {
                    ratings = 4;
                } else if (s3) {
                    ratings = 3;
                } else if (s2) {
                    ratings = 2;
                } else if (s1) {
                    ratings = 1;
                } else {
                    ratings = 0;
                }
                insertRatings();
            }
        }
    }
    public void setSrcRstar(int c,boolean b)
    {
        switch (c)
        {
            case 1:
                if(b)
                {
                    rst1.setImageResource(R.drawable.starratingfillcopy);
                    rst2.setImageResource(R.drawable.starratingfillcopy);
                    rst3.setImageResource(R.drawable.starratingfillcopy);
                    rst4.setImageResource(R.drawable.starratingfillcopy);
                    rst5.setImageResource(R.drawable.starratingfillcopy);
                    s1=s2=s3=s4=s5=false;
                }
                else
                {
                    rst1.setImageResource(R.drawable.stars);
                    s1=true;
                }
                break;
            case 2:
                if(b)
                {
                    rst2.setImageResource(R.drawable.starratingfillcopy);
                    rst3.setImageResource(R.drawable.starratingfillcopy);
                    rst4.setImageResource(R.drawable.starratingfillcopy);
                    rst5.setImageResource(R.drawable.starratingfillcopy);
                    s2=s3=s4=s5=false;
                }
                else
                {
                    rst1.setImageResource(R.drawable.stars);
                    rst2.setImageResource(R.drawable.stars);
                    s1=s2=true;
                }
                break;
            case 3:
                if(b)
                {
                    rst3.setImageResource(R.drawable.starratingfillcopy);
                    rst4.setImageResource(R.drawable.starratingfillcopy);
                    rst5.setImageResource(R.drawable.starratingfillcopy);
                    s3=s4=s5=false;
                }
                else
                {
                    rst1.setImageResource(R.drawable.stars);
                    rst2.setImageResource(R.drawable.stars);
                    rst3.setImageResource(R.drawable.stars);
                    s1=s2=s3=true;
                }
                break;
            case 4:
                if(b)
                {
                    rst4.setImageResource(R.drawable.starratingfillcopy);
                    rst5.setImageResource(R.drawable.starratingfillcopy);
                    s4=s5=false;
                }
                else {
                    rst1.setImageResource(R.drawable.stars);
                    rst2.setImageResource(R.drawable.stars);
                    rst3.setImageResource(R.drawable.stars);
                    rst4.setImageResource(R.drawable.stars);
                    s1 = s2 = s3 = s4 = true;
                }
                break;
            case 5:
                if(b)
                {
                    rst5.setImageResource(R.drawable.starratingfillcopy);
                    s5=false;
                }
                else {
                    rst1.setImageResource(R.drawable.stars);
                    rst2.setImageResource(R.drawable.stars);
                    rst3.setImageResource(R.drawable.stars);
                    rst4.setImageResource(R.drawable.stars);
                    rst5.setImageResource(R.drawable.stars);
                    s1 = s2 = s3 = s4 =s5= true;
                }
                break;
            default:
                break;

        }
    }
    public void insertRatings()
    {
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/SellerCustomer/ratings.php";
        String t1=smListner.getUser();
        String t2=data.getSellerID();
        String t3=String.valueOf(ratings);
        String qry="?t1="+t1.trim()+"&t2="+t2.trim()+"&t3="+t3.trim();
        class ratingsSeller extends AsyncTask<String,Void,String>
        {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getContext(),"Submitted",Toast.LENGTH_SHORT).show();
            }
            @Override
            protected String doInBackground(String... strings){
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
        ratingsSeller rs=new ratingsSeller();
        rs.execute(url+qry);
    }
}