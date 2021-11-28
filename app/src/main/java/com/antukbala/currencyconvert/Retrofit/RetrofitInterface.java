package com.antukbala.currencyconvert.Retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("v6/449f90d99d42fd21cb572563/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
