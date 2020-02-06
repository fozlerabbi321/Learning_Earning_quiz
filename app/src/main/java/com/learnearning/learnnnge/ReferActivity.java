package com.learnearning.learnnnge;

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
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

public class ReferActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tvrefyourrefercode)
    TextView tvrefyourrefercode;
    @BindView(R.id.tvreftotalreferjoinig)
    TextView tvreftotalreferjoinig;
    @BindView(R.id.btnrefinviteernt)
    Button btnrefinviteernt;
    @BindView(R.id.addtomainbalance)
    Button addtomainbalance;
    @BindView(R.id.refhomebtn)
    Button refhomebtn;
    @BindView(R.id.refearnmoney)
    Button refearnmoney;
    @BindView(R.id.reftoplist)
    Button reftoplist;
    @BindView(R.id.Availablebalance)
    TextView Availablebalance;
    private TextView Noticetext, url,username,useremali,usermobile,alluser,paidpoints,totlaclick,totalinvalid,acountstats;
    private FirebaseDatabase noticeDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = noticeDatabase.getReference();
    private DatabaseReference childrefrence = reference.child("Admin Notice");
    private int coutUser=0;
    CountdownView mCountdownView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);
        ButterKnife.bind(this);
        DrawerLayout drawer = findViewById(R.id.referdrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.refertoolbar);
        // score=(TextView)findViewById(R.id.scores);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationrefer);
        View hView = navigationView.getHeaderView(0);
        //  nav_user = (TextView)hView.findViewById(R.id.textView);
        //   imageView=(CircleImageView)hView.findViewById(R.id.ImageView);
        navigationView.setNavigationItemSelectedListener(this);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvrefyourrefercode.setText("Refer Code:" + dataSnapshot.child("Mobile").getValue() + "");
                tvreftotalreferjoinig.setText("Total Refer Join:" + dataSnapshot.child("totalreffer").getValue() + "");
                Availablebalance.setText("Available Balance:" + dataSnapshot.child("referPoints").getValue() + "");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @OnClick({R.id.addtomainbalance, R.id.btnrefinviteernt, R.id.refhomebtn, R.id.refearnmoney, R.id.reftoplist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addtomainbalance:
                pointsAdd();
                break;
            case R.id.btnrefinviteernt:
                String refercode = tvrefyourrefercode.getText().toString();
                String appPackageName = getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Download app");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Do you want to earn free cash/Mobile recharge? Learning & Earning: is the best mobile application to earn free cash in your home.Making money & Get Payment Via Recharge/Bkash/Rocek/Paypal(Global).Easy to use & Instant Payment System.Minimum cashout 1000 points.Click to Download & start making money Download Link:"
                        + "check out the App at: https://play.google.com/store/apps/details?id" + appPackageName + "Join with referral code:" + refercode);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                finish();
                break;
            case R.id.refhomebtn:
                startActivity(new Intent(ReferActivity.this, HomeActivity.class));
                finish();
                break;
            case R.id.refearnmoney:
                timer();
                break;
            case R.id.reftoplist:
               /* startActivity(new Intent(getApplicationContext(),TopListActivity.class));
                finish();*/
                Toast.makeText(getApplicationContext(), "Next Update", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void pointsAdd() {

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ff=dataSnapshot.child("referPoints").getValue()+"";
                int j=Integer.parseInt(ff);
                if(j>0){
                    String previousScore=dataSnapshot.child("point").getValue()+"";
                    String refertotalScore=dataSnapshot.child("referPoints").getValue()+"";

                    int presentPoint=Integer.parseInt(previousScore)+Integer.parseInt(refertotalScore);
                    databaseReference.child("point").setValue(presentPoint);
                    pointsub();
                    MDToast.makeText(getApplicationContext(),"Your Refer available balance is Successfully.",MDToast.TYPE_SUCCESS,MDToast.LENGTH_SHORT).show();


                }else {
                    MDToast.makeText(getApplicationContext(),"Your Refer available balance is 0.",MDToast.LENGTH_SHORT).show();
                }
             startActivity(new Intent(getApplicationContext(),ReferActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void pointsub(){
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("referPoints");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.setValue(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navhomeid:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                break;
            case R.id.navcashoutid:
                startActivity(new Intent(ReferActivity.this, CaswOutActivity.class));
                finish();
                break;
            case R.id.navreferid:
                startActivity(new Intent(ReferActivity.this, ReferActivity.class));
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
            case R.id.navcontctus:
                startActivity(new Intent(getApplicationContext(),ContactusActivity.class));
                finish();
                break;
            case R.id.navtelegram:
                telegram();
                break;
            case R.id.privacy:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/document/d/e/2PACX-1vSs5IbCb1vCmiWWQgMmsO2rA-lXPPjH2WuD4u0C03kTVjl-MM5iVQwuuzo2B4FAq6hFfu8UVmdFuW1A/pub?fbclid=IwAR3cXxcKJDJjZ_dUWv4CAiHc3Juu5OtyJ9dqqBrzXwAQYJwEYEF122keeFo")));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.referdrawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void youtube(){
        String url = "https://www.youtube.com/channel/UC5oOPMuSrKN4xIvydtHGGzg?sub_confirmation=1&fbclid=IwAR3Mm_dNfGaNYV6serKBZBkyMvBsLn4GwakY-wFVxrXWRvhT5gSRH1XvMUs";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();

    }
    public void AccountInfo(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReferActivity.this);
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
    public void aboutEar(){
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReferActivity.this);
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

    public void adminNotice() {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReferActivity.this);
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
    public void telegram(){
        String url ="https://t.me/joinchat/NWwIyhY_MreWKgfZOQUqNA?fbclid=IwAR3cXxcKJDJjZ_dUWv4CAiHc3Juu5OtyJ9dqqBrzXwAQYJwEYEF122keeFo";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
    }
    public void timer(){
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(ReferActivity.this);
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
