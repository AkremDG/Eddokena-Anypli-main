package com.digid.eddokenaCM.WebServices.Historique;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.DeleteEnteteLigneCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.InitEnteteTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete.InsertAllEnteteTask;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.InitLigneTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.InsertAllLigneCallback;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

interface historiqueWS {

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/dokana/orders")
    Call<List<Order>> GetHistorique(@Query("co_no") Long coId, @Query("ct_num") String ctNum, @Header("Authorization") String auth);
}
public class HistoriqueAPI implements DeleteEnteteLigneCallback {

    public void getCmdEntete(Context context, String token, InitEnteteTableCallback callBack, InitLigneTableCallback callback, InsertAllLigneCallback lignesCallback) {

        historiqueWS api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(historiqueWS.class);
        Call<List<Order>>   call = api.GetHistorique(null,SessionManager.getInstance().getUserId(context),"bearer " + token);
        call.clone().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {

                List<Order> ordersList= new ArrayList<>();

                Log.i("kkkkkkkkkkkkkkl", String.valueOf(response.body()));

                if (response.isSuccessful()) {
                    try {
                        ordersList.addAll(response.body());
                        callBack.onEnteteCallSuccess();
                        /*
                        for(Order order : ordersList){
                            if(order.getStatus().equals("new")){
                                List<OrderItem> orderItemList = new ArrayList<>();
                                orderItemList.addAll(order.getLigneList());
                                for(OrderItem orderItem : orderItemList){
                                    orderItem.setFirstOrderRelated(true);
                                }
                                order.setLigneList(orderItemList);
                            }
                        }
                         */


                        new InsertAllEnteteTask(context, ordersList, callBack,lignesCallback).execute();

                        Log.i("historique", "onResponse: ");
                    }catch (Exception e) {
                        Log.i("getCmdEntete", e.getMessage());
                    }

                } else {
                    Log.i("historique", "onResponse: ");
                    callBack.onEnteteCallFailed();
                    Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.i("justtest1", "onFailure: " + t.getMessage());
                callBack.onEnteteCallFailed();

                Toast.makeText(context, "CALL FAIL", Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void ontEnteteLignesDeleteSucces() {

    }

    @Override
    public void ontEnteteLignesDeleteError() {

    }
}
