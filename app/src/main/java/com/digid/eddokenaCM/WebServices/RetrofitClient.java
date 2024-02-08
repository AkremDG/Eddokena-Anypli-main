package com.digid.eddokenaCM.WebServices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final RetrofitClient ourInstance = new RetrofitClient();

    public static RetrofitClient getInstance() {
        return ourInstance;
    }

    private RetrofitClient() {
    }

    public Retrofit GetRetrofitClientWS(String base) {
        //COG String apiBaseUrl = "http://41.227.23.133:81/wsglobal/"+base;
        //String apiBaseUrl = "https://tejerapi.azurewebsites.net";
        String apiBaseUrl = "https://staging.e-kmandi-api.d41d8cd98f.develop.anypli.com";
        //String apiBaseUrl = "http://41.227.23.130:85/wsalpfood/"+base;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().writeTimeout(180, TimeUnit.SECONDS).readTimeout(180, TimeUnit.SECONDS).connectTimeout(180, TimeUnit.SECONDS).addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

}
