package com.vaibhav.agrobazarv1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.rilixtech.CountryCodePicker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAcc_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAcc_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateAcc_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAcc_Fragment.
     */
    // TODO: Rename and change types and number of parameters

    EditText fname,lname,pwd,pwdC,phno,gmail;
    TextView hiddenText;
    Button cAccbut;
    CountryCodePicker ccp;
    public static CreateAcc_Fragment newInstance(String param1, String param2) {
        CreateAcc_Fragment fragment = new CreateAcc_Fragment();
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
    private createAcc interFace;
   public interface createAcc{
       void loadingProcess(int b);
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).isCreated(true);
        View v= inflater.inflate(R.layout.fragment_create_acc, container, false);
        cAccbut= v.findViewById(R.id.buttonCreateAcc);
        interFace.loadingProcess(0);
        cAccbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interFace.loadingProcess(1);
                createAccount(v);
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof createAcc){
            interFace=(createAcc) context;
        }else {
            throw new RuntimeException(context.toString()+"Must implement fragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interFace=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).isCreated(false);
    }
    public void createAccount(View v){
        hiddenText=v.findViewById(R.id.hiddenText);
        ccp=v.findViewById(R.id.ccp);
        fname=v.findViewById(R.id.fname);
        lname=v.findViewById(R.id.lname);
        phno=v.findViewById(R.id.phnoText);
        pwd=v.findViewById(R.id.passwordText);
        pwdC=v.findViewById(R.id.rtpasswordText);
        gmail=v.findViewById(R.id.gmailText);
        String fn,ln,pn,pass,passC,gm;
        fn=fname.getText().toString();
        ln=lname.getText().toString();
        pn=phno.getText().toString();
        pass=pwd.getText().toString();
        passC=pwdC.getText().toString();
        gm=gmail.getText().toString();
        hiddenText.setText(pn);
        ccp.registerPhoneNumberTextView(hiddenText);
        boolean pwdCheck=true,textCheck=true,phnocheck=false;
        final boolean registered=false;
        if(!(fn.length()>0 && ln.length()>0 && pn.length()>0 && pass.length()>0))
        {
            if(fn.length()<1)
            {
                Toast.makeText(getContext(),"Please Enter First name",Toast.LENGTH_SHORT).show();
            }
            else if(ln.length()<1)
            {
                Toast.makeText(getContext(),"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            }
            else if(pn.length()<1)
            {
                Toast.makeText(getContext(),"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(),"Please Enter Password",Toast.LENGTH_SHORT).show();
            }
            textCheck=false;
        }
        if (!(pass.equals(passC))) {
            Toast.makeText(getContext(), "Confirm Password correctly", Toast.LENGTH_SHORT).show();
            pwdCheck = false;
        }
        if (textCheck && pwdCheck)
        {
            interFace.loadingProcess(1);
            String url="https://agrobazarvaibhavpatil.000webhostapp.com/LogIn/Check_Registery.php";
            String qry="?t1="+pn.trim();
            Log.d("qry",qry);
            class createaccproc extends AsyncTask<String,Void,String>
            {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    interFace.loadingProcess(0);
                    if(s.trim().equals("0"))
                    {
                        Bundle bundle=new Bundle();
                        bundle.putString("pn",ccp.getFullNumberWithPlus().trim());
                        bundle.putString("fn",fn.trim());
                        bundle.putString("ln",ln.trim());
                        bundle.putString("gm",gm.trim());
                        bundle.putString("pass",pass.trim());
                        Verify_fragment vf=new Verify_fragment();
                        vf.setArguments(bundle);
                        ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container,vf).commit();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Phone Number is already registered", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                protected String doInBackground(String... strings) {
                    String furl=strings[0];
                    try {
                        URL url=new URL(furl);
                        HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        return br.readLine().toString().trim();
                    } catch (Exception e) {
                        return e.getMessage();
                    }
                }
            }
            createaccproc acc=new createaccproc();
            acc.execute(url+qry);
        }

    }
}