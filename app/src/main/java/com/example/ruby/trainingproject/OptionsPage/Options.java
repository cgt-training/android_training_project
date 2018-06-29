package com.example.ruby.trainingproject.OptionsPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruby.trainingproject.ProfilePage.Profile;
import com.example.ruby.trainingproject.R;
import com.example.ruby.trainingproject.Reminder.Reminder;
import com.example.ruby.trainingproject.SessionManagement.SessionManager;
import com.example.ruby.trainingproject.TodoList.Todo;

import java.util.HashMap;

public class Options extends AppCompatActivity {

    private Toolbar toolbar ;
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView lblName = (TextView) findViewById(R.id.headingUsername);
        TextView lblEmail = (TextView) findViewById(R.id.headingEmail);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // name
        String name = user.get(SessionManager.KEY_NAME);

        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        // displaying user data
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
    }

    public void openTodo(View view){
        Intent intent = new Intent(this, Todo.class);
        startActivity(intent);
    }

    public void openReminder(View view){
        Intent intent = new Intent(this, Reminder.class);
        startActivity(intent);
    }

    public void openLogout(View view){
        finish();
        session.logoutUser();
    }

    public void openProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}
