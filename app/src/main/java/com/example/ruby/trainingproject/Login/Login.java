package com.example.ruby.trainingproject.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruby.trainingproject.OptionsPage.Options;
import com.example.ruby.trainingproject.R;
import com.example.ruby.trainingproject.Registration.Registration;
import com.example.ruby.trainingproject.Retrofit2.FileUploadService;
import com.example.ruby.trainingproject.Retrofit2.ServiceGenerator;
import com.example.ruby.trainingproject.RetrofitForLogin.ForLogin;
import com.example.ruby.trainingproject.SessionManagement.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private Toolbar toolbar ;
    // Session Manager Class
    SessionManager session;
    HashMap<String, Object> mArrayList;
    EditText id,password;
    TextView errorMessage;
    String Check="";
    String name,email,address,phone,photo;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Session Manager
        session = new SessionManager(getApplicationContext());

        id=(EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.pass);
        errorMessage = (TextView)findViewById(R.id.errorMessage);

        LoginButton = (Button)findViewById(R.id.login);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCheck();
            }
        });
    }

    public void register(View view){
    Intent intent = new Intent(Login.this, Registration.class);
    startActivity(intent);
    }


    private void LoginCheck(){
        ForLogin service =
                ServiceGenerator.createService(ForLogin.class);

        mArrayList = new HashMap<>();
        mArrayList.put("email",id.getText().toString());
        mArrayList.put("password",password.getText().toString());

//        Toast.makeText(Login.this,"Hellow form iMage",Toast.LENGTH_LONG).show();

        Call<HashMap<String, String>> call = service.upload(mArrayList);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call,
                                   Response<HashMap<String, String>> response) {

                Log.d("Upload", response.body().toString());
                Log.d("Upload MessaGE", String.valueOf(response.body().get("message")));
                Log.d("Upload Message for user",String.valueOf(response.body().get("user")));
                Check = String.valueOf(response.body().get("message"));
                errorMessage.setText(Check);
                errorMessage.setTextColor(Color.RED);
                errorMessage.setGravity(1);
                errorMessage.setTextSize(25);

                String data = String.valueOf(response.body().get("user"));
                // Getting Objects
                try {
                    JSONObject jObj = new JSONObject(data);
                    name = jObj.getString("name");
                    address = jObj.getString("address");
                    phone = jObj.getString("phone");
                    email = jObj.getString("email");
                    photo = jObj.getString("image_url");

//                    Toast.makeText(Login.this," "+photo,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                Toast.makeText(Login.this,String.valueOf(response.body().get("user")),Toast.LENGTH_LONG).show();
//                Log.d("In the Login Function","it is coming here");

                if(Check.equals("Login Successfull")){
                    Intent intent = new Intent(Login.this,Options.class);
                    session.createLoginSession(""+name, ""+email,""+address,""+phone, ""+photo);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Login.this,"Try Again!!",Toast.LENGTH_LONG).show();
                }

//                Toast.makeText(Login.this,""+ response.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
