package com.learnearning.learnnnge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HowtouserActivity extends AppCompatActivity {

    @BindView(R.id.btnbangla)
    Button btnbangla;
    @BindView(R.id.btnenglish)
    Button btnenlish;
    @BindView(R.id.tvbangla)
    TextView tvbangla;
    @BindView(R.id.tvfbpage)
    TextView tbfbpage;
    @BindView(R.id.tvfbgroup)
    TextView fbgroup;
    @BindView(R.id.tvytchannel)
    TextView tbytchannel;
    @BindView(R.id.tvuse)
    TextView tvuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtouser);
        ButterKnife.bind(this);
        tvbangla.setText(R.string.bangla);
        tvuse.setText(R.string.howtouserbangla);
    }

    @OnClick({R.id.btnbangla, R.id.btnenglish, R.id.tvfbpage, R.id.tvfbgroup, R.id.tvytchannel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnbangla:
                tvuse.setText(R.string.howtouserbangla);
                tvbangla.setText(R.string.bangla);
                break;
            case R.id.btnenglish:
                tvuse.setText(R.string.howtouserenglish);
                tvbangla.setText(R.string.english);
                break;
            case R.id.tvfbpage:
                fbpage();
                break;
            case R.id.tvfbgroup:
                fbgroup();
                break;
            case R.id.tvytchannel:
                youtube();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    public void youtube() {
        String url = "https://www.youtube.com/channel/UC5oOPMuSrKN4xIvydtHGGzg?sub_confirmation=1&fbclid=IwAR3Mm_dNfGaNYV6serKBZBkyMvBsLn4GwakY-wFVxrXWRvhT5gSRH1XvMUs";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();

    }

    public void fbgroup() {
        String url = "https://www.facebook.com/groups/205525066724822?_rdc=1&_rdr";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
    }

    public void fbpage() {
        String url = "https://www.facebook.com/trustmoney.bd/?_rdc=1&_rdr";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
    }
}
