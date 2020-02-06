package com.learnearning.learnnnge;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar process;
    private Timer timer;
    private int i = 0;
    private TextView textView;
    private Button vpncheck;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage( Locale.US);

                }
            }
        });


       if (checkvpn()){
            MDToast.makeText(getApplicationContext(), "Please Turn off your vpn and try again", MDToast.LENGTH_SHORT).show();
           tts.speak("Please Turn off your vpn and try again", TextToSpeech.QUEUE_FLUSH, null);

            AlertDialog.Builder mBuilder=new AlertDialog.Builder(SplashActivity.this);
            View mView=getLayoutInflater().inflate(R.layout.vpnblocker,null);
            vpncheck=mView.findViewById(R.id.vpnid);
            mBuilder.setView(mView);
            AlertDialog dialog=mBuilder.create();
            dialog.setCancelable(false);
            dialog.show();
            vpncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }else{
         /*  final long period =100;
           timer=new Timer();
           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   //this repeats every 100 ms
                   if (i<100){
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               textView.setText(String.valueOf(i)+"%");
                           }
                       });
//                       process.setProgress(i);
                       i=i+2;
                   }else{
                       //closing the timer
                       timer.cancel();

                       if(FirebaseAuth.getInstance().getCurrentUser()!= null){
                           startActivity( new Intent(getApplicationContext(),SessionActivity.class));
                           finish();
                       }
                       else {
                           startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                           finish();
                       }
                       finish();
                       // close this activity
                   }
               }
           }, 0, period);*/
         checkPermission();
        }
     /*   textView=findViewById(R.id.textView);
        textView.setText("");
        process=findViewById(R.id.progress);
        process.setProgress(0);
*/

    }

    public boolean checkvpn(){
        List<String> networkList = new ArrayList<>();
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    networkList.add(networkInterface.getName());
            }
        } catch (Exception ex) {
            //Timber.d("isVpnUsing Network List didn't received");
        }
        return networkList.contains("tun0");
    }
    boolean isPermissionGranted = false;
    void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onPermissionGranted() {
                isPermissionGranted = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                            startActivity( new Intent(getApplicationContext(),SessionActivity.class));
                            finish();
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }
                        finish();
                    }
                }, 1000);
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                isPermissionGranted = false;
                Log.e("permission: ", deniedPermissions.toString());
                checkPermission();
            }
        };
        TedPermission.with(SplashActivity.this).setPermissionListener(permissionlistener)
                .setRationaleMessage("We need some hardware permission to make the app functional")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                        Manifest.permission.READ_PHONE_STATE
                ).check();
    }
    }

