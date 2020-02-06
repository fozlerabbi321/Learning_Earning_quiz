package com.learnearning.learnnnge;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etCreMobile)
    EditText etCreMobile;
    @BindView(R.id.etCrePass)
    EditText etCrePass;
    @BindView(R.id.loginbtnforgot)
    Button loginbtnforgot;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvSignup)
    Button tvSignup;
    @BindView(R.id.adView)
    AdView adView;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth= FirebaseAuth.getInstance();
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick({R.id.loginbtnforgot, R.id.btnLogin, R.id.tvSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginbtnforgot:
                UserForgot();
                break;
            case R.id.btnLogin:
                UserLogin();
                break;
            case R.id.tvSignup:
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
                break;
        }
    }

    private void UserLogin() {

        if (TextUtils.isEmpty(etCreMobile.getText().toString())){
            MDToast.makeText(getApplicationContext(),"Please Enter your Login Email", MDToast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(etCrePass.getText().toString())){
            MDToast.makeText(getApplicationContext(),"Please Enter your Login Password", MDToast.LENGTH_SHORT).show();
        }if (TextUtils.isEmpty(etCrePass.getText().toString())&& TextUtils.isEmpty(etCreMobile.getText().toString()))
        {
            MDToast.makeText(getApplicationContext(),"Please Enter your Login Email and Password", MDToast.LENGTH_SHORT).show();
        }else
            {

            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(etCreMobile.getText().toString(),etCrePass.getText().toString()).
                    addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),SessionActivity.class));
                    finish();
                    MDToast.makeText(getApplicationContext(),"Login successfully.",MDToast.TYPE_SUCCESS, MDToast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else {
                    MDToast.makeText(getApplicationContext(),"Please check email and password.",MDToast.TYPE_ERROR, MDToast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                }
            });
        }
}
    private void UserForgot() {
     startActivity(new Intent(getApplicationContext(),ForgotActivity.class));
     finish();
    }

}

