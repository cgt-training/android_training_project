package com.example.ruby.trainingproject.Registration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ruby.trainingproject.FromBitmapToUri.BitmapToUri;
import com.example.ruby.trainingproject.Login.Login;
import com.example.ruby.trainingproject.R;
import com.example.ruby.trainingproject.Retrofit2.FileUploadService;
import com.example.ruby.trainingproject.Retrofit2.ServiceGenerator;
import com.example.ruby.trainingproject.Spinner.CustomOnItemSelectedListener;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {
    Uri selectedImageUri;
    private Toolbar toolbar;
    ImageView ivImage;
    File file;
    String email;

    Integer REQUEST_CAMERA=1, SELECT_FILE=0,CAMERA=22;
    Button buttonRequestSend;
    EditText ed1,ed3,ed4,ed5,ed6,ed7,ed8;
    Spinner ed2;

    HashMap<String, Object> mPostArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ed1 =(EditText)findViewById(R.id.ed1);
        ed2 =(Spinner)findViewById(R.id.ed2);
        ed3 =(EditText)findViewById(R.id.ed3);
        ed4 =(EditText)findViewById(R.id.ed4);
        ed5 =(EditText)findViewById(R.id.ed5);
        ed6 =(EditText)findViewById(R.id.ed6);
        ed7 =(EditText)findViewById(R.id.ed7);
        ed8 =(EditText)findViewById(R.id.ed8);



        ed2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        ed2.setPrompt("State");

        buttonRequestSend=(Button)findViewById(R.id.buttonRequestSent);

        ivImage = (ImageView)findViewById(R.id.ivImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.round_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonRequestSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=ed6.getText().toString();

                if(isValidEmail(email)){

                if(CAMERA.equals(1)|CAMERA.equals(0)) {
                    uploadFile(selectedImageUri);
                    finish();
                }else{
                    Toast.makeText(Registration.this,"Please inset an Image",Toast.LENGTH_LONG).show();
                }
                }else {
                    Toast.makeText(Registration.this,"Enter correct Email",Toast.LENGTH_LONG).show();
//                    Log.d("Magggic", " "+email);
                }
            }
        });
    }

    public void SelectImage(){
        final CharSequence[] items = {"Camera","Gallery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){

                    Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(intent,REQUEST_CAMERA);}

                }else if(items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);

                }else if(items[i].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap)bundle.get("data");
                ivImage.setImageBitmap(bmp);
                CAMERA=0;

                // Handling Bitmap file
                persistImage(this,bmp,"BitmapImage");
//                Toast.makeText(Registration.this,"Hellow form iMage",Toast.LENGTH_LONG).show();

            }else if(requestCode==SELECT_FILE){
                CAMERA=1;
                selectedImageUri = data.getData();
                ivImage.setImageURI(selectedImageUri);
            }
        }
    }

    public String address(){
        String address = ed1.getText().toString()+" "+ed2.getSelectedItem().toString()+" "+ed3.getText().toString()+" "+ed4.getText().toString();
        return address;
    }

    private void uploadFile(Uri fileUri) {
        // create upload service client
        FileUploadService service =
                ServiceGenerator.createService(FileUploadService.class);

//        // use the FileUtils to get the actual file by uri
//
//        File file = FileUtils.getFile(this, fileUri);

        MultipartBody.Part imageFileBody=null;

        // If camera = 1, then
        if(CAMERA==1) {
            // Creating file from uri
            file = FileUtils.getFile(this, fileUri);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        imageFileBody = MultipartBody.Part.createFormData("profile", file.getName(), requestBody);

        // add another part within the multipart request

        String nameS,phoneS,statusS,emailS,lnameS,passwordS,adr;
        nameS=ed5.getText().toString();
        phoneS=ed8.getText().toString();
        emailS=ed6.getText().toString();
        lnameS = address();
        statusS="0";
        adr="5";
        passwordS=ed7.getText().toString();

        mPostArrayList = new HashMap<>();
        mPostArrayList.put("name",nameS);
        mPostArrayList.put("phone",phoneS);
        mPostArrayList.put("status",statusS);
        mPostArrayList.put("email",emailS);
        mPostArrayList.put("avl_day",adr);
        mPostArrayList.put("addr",address());
        mPostArrayList.put("password",passwordS);
        mPostArrayList.put("profile",file);


//        Toast.makeText(Registration.this,"Hellow form iMage",Toast.LENGTH_LONG).show();

        // finally, execute the request
        Call<HashMap<String, String>> call = service.upload(mPostArrayList,imageFileBody);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call,
                                   Response<HashMap<String, String>> response) {
                Log.d("Upload", response.toString());
                Log.d("Upload", String.valueOf(response.body().get("message")));
//                String jsonStr= response.body().toString();
//                Toast.makeText(Registration.this,""+ response.toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    public void persistImage(Context context, Bitmap bitmap, String name) {
        File filesDir = context.getFilesDir();
        file = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("persisitImage", "Error writing bitmap", e);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}