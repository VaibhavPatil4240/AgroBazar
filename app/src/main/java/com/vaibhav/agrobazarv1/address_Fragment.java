package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link address_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class address_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean calledByBuyNow=false;
    HomePageDatum productSelected=null;
    String amountToBuy="";
    public address_Fragment() {
        // Required empty public constructor
    }
    public address_Fragment(boolean b,HomePageDatum pr,String am)
    {
        productSelected=pr;
        amountToBuy=am;
        calledByBuyNow=b;
    }
    addressFrag AF;

    interface addressFrag {
        String getUser();

        void loadingProcess(int b, String s);
    }
    addressAdapter adapter;
    boolean updateAddress=false;
    String addId="";
    Button addAddressButton, continueButton, addAdresscardBt, cancelAddaddress;
    EditText addressText, landmark, state, ditrict, pincode;
    ImageView bkIMG, bkIMG2;
    CardView addAddressCard;
    RecyclerView recview;
    AddressDatum[] data = null;
    AddressDatum selectedAddress;
    private static final String url = "https://agrobazarvaibhavpatil.000webhostapp.com/Customer/fetchAddresses.php";


    public static address_Fragment newInstance(String param1, String param2) {
        address_Fragment fragment = new address_Fragment();
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
        View v = inflater.inflate(R.layout.fragment_address, container, false);
        addressText = v.findViewById(R.id.addressEdText);
        state = v.findViewById(R.id.stateText);
        ditrict = v.findViewById(R.id.districtText);
        landmark = v.findViewById(R.id.landmarkText);
        pincode = v.findViewById(R.id.pincode);
        addAdresscardBt = v.findViewById(R.id.AddAddressCard);
        addAddressCard = v.findViewById(R.id.addAddresscard);
        cancelAddaddress = v.findViewById(R.id.cancelAddAddress);
        bkIMG = v.findViewById(R.id.bkImg);
        addAddressCard.setVisibility(View.INVISIBLE);
        bkIMG.setVisibility(View.INVISIBLE);
        addAddressButton = v.findViewById(R.id.addAddressButton);
        continueButton = v.findViewById(R.id.continuebt);
        if(!calledByBuyNow)
        {
            continueButton.setVisibility(View.INVISIBLE);
        }
        String userdID = AF.getUser();
        String url2 = url + "?t1=" + userdID;
        recview = v.findViewById(R.id.recViewAddress);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        StringRequest request = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("0")) {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    data = gson.fromJson(response, AddressDatum[].class);
                }
                adapter = new addressAdapter(data, getContext(), (MainActivity) getActivity(), address_Fragment.this);
                recview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = new Volley().newRequestQueue(getContext());
        queue.add(request);
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdresscardBt.setText("Add");
                addAddressCard.setVisibility(View.VISIBLE);
                bkIMG.setVisibility(View.VISIBLE);
                updateAddress=false;
                addId="";
            }
        });
        addAdresscardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdress(updateAddress,addId);
            }
        });
        cancelAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddressCard.setVisibility(View.INVISIBLE);
                bkIMG.setVisibility(View.INVISIBLE);
            }
        });
        bkIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddressCard.setVisibility(View.INVISIBLE);
                bkIMG.setVisibility(View.INVISIBLE);
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               selectedAddress= adapter.returnAddressSelected();
               if(selectedAddress==null)
               {
                   Toast.makeText(getContext(),"Please select Address",Toast.LENGTH_SHORT).show();
               }
               else
               {
                   ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container,new buyNow_Fragment(productSelected,selectedAddress,amountToBuy)).addToBackStack(null).commit();
               }
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof addressFrag) {
            AF = (addressFrag) context;
        } else {
            throw new RuntimeException(context.toString() + "Must implement fragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        AF = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public void addAdress(boolean b,String addID) {
        String address = addressText.getText().toString().trim();
        String districtS = ditrict.getText().toString().trim();
        String stateS = state.getText().toString().trim();
        String landmarkS = landmark.getText().toString().trim();
        String pinc = pincode.getText().toString().trim();
        boolean flagIncomplete = false;
        if (address.length() < 1) {
            Toast.makeText(getContext(), "Please enter Adress", Toast.LENGTH_SHORT).show();
            flagIncomplete = true;
        }
        if (districtS.length() < 1 && !flagIncomplete) {
            Toast.makeText(getContext(), "Please enter District", Toast.LENGTH_SHORT).show();
            flagIncomplete = true;
        }
        if (stateS.length() < 1 && !flagIncomplete) {
            Toast.makeText(getContext(), "Please enter State", Toast.LENGTH_SHORT).show();
            flagIncomplete = true;
        }
        if (landmarkS.length() < 1 && !flagIncomplete) {
            Toast.makeText(getContext(), "Please enter Landmark", Toast.LENGTH_SHORT).show();
            flagIncomplete = true;
        }
        if (pinc.length() != 6 && !flagIncomplete) {
            Toast.makeText(getContext(), "Please enter valid Pin code", Toast.LENGTH_SHORT).show();
            flagIncomplete = true;
        }
        if (pinc.length() < 1 && !flagIncomplete) {
            Toast.makeText(getContext(), "Please enter Pin code", Toast.LENGTH_SHORT).show();
            flagIncomplete = true;
        }
        if (!flagIncomplete) {
            if (b)
            {
                AF.loadingProcess(1, "Updating Address");
            }
            else
            {
                AF.loadingProcess(1, "Adding Address");
            }
            String url = "https://agrobazarvaibhavpatil.000webhostapp.com/Customer/insertAddress.php";
            String qry = "?t1=" + AF.getUser() + "&t2=" + address + "-" + districtS + "-" + stateS + "&t3=" + landmarkS + "&t4=" + pinc;
            if(b)
            {
                url = "https://agrobazarvaibhavpatil.000webhostapp.com/Customer/updateAddress.php";
                 qry = "?t1=" + addID + "&t2=" + address + "-" + districtS + "-" + stateS + "&t3=" + landmarkS + "&t4=" + pinc;
            }

            class insertAdd extends AsyncTask<String, Void, String> {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    addAddressCard.setVisibility(View.INVISIBLE);
                    bkIMG.setVisibility(View.INVISIBLE);
                    AF.loadingProcess(0, " ");
                    setUserVisibleHint(true);
                    if(b)
                    {
                        if (s.equals("1")) {
                            Toast.makeText(getContext(), "Address Updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Some error occurred please retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        if (s.equals("1")) {
                            Toast.makeText(getContext(), "Address added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Some error occurred please retry", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                protected String doInBackground(String... strings) {
                    String furl = strings[0];
                    try {
                        URL url = new URL(furl);
                        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        return br.readLine();
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                }
            }
            insertAdd insertadd = new insertAdd();
            insertadd.execute(url + qry);
        }
    }
    void callByUpdateAddress(boolean b,String id)
    {
        addAdresscardBt.setText("Update");
        addAddressCard.setVisibility(View.VISIBLE);
        bkIMG.setVisibility(View.VISIBLE);
        updateAddress=b;
        addId=id;
    }
}