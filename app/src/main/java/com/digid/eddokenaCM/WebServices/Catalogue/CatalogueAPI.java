package com.digid.eddokenaCM.WebServices.Catalogue;


import android.content.Context;
import android.util.Log;

import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.InitCatalogueTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue.InsertAllCatalogueTask;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

interface CatalogueWS {
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/dokana/catalogs")
    Call<List<Catalog>> GetCatalogue(@Header("Authorization") String auth);
}
public class CatalogueAPI {
    public void getCatalogue(Context context, String token, InitCatalogueTableCallback callBack) {

        CatalogueWS api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(CatalogueWS.class);
        Call<List<Catalog>> call = api.GetCatalogue("bearer " + token);
        call.clone().enqueue(new Callback<List<Catalog>>() {
            @Override
            public void onResponse(Call<List<Catalog>> call, Response<List<Catalog>> response) {
                if (response.isSuccessful()) {
                    try {
                        callBack.onCatalogueCallSuccess();
                        new InsertAllCatalogueTask(context, response.body(), callBack).execute();
                    }catch (Exception e){
                        Log.i("get catalogue exception",e.getMessage());
                    }


                } else {
                    callBack.onCatalogueCallFailed();
                }
            }
            @Override
            public void onFailure(Call<List<Catalog>> call, Throwable t) {
                callBack.onCatalogueCallFailed();
            }
        });
    }
}
