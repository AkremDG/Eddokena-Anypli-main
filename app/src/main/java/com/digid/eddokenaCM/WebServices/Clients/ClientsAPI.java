package com.digid.eddokenaCM.WebServices.Clients;


import android.content.Context;
import android.util.Log;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.InitClientsTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet.InsertAllClientsTask;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

interface ClientsWS {


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/dokana/clients")
    Call<List<Client>> GetClients( @Header("Authorization") String auth);
}

public class ClientsAPI {

    public void getClients(Context context, Long coId, String token, InitClientsTableCallback callBack) {


        ClientsWS api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(ClientsWS.class);
        Call<List<Client>> call = api.GetClients( "bearer " + token);
        call.clone().enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {

                try{
                    if (response.isSuccessful()) {
                        for(Client client : response.body()){
                            Log.d("Clientssssssssss",client.toString());
                        }
                        callBack.onClientCallSuccess();


                        new InsertAllClientsTask(context, response.body(), callBack).execute();


                    } else {
                        callBack.onClientCallFailed();
                    }
                }catch (Exception e){
                    Log.i("getClients Exception",e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                callBack.onClientCallFailed();
            }
        });


    }
}
