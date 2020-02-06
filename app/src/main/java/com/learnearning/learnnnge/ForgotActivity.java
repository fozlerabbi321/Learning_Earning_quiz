package com.learnearning.learnnnge;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotActivity extends AppCompatActivity {

    @BindView(R.id.etforgot)
    EditText etforgot;
    @BindView(R.id.btnchangepassword)
    Button btnchangepassword;
    @BindView(R.id.forgotadView)
    AdView forgotadView;
    @BindView(R.id.circle_loading_view)
    AnimatedCircleLoadingView circleLoadingView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        forgotadView = findViewById(R.id.forgotadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        forgotadView.loadAd(adRequest);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btnchangepassword)
    public void onViewClicked() {
        circleLoadingView.startIndeterminate();
        mAuth.sendPasswordResetEmail(etforgot.getText().toString()).addOnCompleteListener(ForgotActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    circleLoadingView.stopOk();
                    MDToast.makeText(getApplicationContext(), "Password reset sended . Please Check your Email Inbox", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();

    }
}
