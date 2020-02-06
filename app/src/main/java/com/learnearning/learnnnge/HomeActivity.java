package com.learnearning.learnnnge;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.layoutcashpoint)
    TextView layoutcashpoint;
    @BindView(R.id.layoutrefer)
    TextView layoutrefer;
    @BindView(R.id.layoutstart)
    TextView layoutstart;
    @BindView(R.id.layouthowtouse)
    TextView layouthowtouse;
    @BindView(R.id.layoutpointstat)
    TextView layoutpointstat;
    @BindView(R.id.layoutyoutube)
    TextView layoutyoutube;
    @BindView(R.id.mainhomebtn)
    Button mainhomebtn;
    @BindView(R.id.mainearnmoney)
    Button mainearnmoney;
    @BindView(R.id.maintoplist)
    Button maintoplist;
    private TextView Noticetext, url,username,useremali,usermobile,alluser,paidpoints,totlaclick,totalinvalid,acountstats;
    private FirebaseDatabase noticeDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = noticeDatabase.getReference();
    private DatabaseReference childrefrence = reference.child("Admin Notice");
    private int coutUser=0;
    CountdownView mCountdownView;
    private TextView score,tvrefer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        score=(TextView)findViewById(R.id.tvpoints);
        tvrefer=(TextView)findViewById(R.id.tvrefer);
        DrawerLayout drawer = findViewById(R.id.drawyeridll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationidll);
        View hView = navigationView.getHeaderView(0);
        //  nav_user = (TextView)hView.findViewById(R.id.textView);
        //   imageView=(CircleImageView)hView.findViewById(R.id.ImageView);
        navigationView.setNavigationItemSelectedListener(this);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                score.setText(Double.parseDouble(dataSnapshot.child("point").getValue().toString())+" points");
                 tvrefer.setText(dataSnapshot.child("Mobile").getValue()+"");
                if ((dataSnapshot.child("invalid").getValue() + "").equals("3")) {
                    startActivity(new Intent(getApplicationContext(), AcountBockActivity.class));
                    finish();
                }
                if((dataSnapshot.child("click").getValue()+"").equals("8"))
                {
                    startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navhomeid:
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.navcashoutid:
                startActivity(new Intent(HomeActivity.this, CaswOutActivity.class));
                finish();
                break;
            case R.id.navreferid:
                startActivity(new Intent(HomeActivity.this, ReferActivity.class));
                finish();
                break;
            case R.id.navmakemoneyid:
                timer();
                break;
            case R.id.navaccountid:
                AccountInfo();
                break;
            case R.id.navyoutubeid:
                youtube();
                break;
            case R.id.navaboutstormid:
                aboutEar();
                break;
            case R.id.navexitid:
                finish();
                break;
            case R.id.navtelegram:
                telegram();
                break;
            case R.id.navcontctus:
                startActivity(new Intent(getApplicationContext(),ContactusActivity.class));
                finish();
                break;
            case R.id.privacy:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/document/d/e/2PACX-1vSs5IbCb1vCmiWWQgMmsO2rA-lXPPjH2WuD4u0C03kTVjl-MM5iVQwuuzo2B4FAq6hFfu8UVmdFuW1A/pub?fbclid=IwAR3cXxcKJDJjZ_dUWv4CAiHc3Juu5OtyJ9dqqBrzXwAQYJwEYEF122keeFo")));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawyeridll);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage("Are you sure exit?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(getApplicationContext(),"Finish",Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog1=alertDialog.create();
        alertDialog1.show();

    }




    @OnClick({R.id.layoutcashpoint, R.id.layoutrefer, R.id.layoutstart, R.id.layouthowtouse, R.id.layoutpointstat, R.id.layoutyoutube, R.id.mainhomebtn, R.id.mainearnmoney, R.id.maintoplist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutcashpoint:
                startActivity(new Intent(getApplicationContext(), CaswOutActivity.class));
                finish();
                break;
            case R.id.layoutrefer:
                startActivity(new Intent(getApplicationContext(), ReferActivity.class));
                finish();
                break;
            case R.id.layoutstart:
                timer();
                break;
            case R.id.layouthowtouse:
                startActivity(new Intent(getApplicationContext(), HowtouserActivity.class));
                finish();
                break;
            case R.id.layoutpointstat:
                Stats();
                break;
            case R.id.layoutyoutube:
                youtube();
                break;
            case R.id.mainhomebtn:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;
            case R.id.mainearnmoney:
                timer();
                break;
            case R.id.maintoplist:
                startActivity(new Intent(getApplicationContext(), TopListActivity.class));
                finish();
                //Toast.makeText(getApplicationContext(), "Next Update", Toast.LENGTH_SHORT).show();
                break;


        }
    }

/*
    @Override
    protected void onStart() {
        super.onStart();

    }
*/

    public void adminNotice() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Admin Notification");

        View mView = getLayoutInflater().inflate(R.layout.adminnotice, null);
        Noticetext = mView.findViewById(R.id.textNotice);
        Button button = mView.findViewById(R.id.btngotit);

        childrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String message = dataSnapshot.getValue(String.class);
                Noticetext.setText(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.mainnoticemenu){
            adminNotice();
        }
        return super.onOptionsItemSelected(item);
    }
    public void aboutEar(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("About Learning & Earning");
        View mView = getLayoutInflater().inflate(R.layout.about, null);
        alluser = mView.findViewById(R.id.tvalluser);
        paidpoints=mView.findViewById(R.id.tvallpaidpoints);
        Button button = mView.findViewById(R.id.btnaboutgotit);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("user");
        reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              if(dataSnapshot.exists()){
                  coutUser= (int) dataSnapshot.getChildrenCount();
                  alluser.setText("Total Active Members: "+Integer.toString(coutUser));
              }
              }
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }

      });
        AllpaidPoints();
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void AccountInfo(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Account Details");

        View mView = getLayoutInflater().inflate(R.layout.accountinfo, null);
        username = mView.findViewById(R.id.tvacountname);
        useremali=mView.findViewById(R.id.tvacountemail);
        usermobile=mView.findViewById(R.id.tvacountmobile);
        Button button = mView.findViewById(R.id.accubtngotit);

        final DatabaseReference databaseReference111= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference111.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText("Name: "+dataSnapshot.child("Name").getValue(String.class));
                useremali.setText("Email: "+dataSnapshot.child("Email").getValue(String.class));
                usermobile.setText("Mobile Number: "+dataSnapshot.child("Mobile").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


     /*   mBuilder.setNegativeButton("Got It!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void AllpaidPoints(){
        final DatabaseReference databaseReference111= FirebaseDatabase.getInstance().getReference();
        databaseReference111.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paidpoints.setText("Total Paid Points:"+dataSnapshot.child("allwithdraw").getValue(Long.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void Stats(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        mBuilder.setCancelable(false);
        mBuilder.setTitle("Your Stats");

        View mView = getLayoutInflater().inflate(R.layout.acountsate, null);
        totlaclick = mView.findViewById(R.id.tvtototlaclick);
        totalinvalid=mView.findViewById(R.id.tvtotalinvalidclick);
        acountstats=mView.findViewById(R.id.tvstatas);
        Button button = mView.findViewById(R.id.btnacountstareok);

        final DatabaseReference databaseReference111= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference111.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totlaclick.setText("Your Total Clicks: "+dataSnapshot.child("click").getValue(String.class));
                totalinvalid.setText("Total Invalid Clicks: "+dataSnapshot.child("invalid").getValue(String.class));
                String invalid=dataSnapshot.child("invalid").getValue(String.class);
                if(Integer.parseInt(invalid)==0){
                  acountstats.setText("Your Account is safe! 100%");
                }
                if(Integer.parseInt(invalid)==1){
                    acountstats.setText("Your Account is safe! 20%");
                } if(Integer.parseInt(invalid)==2){
                    acountstats.setText("Your account is safe! 1%");
                } /*if(Integer.parseInt(invalid)==3){
                    acountstats.setText("Your Account is safe! 30%");
                } if(Integer.parseInt(invalid)==4){
                    acountstats.setText("Your Account is safe! 10%");
                }*/
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


     /*   mBuilder.setNegativeButton("Got It!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void youtube(){
        String url = "https://www.youtube.com/channel/UC5oOPMuSrKN4xIvydtHGGzg?sub_confirmation=1&fbclid=IwAR3Mm_dNfGaNYV6serKBZBkyMvBsLn4GwakY-wFVxrXWRvhT5gSRH1XvMUs";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();

    }
    public void telegram(){
        String url ="https://t.me/joinchat/NWwIyhY_MreWKgfZOQUqNA?fbclid=IwAR3cXxcKJDJjZ_dUWv4CAiHc3Juu5OtyJ9dqqBrzXwAQYJwEYEF122keeFo";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
    }
    public void timer(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(HomeActivity.this);
        View mView=getLayoutInflater().inflate(R.layout.dialog_timer,null);
        mCountdownView = mView.findViewById(R.id.ll);
        SharedPreferences editor = getSharedPreferences("time", MODE_PRIVATE);
        long l1 = Long.parseLong(editor.getString("time", "0"));
        Date date = new Date();
        long l2 = System.currentTimeMillis();
        if (l2 > l1) {
            startActivity(new Intent(getApplicationContext(),QuizActivity.class));
            finish();
        } else {
            long wait = (l1 - l2);
            Toast.makeText(getApplicationContext(), "please wait", Toast.LENGTH_SHORT).show();
            mCountdownView.start(wait);
        }
        mBuilder.setView(mView);
        AlertDialog dialog=mBuilder.create();
        dialog.setCancelable(true);
     dialog.show();

    }

}