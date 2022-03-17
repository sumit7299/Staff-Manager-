package com.example.staffmanager.Network;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiPlaceHolder {

    @POST("staff-add")
    Call<ResponseBody> addstaff(@Body JsonObject jsonObject);

    @POST("staff-list")
    Call<ResponseBody> liststaff(@Body JsonObject jsonObject);

    @POST("staff-update")
    Call<ResponseBody> updatestaff(@Body JsonObject jsonObject);

    @POST("staff-delete")
    Call<ResponseBody> deletestaff(@Body JsonObject jsonObject);

    @POST("staff-getsalary")
    Call<ResponseBody> liststaffsalary(@Body JsonObject jsonObject);

    @POST("staff-viewprofile")
    Call<ResponseBody>liststaffprofile(@Body JsonObject jsonObject);

    @POST("staff-getattendance")
    Call<ResponseBody>liststaffattendance(@Body JsonObject jsonObject);

}
