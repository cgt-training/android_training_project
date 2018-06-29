package com.example.ruby.trainingproject.Reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ruby.trainingproject.R;

public class RemainderMessage extends AppCompatActivity {

    private Toolbar toolbar ;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder_message);
        tv1 = (TextView)findViewById(R.id.tv1);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = extras.getString("k1");
            tv1.setText(message);
        }
    }

    public void toStop(View view){
        ReminderReceiver.ringtone.stop();
        finish();
    }
}
