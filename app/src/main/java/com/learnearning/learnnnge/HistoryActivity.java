package com.learnearning.learnnnge;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.btnhistorycasht)
    Button btnhistorycasht;
    @BindView(R.id.btnhistoryhistory)
    Button btnhistoryhistory;
    @BindView(R.id.tvhistorypoint)
    TextView tvhistorypoint;
    @BindView(R.id.tvhislastwithdraw)
    TextView tvhislastwithdraw;
    @BindView(R.id.tvhistotalwithdraw)
    TextView tvhistotalwithdraw;
    @BindView(R.id.hadView)
    AdView hadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        hadView = findViewById(R.id.hadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        hadView.loadAd(adRequest);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tvhistorypoint.setText("Current Points:"+Double.parseDouble(dataSnapshot.child("point").getValue().toString())+" points");
                tvhistotalwithdraw.setText("Total CashOut: "+dataSnapshot.child("totalcashout").getValue()+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference databaseReference11= FirebaseDatabase.getInstance().getReference("withdraw").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference11.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvhislastwithdraw.setText("Last CashOut: "+dataSnapshot.child("amount").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @OnClick({R.id.btnhistorycasht, R.id.btnhistoryhistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnhistorycasht:
                startActivity(new Intent(getApplicationContext(),CaswOutActivity.class));
                finish();
                break;
            case R.id.btnhistoryhistory:
                startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                finish();
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            startActivity(new Intent(getApplicationContext(),CaswOutActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),CaswOutActivity.class));
        finish();

    }
}
