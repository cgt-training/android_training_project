package com.example.ruby.trainingproject.ProfilePage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruby.trainingproject.R;
import com.example.ruby.trainingproject.SessionManagement.SessionManager;
import com.example.ruby.trainingproject.SettingImageInProfile.ImageLoadTask;

import java.util.HashMap;

import static com.example.ruby.trainingproject.SettingImageInProfile.ImageLoadTask.getBitmapFromURL;

public class Profile extends AppCompatActivity {

    private Toolbar toolbar ;
    SessionManager session;
    TextView nameTV, emailTV, addressTV, phoneTV;
    ImageView imageview,smallimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageview = findViewById(R.id.photo);
        smallimage = findViewById(R.id.SmallPhoto);

        nameTV = findViewById(R.id.name);
        emailTV = findViewById(R.id.mailId);
        addressTV = findViewById(R.id.phoneNumber);
        phoneTV = findViewById(R.id.address);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);
        String address = user.get(SessionManager.KEY_ADDRESS);
        String phone = user.get(SessionManager.KEY_PHONE);
        String url = user.get(SessionManager.KEY_IMAGE);

        nameTV.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        emailTV.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
        addressTV.setText(Html.fromHtml("Address: <b>" + address + "</b>"));
        phoneTV.setText(Html.fromHtml("Phone: <b>" + phone + "</b>"));
        new ImageLoadTask(url, imageview).execute();
        new ImageLoadTask(url,smallimage).execute();


    }
}
