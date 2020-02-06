package com.learnearning.learnnnge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.radioButton)
    RadioButton rb1;
    @BindView(R.id.radioButton2)
    RadioButton rb2;
    @BindView(R.id.radioButton3)
    RadioButton rb3;
    @BindView(R.id.radioButton4)
    RadioButton rb4;
    @BindView(R.id.showid)
    TextView showid;
    @BindView(R.id.quizno)
    TextView quizno;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.submit)
    Button submit;
    private TextToSpeech tts;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;
    private InterstitialAd mInterstitial,nInterstitial,oInterstitial,pInterstitial;
    private AdView mAdView;
    int total_point = 0;
    String quiz[] = {"", "", "", "", "",//keep empty
            //============Question===================op1===op2====option3=option4==right answer==============
            "which one is not programming language", "C", "Java", "html", "phyton", "html",//1st
            "Who is the computer detector", "Charles Babbage", "Howard Eckin", "Ray Tomlinson", "Gareth Bell", "Howard Eckin",//2nd
            "Who is The father of computer", "Charles Babbage", "Howard Eckin", "Ray Tomlinson", "Gareth Bell", "Charles Babbage", //3rd
            "Which is the input device ? ", "Keyboard", "Monitor", "Printer", "Flopy Disk", "Keyboard", //4rth
            "How much is discovered Facebook ? ", "2005", "2003", "2004", "2001", "2004", //5
            "Which is the largest continent ? ", "Australia", "Asia", "South America", "Africa", "Asia", //6
            "Which is the largest Country ? ", "China", "America", "Russia", "India", "Russia", //7
            "Which is the largest City? ", "London", "Beijing", "Tokyo", "Sydney", "London", //8
            "Which is the largest Sea Beach?", "Cape Town", "Cox's Bazar", "Hanley Bay", "Robinhood's Bay", "Cox's Bazar", //9
            "Which is the largest ocean? ", "Northern Ocean", "The Atlantic Ocean", "The Pacific Ocean", "Indian ocean", "The Pacific Ocean", //10
            "Which is the largest desert?", "Sahara", "Thor", "Gobi", "Kalahari", "Sahara", //11
            "Which is the largest island country? ", "Singapore", "Australia", "Thailand ", "Indonesia", "Indonesia", //12
            "Which is the largest country in the population?", "China", "Russia", "India", "America", "China", //13
            "Which is the sunrise country?", "Japan", "New Zealand", "North Korea", "Thailand", "Japan", //14
            "What is the thunderbolt country?", "Nepal", "Thailand", "Bhutan", "Myanmar", "Bhutan", //15
            "Which country is Known for kangaroor?", "Turkey", "Greenland", "New Zealand", "Australia", "Australia", //16
            "Who is The father of computer?", "Iceland", "Norwey", "France", "Italy", "Norwey", //17
            "Where is the UN headquarters?", "Venice", "Moscow", "New York", "Beijing", "New York", //18
            "Who are made this apps?", "Friends Software IT", "Friends Developer", "Friends IT", "Software IT", "Friends Software IT", //19
            "When was the first world war Started?", "1928", "1914", "1920", "1918", "1914"};
    int serial = 0;
    private Question question = new Question();
    private String answer;
    private int questionLength = question.questions.length;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
       // show_quiz();
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnDisplay = (Button) findViewById(R.id.submit);
        mAdView = findViewById(R.id.qadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //point show method call
            showpoint();

// shatedpreferances
        sharedPreferences=getSharedPreferences("quiz",MODE_PRIVATE);
        editor=getSharedPreferences("quiz",MODE_PRIVATE).edit();
        i=sharedPreferences.getInt("serial",0);
        Log.i("123321","current i = "+i);
        if(i<questionLength){
            NextQuestion(i);
        }
        else {
            Toast.makeText(this, "no more quiestion", Toast.LENGTH_SHORT).show();
        }

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  nav_user.setText(dataSnapshot.child("name").getValue()+"");
                //   score.setText(dataSnapshot.child("point").getValue()+"");
                if((dataSnapshot.child("invalid").getValue()+"").equals("3"))
                {
                    startActivity(new Intent(getApplicationContext(),AcountBockActivity.class));
                }
                if((dataSnapshot.child("click").getValue()+"").equals("8"))
                {
                    startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * invalid click ads
                 */
                if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked())
                {
                    if(i+1<=18){
                        int selectedId = radioSexGroup.getCheckedRadioButtonId();
                        radioSexButton = (RadioButton) findViewById(selectedId);
                        String ss = radioSexButton.getText() + "";
                        if (ss.equals(answer)) {
                            MDToast.makeText(getApplicationContext(), "Right Answer.", Toast.LENGTH_SHORT).show();
                            showAds();
                        } else {
                            MDToast.makeText(getApplicationContext(), "Wrong Answer.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    /**
                     * valid click adds
                     */
                  else if(i+1==19)
                  {
                      int selectedId = radioSexGroup.getCheckedRadioButtonId();
                      radioSexButton = (RadioButton) findViewById(selectedId);
                      String ss = radioSexButton.getText() + "";
                      if (ss.equals(answer)) {
                          MDToast.makeText(getApplicationContext(), "Right Answer.", Toast.LENGTH_SHORT).show();
                          clickAblAds();
                      } else {
                          MDToast.makeText(getApplicationContext(), "Wrong Answer.", Toast.LENGTH_SHORT).show();
                      }
                    }
                    else {
                        waitm();
                        pintadd();
                    }
                }
                else
                {
                    MDToast.makeText(getApplicationContext(), "Please Selected ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

/*    public void show_quiz() {
        if (serial <= 19) {
            serial++;
          //  Toast.makeText(this, "serial" + serial, Toast.LENGTH_SHORT).show();
            quizno.setText("Quiz No: "+serial+"/"+"20");
            int i = (serial * 6) - 1;
            Log.i("123321", "" + i);
            title.setText(quiz[i]);
            rb1.setText(quiz[i + 1]);
            rb2.setText(quiz[i + 2]);
            rb3.setText(quiz[i + 3]);
            rb4.setText(quiz[i + 4]);
            showid.setText("Ans: " + quiz[i + 5]);

            Log.i("123321", i + "serial " + serial);
        } else {
            MDToast.makeText(getApplicationContext(), "Not more question.", Toast.LENGTH_SHORT).show();

        }

    }*/


    public void showAds(){

        /**
         *  1st ads
         */
        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId(getString(R.string.interstialads));
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mInterstitial.loadAd(request);
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.i("adclick", "ad clicked");
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("invalid");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String previousInvalidClick = dataSnapshot.getValue(String.class);
                        if (previousInvalidClick != null) {
                            int finalScore = Integer.parseInt(previousInvalidClick) + 1;
                            databaseReference.setValue(String.valueOf(finalScore));
                            MDToast.makeText(getApplicationContext(), "warning !!! clicking ad may suspend your account",MDToast.TYPE_WARNING, MDToast.LENGTH_LONG).show();
                           // startActivity(new Intent(getApplicationContext(), SpinActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mInterstitial.show();
                Log.i("ad", "AD LOADED");

            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("ad", "error" + errorCode);
                MDToast.makeText(getApplicationContext(), "Error! please try leter " + errorCode, MDToast.LENGTH_SHORT).show();;
            }

            @Override
            public void onAdOpened() {
                Log.i("ad", "ad open");
                MDToast.makeText(getApplicationContext(), "Now Carefully Press The Cross Button", MDToast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdLeftApplication() {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("invalid");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String previousScore = dataSnapshot.getValue(String.class);
                        if (previousScore != null) {
                            int finalScore = Integer.parseInt(previousScore) + 1;
                            databaseReference.setValue(String.valueOf(finalScore));
                            Toast.makeText(getApplicationContext(), "  warning !!! clicking ad may suspend your account", Toast.LENGTH_SHORT).show();
                           // startActivity(new Intent(getApplicationContext(), SpinActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onAdClosed()
            {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("ad", "ad closed");
             //show_quiz();
                i+=1;
                editor.putInt("serial",i);
                Log.i("123321",""+i);
                editor.commit();
                sharedPreferences=getSharedPreferences("quiz",MODE_PRIVATE);
                i=sharedPreferences.getInt("serial",1);
                if(i<questionLength){
                    NextQuestion(i);
                }
                else {
                    MDToast.makeText(getApplicationContext(), "No more question", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //long l2= System.currentTimeMillis()+2*60*60*1000;
    }
    public void clickAblAds(){
        nInterstitial = new InterstitialAd(this);
        nInterstitial.setAdUnitId(getString(R.string.interstialads));
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        nInterstitial.loadAd(request);
        nInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.i("adclick", "ad clicked");
            }
        });
        nInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                nInterstitial.show();
                MDToast.makeText(getApplicationContext(), "Now Click The Ad And Wait Some Moment", Toast.LENGTH_LONG).show();
                //tts.speak("Now Click The Ad And Wait Some Moment", TextToSpeech.QUEUE_FLUSH, null);
                Log.i("ad", "AD LOADED");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("ad", "error" + errorCode);
                MDToast.makeText(getApplicationContext(), "Error! please try leter " + errorCode, Toast.LENGTH_LONG).show();;
            }
            @Override
            public void onAdOpened() {
                Log.i("ad", "ad open");
            }

            @Override
            public void onAdLeftApplication() {
                new CountDownTimer(60000, 2000) {
                    @Override
                    public void onTick(long l) {
                        if(l>2000)
                        {
                            MDToast mdToast = MDToast.makeText(getApplicationContext(), " " +("Please wait "+( l / 1000)) + " second");
                            mdToast.show();
                        }
                    }
                    @Override
                    public void onFinish() {
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("click");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String previousScore = dataSnapshot.getValue(String.class);
                                if (previousScore != null) {
                                    int finalScore = Integer.parseInt(previousScore) + 1;
                                    databaseReference.setValue(String.valueOf(finalScore));

                                    //show_quiz();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                       startActivity(new Intent(getApplicationContext(),QuizActivity.class));
                       finish();

                    }
                }.start();

                i+=1;
                editor.putInt("serial",i);
                Log.i("123321",""+i);
                editor.commit();
                sharedPreferences=getSharedPreferences("quiz",MODE_PRIVATE);
                i=sharedPreferences.getInt("serial",1);
                if(i<questionLength){

                    NextQuestion(i);
                }
                else {
                    MDToast.makeText(getApplicationContext(), "No more question", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("ad", "ad closed");
              // show_quiz();
            }

        });
    }
    public void pintadd(){
        oInterstitial = new InterstitialAd(this);
        oInterstitial.setAdUnitId(getString(R.string.interstialads));
        AdRequest request = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        oInterstitial.loadAd(request);
        oInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                Log.i("adclick", "ad clicked");

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("invalid");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String previousScore = dataSnapshot.getValue(String.class);
                        if (previousScore != null) {
                            int finalScore = Integer.parseInt(previousScore) + 1;
                            databaseReference.setValue(String.valueOf(finalScore));
                            MDToast.makeText(getApplicationContext(), "warning !!! clicking ad may suspend your account", MDToast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        oInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                oInterstitial.show();
                MDToast.makeText(getApplicationContext(), "Now Close this ads and point added", Toast.LENGTH_LONG).show();
              ///  tts.speak("Now Close this ads and point added", TextToSpeech.QUEUE_FLUSH, null);

                Log.i("ad", "AD LOADED");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("ad", "error" + errorCode);
                MDToast.makeText(getApplicationContext(), "Error! please try leter " + errorCode, Toast.LENGTH_LONG).show();;
            }
            @Override
            public void onAdOpened() {
                Log.i("ad", "ad open");
            }

            @Override
            public void onAdLeftApplication()
            {

            }

            @Override
            public void onAdClosed() {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            String previousScore = dataSnapshot.child("point").getValue() + "";
                            int finalScore = Integer.parseInt(previousScore) + Integer.parseInt("250");
                            databaseReference.child("point").setValue(finalScore);
                        } else {
                            Log.i("123321", "database not exist");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                reset();
            }
        });
    }
    // points show method
    public void showpoint() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String point = dataSnapshot.child("point").getValue() + "";
                total_point = Integer.parseInt(point);
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
    public void waitm(){
        Date date=new Date();
        long l2= System.currentTimeMillis()+2*60*60*1000;
        SharedPreferences.Editor editor=getSharedPreferences("time",MODE_PRIVATE).edit();
        editor.putString("time",l2+"");
        editor.commit();
    }
    private void NextQuestion(int num){
        quizno.setText(i+1+"/"+20);
        title.setText(question.getQuestion(num));
        rb1.setText(question.getchoice1(num));
        rb2.setText(question.getchoice2(num));
        rb3.setText(question.getchoice3(num));
        rb4.setText(question.getchoice4(num));
        showid.setText("Answer: "+question.getCorrectAnswer(num));
        answer = question.getCorrectAnswer(num);
    }
    public void result(){
        Toast.makeText(QuizActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
        i+=1;
        editor.putInt("serial",i);
        Log.i("123321",""+i);
        editor.commit();
        sharedPreferences=getSharedPreferences("quiz",MODE_PRIVATE);
        i=sharedPreferences.getInt("serial",1);
        if(i<questionLength){

            NextQuestion(i);
        }
        else {
            Toast.makeText(this, "no more quiestion", Toast.LENGTH_SHORT).show();}
    }
    public  void reset(){
        editor.putInt("serial",0);
        Log.i("123321","reset success");
        editor.commit();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }
}
