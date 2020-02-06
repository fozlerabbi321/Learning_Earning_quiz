package com.learnearning.learnnnge;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaswOutActivity extends AppCompatActivity {

    @BindView(R.id.btnselectcahsout)
    Button btnselectcahsout;
    @BindView(R.id.btncashistory)
    Button btncashistory;
    @BindView(R.id.btncaspaymenttipe)
    Button btncaspaymenttipe;
    @BindView(R.id.etpaymenttype)
    EditText etpaymenttype;
    @BindView(R.id.etnumberemail)
    EditText etnumberemail;
    @BindView(R.id.etpoint)
    EditText etpoint;
    @BindView(R.id.btncashout)
    Button btncashout;
    @BindView(R.id.withadView)
    AdView withadView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casw_out);
        ButterKnife.bind(this);


        etpaymenttype.setEnabled(false);
        withadView = findViewById(R.id.withadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        withadView.loadAd(adRequest);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @OnClick({R.id.btnselectcahsout, R.id.btncashistory, R.id.btncaspaymenttipe, R.id.btncashout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnselectcahsout:
                startActivity(new Intent(getApplicationContext(),CaswOutActivity.class));
                finish();
                break;
            case R.id.btncashistory:
                startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                finish();
                break;
            case R.id.btncaspaymenttipe:
                poupmenu();
                break;
            case R.id.btncashout:
                String paymetnmtype=etpaymenttype.getText().toString();
                String number=etnumberemail.getText().toString();
                String amount=etpoint.getText().toString();

                if (TextUtils.isEmpty(amount)){

                    MDToast.makeText(getApplicationContext(),"Please Enter your Point", MDToast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(number)){
                    MDToast.makeText(getApplicationContext(),"Please Enter your account Number or Email", MDToast.LENGTH_SHORT).show();

            }if (TextUtils.isEmpty(amount)&&TextUtils.isEmpty(amount)&& TextUtils.isEmpty(paymetnmtype)){
                MDToast.makeText(getApplicationContext(),"Please Enter your minimum point and account Number or Email", MDToast.LENGTH_SHORT).show();
            }else{
                if(true) {
                    if(Integer.parseInt(etpoint.getText().toString())<2000){
                        Toast.makeText(getApplicationContext(),"Minimum Amount is 2000",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            changeData();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "This is very sad "+e, Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            }
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void changeData(){

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int previousScore = dataSnapshot.child("point").getValue(Integer.class);
                String totalCashout = dataSnapshot.child("totalcashout").getValue(String.class);
                int i=Integer.parseInt(etpoint.getText().toString());
                if (previousScore>i){
                    Log.i("123321",previousScore+"selected amount   "+i);
                    int finalScore = previousScore -i;
                    int finalcashout=Integer.parseInt(totalCashout)+i;
                    databaseReference.child("point").setValue(finalScore);
                    databaseReference.child("totalcashout").setValue(finalcashout+"");
                    Toast.makeText(getApplicationContext(),"  point SuccessFully Added", Toast.LENGTH_SHORT).show();
                    addData();
                }
                else {

                    Toast.makeText(getApplicationContext(),"  not enough fund", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void addData(){

        final DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("name");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("withdraw").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                databaseReference.child("account").setValue(etnumberemail.getText().toString());
                databaseReference.child("amount").setValue(etpoint.getText().toString());
                databaseReference.child("status").setValue("pending");
                databaseReference.child("method").setValue(etpaymenttype.getText().toString());
                Allwithdraw();
                Toast.makeText(getApplicationContext(),"Success! Please Wait some moment",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void poupmenu(){
        PopupMenu popup=new PopupMenu(CaswOutActivity.this,btncaspaymenttipe);
        popup.getMenuInflater().inflate(R.menu.poupup_menu,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
              etpaymenttype.setText("Payment Type = "+item.getTitle());
                return true;
            }
        });
        popup.show();
    }

    @Override
    public void onBackPressed() {
       startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
    public void Allwithdraw(){
        final DatabaseReference databaseReference111= FirebaseDatabase.getInstance().getReference();
        databaseReference111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long  previousPoints=dataSnapshot.child("allwithdraw").getValue(Long.class);
                long  presentpoints=Long.parseLong(etpoint.getText().toString());
                long finalwithdraw=(previousPoints+presentpoints);
                    databaseReference111.child("allwithdraw").setValue(finalwithdraw);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
