package com.learnearning.learnnnge;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AcountBockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_bock);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
