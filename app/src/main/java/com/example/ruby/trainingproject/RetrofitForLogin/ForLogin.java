package com.example.ruby.trainingproject.RetrofitForLogin;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by ruby on 29/6/18.
 */

public interface ForLogin {

    @POST("https://shrouded-escarpment-62032.herokuapp.com/users/login_football")
//        @POST("http://192.168.1.59:3000/users/login_football")
    Call<HashMap<String, String>> upload(@Body HashMap<String, Object> parameters);
}
