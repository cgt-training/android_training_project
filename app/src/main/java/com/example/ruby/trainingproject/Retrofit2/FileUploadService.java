package com.example.ruby.trainingproject.Retrofit2;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by ruby on 15/6/18.
 */

public interface FileUploadService {

//    @Headers({
//                "Content-Type:application/x-www-form-urlencoded",
//                "Accept: application/x-www-form-urlencoded"
//        })

    @Multipart
//    @POST("http://192.168.1.59:3000/users/register_football")
    @POST("https://shrouded-escarpment-62032.herokuapp.com/users/register_football")
    Call<HashMap<String, String>> upload(@PartMap HashMap<String, Object> parameters,
                                         @Part MultipartBody.Part file);

}
