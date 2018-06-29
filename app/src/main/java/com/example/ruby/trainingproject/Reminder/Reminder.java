package com.example.ruby.trainingproject.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ruby.trainingproject.R;

import java.util.Calendar;

public class Reminder extends AppCompatActivity {
    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;
    EditText ed1;
    AlarmManager alarmManager,manager;
    PendingIntent pendingIntent,pending;
    String alarmCheck;
    private Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ed1 = (EditText) findViewById(R.id.ed1reminder);

        textAlarmPrompt = (TextView)findViewById(R.id.tv2);

        buttonstartSetDialog = (Button)findViewById(R.id.bt1);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }});
    }

    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(Reminder.this,
                onTimeSetListener,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),is24r);
        timePickerDialog.setTitle("Set Reminder Time");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){
                //Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);
        }};

    private void setAlarm(Calendar targetCal){

        textAlarmPrompt.setText("\n\n***\n" + "Reminder is set@ " + targetCal.getTime() + "\n" + "***\n");

        Intent intent = new Intent(getBaseContext(), ReminderReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

        alarmCheck="yes";
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE); // CORRECT
        Intent intent1 = new Intent(this, MyReceiver.class); // CORRECT
        Bundle bundle = new Bundle();
        bundle.putString("k1",ed1.getText().toString());
        intent1.putExtras(bundle);
        pending = PendingIntent.getBroadcast( this, 0, intent1, 0 ); // CORRECT
        manager.set( AlarmManager.RTC_WAKEUP,  targetCal.getTimeInMillis(), pending ); // CORRECT

    }

    public void cancelAlarm(View view) {
        if (alarmCheck=="yes") {
            alarmManager.cancel(pendingIntent);
            manager.cancel(pending);
            textAlarmPrompt.setText("\n\n***\n" + "Reminder is cancelled " + "\n" + "***\n");
        }else {
            Toast.makeText(this,"No alarm is set",Toast.LENGTH_LONG).show();
        }
    }
}
