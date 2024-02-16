package com.digid.eddokenaCM.WebServices.Commande;

import android.content.Context;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderRequest;
import com.digid.eddokenaCM.Models.OrderRequestObject;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface CommendeWs {


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/CMDID")
    Call<Order> getSingleCmd(@Query("do_piecev") String doPiece, @Header("Authorization") String auth);

    @POST("api/dokana/orders")
    Call<List<OrderRequestObject>> sendFacture(@Body OrderRequest orderGson, @Header("Authorization") String auth);


    @POST("api/Cmdlistadd")
    Call<Object> sendFatureList(@Body List<Order> cmdList, @Header("Authorization") String auth);
}

public class CommendeAPI {


    public void getSingleCmd(Context context, String doPiece, String token, GetSingleCmdCallback callback) {
        CommendeWs api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(CommendeWs.class);
        Call<Order> call = api.getSingleCmd(doPiece, "bearer " + token);
        call.clone().enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                callback.getSingleCmdSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                callback.getSingleCmdFailed();
            }
        });

    }

    public void addFacture(Context context, OrderRequest order, String token, SingleCommandeCallback callback) {
        CommendeWs api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(CommendeWs.class);
        Gson gson = new Gson().newBuilder().create();
        Call<List<OrderRequestObject>> call = api.sendFacture(order, "bearer " + token);
        call.clone().enqueue(new Callback<List<OrderRequestObject>>() {
            @Override
            public void onResponse(Call<List<OrderRequestObject>> call, Response<List<OrderRequestObject>> response) {
                    if (response.isSuccessful()){
                        try
                        {
                            if(response != null){
                                try {

                                    if(response.body().get(0).getExtra().getIdTab()!=null){
                                        callback.addCmdSuccess(response.body().get(0).getExtra().getIdTab(),
                                                response.body().get(0).getRef());
                                    }else
                                    {
                                        Log.i("TAG", "onResponse: ");
                                    }

                                } catch (Exception e){

                                    Log.i("CAUSEEEEcmd", response.errorBody().toString());
                                    callback.addCmdFailed(response.toString());

                                }
                            }
                        }catch (Exception e){
                            Log.i("addFacture Exception",e.getMessage());
                        }
                    } else {

                        Log.i("CAUSEEEEcmd", response.errorBody().toString());
                        callback.addCmdFailed(response.toString());

                    }
            }

            @Override
            public void onFailure(Call<List<OrderRequestObject>> call, Throwable t) {
                Log.i("rrrrrrrr", "onFailure: ");


                callback.addCmdFailed(t.getMessage());

            }
        });
    }

    public void addFactureList(Context context, List<Order> cmdList, String token, MultipleCommandeCallback callback) {
        CommendeWs api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(CommendeWs.class);
        Call call = api.sendFatureList(cmdList, "bearer " + token);
        call.clone().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()){



                    HashMap<Long, String> doPieceMap = new HashMap<>();
                    try {

                        JSONArray jsonResultArray = new JSONArray(response.body().toString());
                        for (int i = 0; i < jsonResultArray.length(); i++) {
                            doPieceMap.put(jsonResultArray.getJSONObject(i).getLong("id"), jsonResultArray.getJSONObject(i).getString("DO_Piece"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        doPieceMap.clear();


                    }
                    callback.addCmdListSuccess(doPieceMap);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("tesst", "onFailure: " + t.getMessage());

                callback.addCmdListFailed(t.getMessage());
            }
        });
    }
}
