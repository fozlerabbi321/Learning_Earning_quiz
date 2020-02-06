package com.learnearning.learnnnge;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SessionActivity extends AppCompatActivity {
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);

                }
            }
        });
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("123", android_id);
        String timeSettings = Settings.System.getString(this.getContentResolver(), Settings.System.AUTO_TIME);
        if (timeSettings.contentEquals("0")) {
            Log.i("0147", "working");


            Toast.makeText(getApplicationContext(), "Please Check your date and try", Toast.LENGTH_LONG).show();
        } else {
            if (checkvpn()) {
                MDToast.makeText(getApplicationContext(), "Please Stop your vpn", MDToast.LENGTH_LONG).show();
                tts.speak("turn off your vpn", TextToSpeech.QUEUE_FLUSH, null);

            } else {
                final Date date = new Date();
                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("date");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int current_date = Integer.parseInt(dateFormat.format(date));
                            int server_date = Integer.parseInt(dataSnapshot.getValue() + "");
                            if (current_date > server_date) {
                                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                databaseReference2.child("date").setValue(current_date + "");
                              //   databaseReference2.child("invalid").setValue("0");
                                //    databaseReference2.child("spin").setValue("150");
                                databaseReference2.child("click").setValue("0");
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        } else
                            {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
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
}
