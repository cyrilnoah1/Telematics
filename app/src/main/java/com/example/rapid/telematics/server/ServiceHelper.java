package com.example.rapid.telematics.server;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Service helper containing the required endpoint for accessing data.
 */
public interface ServiceHelper {

    // LOGIN:
    @FormUrlEncoded
    @POST("authz/oauth2/token")
    Call<JsonObject> login(@Field("username") String username, @Field("password") String password,
                           @Field("grant_type") String grantType, @Field("client_id") String clientId);
    
}


