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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link forgotPassword_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class forgotPassword_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public forgotPassword_fragment() {
        // Required empty public constructor
    }


    forgotPass fp;
    interface forgotPass{
        void loadingProcess(int b,String s);
    };
    EditText phno,otp,newpass,newpassC;
    Button verify,changePass;
    String ccphno,otpid;
    FirebaseAuth mAuth;
    boolean getotp;

    public static forgotPassword_fragment newInstance(String param1, String param2) {
        forgotPassword_fragment fragment = new forgotPassword_fragment();
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
        View v= inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ((MainActivity)getActivity()).isCreated(true);
        phno=v.findViewById(R.id.phnoText);
        verify=v.findViewById(R.id.getOTP);
        otp=v.findViewById(R.id.otpText);
        newpass=v.findViewById(R.id.newPass);
        newpassC=v.findViewById(R.id.newPass2);
        changePass=v.findViewById(R.id.Chnagepassb);
        newpassC.setVisibility(View.INVISIBLE);
        newpass.setVisibility(View.INVISIBLE);
        changePass.setVisibility(View.INVISIBLE);
        otp.setVisibility(View.INVISIBLE);
        mAuth=FirebaseAuth.getInstance();
        getotp=true;
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getotp)
                {
                    verify(phno.getText().toString());
                    getotp=false;
                }
                else
                {
                    fp.loadingProcess(0,"");
                    if(otp.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(),"Please Enter OTP",Toast.LENGTH_SHORT).show();
                    }
                    else if(otp.getText().toString().length()!=6)
                    {
                        Toast.makeText(getContext(),"Invalid OTP",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                    }
                }

            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newpas=newpass.getText().toString().trim();
                String newpasC=newpassC.getText().toString().trim();
                if(!(newpas.equals(newpasC)))
                {
                    Toast.makeText(getContext(),"Confirm password correctly",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    changePassword(phno.getText().toString().trim(),newpas);
                }
            }
        });
        return v;
    }

    private void verify(String pn) {
            String url="https://agrobazarvaibhavpatil.000webhostapp.com/LogIn/verifyNum.php";
            String qry="?t1="+pn.trim();
            Log.d("qry",qry);
            fp.loadingProcess(1,"Verifying Number");
            class createaccproc extends AsyncTask<String,Void,String>
            {
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    fp.loadingProcess(0,"");
                    if(s.trim().equals("1"))
                    {
                        Toast.makeText(getContext(),"Account not found", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        ccphno="+"+s+pn;
                        intiateOTP();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof forgotPass){
            fp=(forgotPass) context;
        }else {
            throw new RuntimeException(context.toString()+"Must implement fragment listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fp=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).isCreated(false);
    }
    private void intiateOTP() {
        fp.loadingProcess(1,"getting OTP");
        otp.setVisibility(View.VISIBLE);
        phno.setVisibility(View.INVISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(ccphno, 60, TimeUnit.SECONDS, getActivity(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                otpid=s;
                fp.loadingProcess(0,"");
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fp.loadingProcess(1,"Verifying OTP");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fp.loadingProcess(0,"");
                            Toast.makeText(getContext(),"Verified Successfully",Toast.LENGTH_SHORT).show();
                            otp.setVisibility(View.INVISIBLE);
                            phno.setVisibility(View.INVISIBLE);
                            verify.setVisibility(View.INVISIBLE);
                            changePass.setVisibility(View.VISIBLE);
                            newpass.setVisibility(View.VISIBLE);
                            newpassC.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(getContext(),"Failed to Verify",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void changePassword(String np,String pas)
    {
        fp.loadingProcess(0,"");
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/LogIn/changePass.php";
        String qry="?t1="+np.trim()+"&t2="+pas.trim();
        Log.d("qry",qry);
        class createaccproc extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                fp.loadingProcess(0,"");
                if(s.trim().equals("1"))
                {
                    Toast.makeText(getContext(),"Password changed successfully", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container,new LogIn_Fragment()).commit();
                }
                else
                {
                    Toast.makeText(getContext(),"Some error occured please retry", Toast.LENGTH_SHORT).show();
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