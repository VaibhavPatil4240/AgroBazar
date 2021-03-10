package com.vaibhav.agrobazarv1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
 * Use the {@link Verify_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Verify_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Verify_fragment() {
        // Required empty public constructor
    }
    ImageView blurred;
    ProgressBar loadingVerify;
    TextView verifying;
    EditText otp;
    Button verifyButton;
    String fn,ln,pass,gm,pn,otpid;
    FirebaseAuth mAuth;
    boolean getOTP;
    public static Verify_fragment newInstance(String param1, String param2) {
        Verify_fragment fragment = new Verify_fragment();
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
        View v=inflater.inflate(R.layout.fragment_verify, container, false);
        verifyButton=v.findViewById(R.id.verifyButton);
        ((MainActivity)getActivity()).isCreated(true);
        blurred=v.findViewById(R.id.blurredVerify);
        loadingVerify=v.findViewById(R.id.progressBar);
        verifying=v.findViewById(R.id.verifying);
        otp=v.findViewById(R.id.otpText);
        pn =getArguments().getString("pn");
        fn =getArguments().getString("fn");
        ln =getArguments().getString("ln");
        pass =getArguments().getString("pass");
        gm=getArguments().getString("gm");
        mAuth=FirebaseAuth.getInstance();
        blurred.setVisibility(View.VISIBLE);
        loadingVerify.setVisibility(View.VISIBLE);
        verifying.setVisibility(View.VISIBLE);
        intiateOTP();
        blurred.setVisibility(View.INVISIBLE);
        loadingVerify.setVisibility(View.INVISIBLE);
        verifying.setVisibility(View.INVISIBLE);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(otp.getText().toString().isEmpty())
                    {
                        Toast.makeText(getContext(),"Please Enter OTP",Toast.LENGTH_SHORT).show();
                    }
                    else if (otp.getText().toString().length()!=6)
                    {
                        Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        blurred.setVisibility(View.VISIBLE);
                        loadingVerify.setVisibility(View.VISIBLE);
                        verifying.setVisibility(View.VISIBLE);
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,otp.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                    }
            }
        });
        return  v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).isCreated(false);
    }

    private void intiateOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(pn, 60, TimeUnit.SECONDS, getActivity(), new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                otpid=s;
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
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),"Verified Successfully",Toast.LENGTH_SHORT).show();
                            createAccount();

                        } else {
                            Toast.makeText(getContext(),"Failed to Verify",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void createAccount()
    {
        String url="https://agrobazarvaibhavpatil.000webhostapp.com/LogIn/SignUp.php";
        String qry="?t1="+pn.substring(3)+"&t2="+pass+"&t3="+fn.trim()+"&t4="+ln+"&t5="+gm+"&t6="+pn.substring(0,3);
        Log.d("qry",qry);
        class createaccproc extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("Some error occured"))
                {
                    Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
                    ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container,new LogIn_Fragment()).commit();
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
        createaccproc acc=new createaccproc();
        acc.execute(url+qry);
    }
}