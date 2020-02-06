package com.learnearning.learnnnge;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPass)
    EditText etPass;
    @BindView(R.id.etCfPass)
    EditText etCfPass;
    @BindView(R.id.etRef)
    EditText etRef;
    @BindView(R.id.chSingupage)
    CheckBox chSingupage;
    @BindView(R.id.ibtnSignUp)
    Button ibtnSignUp;
    @BindView(R.id.lnLogin)
    LinearLayout lnLogin;
    @BindView(R.id.tvLoginNow)
    TextView tvLoginNow;
    boolean dublicate=false;
    FirebaseAuth mAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mAuth= FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

    }

    @OnClick({R.id.ibtnSignUp, R.id.tvLoginNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibtnSignUp:
                UserSignUp();
                break;
            case R.id.tvLoginNow:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }
    private void UserSignUp() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String imie = telephonyManager.getDeviceId();
        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String address = etAddress.getText().toString();
        final String mobile = etMobile.getText().toString();
        final String password = etPass.getText().toString();
        final String cfpassword = etCfPass.getText().toString();
        final String reference = etRef.getText().toString();

        if (isEmailValid(email) && password.equals(cfpassword) && password.length() >= 6 && chSingupage.isChecked()){

            mAuth.createUserWithEmailAndPassword(email ,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.setTitle(R.string.app_name);
                    progressDialog.setMessage("Please wait");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    if(task.isSuccessful()){
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(mobile).build();
                        user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference();
                                Query queryToGetData=dbRef.child("user").orderByChild("mobile").equalTo(mobile);
                                queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()){
                                            Log.i("123321","number ok");
                                            dublicate =false;

                                            // imei set databse method
                                            DatabaseReference dbRef=FirebaseDatabase.getInstance().getReference();
                                            Query queryToGetData=dbRef.child("user").orderByChild("imie").equalTo(imie);
                                            queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(!dataSnapshot.exists()){
                                                        Log.i("123321","ok"+imie);
                                                        dublicate=false;

                                                        payrefferer(reference);
                                                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(etMobile.getText().toString());
                                                        databaseReference.child("Name").setValue(name);
                                                        databaseReference.child("Address").setValue(address);
                                                        databaseReference.child("click").setValue("0");
                                                        databaseReference.child("invalid").setValue("0");
                                                        databaseReference.child("point").setValue(0);
                                                        databaseReference.child("date").setValue("0");
                                                        databaseReference.child("Mobile").setValue(mobile);
                                                        databaseReference.child("imie").setValue(imie);
                                                        databaseReference.child("Email").setValue(email);
                                                        databaseReference.child("totalreffer").setValue(0);
                                                        databaseReference.child("totalcashout").setValue("0");
                                                        databaseReference.child("Refercode").setValue(mobile);
                                                        databaseReference.child("referPoints").setValue(0);
                                                        Toast.makeText(RegisterActivity.this, "SignUp Successfully.", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                                        finish();
                                                        progressDialog.dismiss();
                                                    }else {
                                                        dublicate =true;
                                                        Log.i("123321","Phone already used "+dataSnapshot.getValue());
                                                        Toast.makeText(getApplicationContext(), "Phone " + " already used "+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });


                                        }else {
                                            dublicate=true;
                                            Log.i("123321","Phone number already used"+dataSnapshot.getValue());
                                            Toast.makeText(RegisterActivity.this, "Phone number already used "+dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });

                    }



                }
            });

        } else {

            MDToast.makeText(getApplicationContext(), "Please Enter A Valid Email And Password or Check The Box", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        }
    }
    boolean isEmailValid(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void payrefferer(final String number){
        final DatabaseReference databaseReference00= FirebaseDatabase.getInstance().getReference("user").child(number).child("referPoints");
        databaseReference00.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    Log.i("123321",dataSnapshot+" previous");
                    String previousScore = dataSnapshot.getValue()+"";

                    Log.i("123321",previousScore+" work previous");
                    int i =Integer.parseInt(previousScore);
                    final int finalScore = Integer.parseInt(previousScore) + 100;
                    databaseReference00.setValue(finalScore);

                  //  circleLoadingView.stopOk();Log.i("123321","success reffer");

                    final DatabaseReference databaseReference001= FirebaseDatabase.getInstance().getReference("user").child(number).child("totalreffer");
                    databaseReference001.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String previousScore = dataSnapshot.getValue()+"";

                                Log.i("123321", previousScore + " previous");
                                int i = Integer.parseInt(previousScore);
                                int finalScore001 = Integer.parseInt(previousScore) + 1;
                              //  String fs=finalScore001+"";
                                databaseReference001.setValue(finalScore001);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                    //  sendEmail();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                    //      startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }
                else {
               //     circleLoadingView.stopOk();
                    Log.i("123321","fail reffer "+number);
                    //   sendEmail();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                    // startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();

    }
}
