package com.learnearning.learnnnge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactusActivity extends AppCompatActivity {

    @BindView(R.id.etcontactwrite)
    EditText etcontactwrite;
    @BindView(R.id.bntcontactsend)
    Button bntcontactsend;
    @BindView(R.id.btnearningtimegroup)
    Button btnearningtimegroup;
    @BindView(R.id.btncontactgoback)
    Button btncontactgoback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bntcontactsend, R.id.btnearningtimegroup, R.id.btncontactgoback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bntcontactsend:
                if(TextUtils.isEmpty(etcontactwrite.getText().toString())){
                    MDToast.makeText(getApplicationContext(),"Empty queries");
                }else {
                    sendEmail();
                }
                break;
            case R.id.btnearningtimegroup:
                fbgroup();
                break;
            case R.id.btncontactgoback:
                startActivity(new Intent(ContactusActivity.this, HomeActivity.class));
                finish();
                break;
        }
    }

    protected void sendEmail() {

        Log.i("Send email", "");
        String[] TO = {"trustmoneydeveloper01@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Learning & Earning User");
        emailIntent.putExtra(Intent.EXTRA_TEXT,etcontactwrite.getText().toString());
        try {
            startActivity(Intent.createChooser(emailIntent, "Send message..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
        }
    }
    public void fbgroup() {
        String url = "https://www.facebook.com/groups/205525066724822?_rdc=1&_rdr";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        finish();
        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();

    }
}
