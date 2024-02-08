package com.digid.eddokenaCM.WebServices.Authentification;

import android.content.Context;
import android.util.Log;

import com.digid.eddokenaCM.Models.LoginRequest;
import com.digid.eddokenaCM.Models.LoginResponse;
import com.digid.eddokenaCM.Models.User;
import com.digid.eddokenaCM.Utils.SessionManager;
import com.digid.eddokenaCM.WebServices.RetrofitClient;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface AuthentificationWS {
    @Headers({"Accept: application/json"})
    @POST("api/dokana/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/auth")
    Call<User> getUser(@Query("login") String login,
                       @Query("password") String pwd,
                       @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/setregid")
    Call<Object> setRegistrationId(@Query("Id") int id,
                                   @Query("registrationid") String registrationId,
                                   @Header("Authorization") String auth);
}

public class AuthentificationAPI {


    public void getToken(Context context, String login, String mdp, AuthenticationCallback callback) {

        AuthentificationWS api = RetrofitClient.getInstance().GetRetrofitClientWS("").create(AuthentificationWS.class);
        Call<LoginResponse> call = api.login(new LoginRequest( login, mdp));
        call.clone().enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
//                    JsonParser jsonParser = new JsonParser();
//                    JsonObject jo = (JsonObject) jsonParser.parse(response.body().toString());
//                    String token = jo.get("access_token").toString().replace("\"", "");
//                    Long secondsFloat = response.body().getExpiresIn();
//                    int seconds = (int) secondsFloat;
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.add(Calendar.SECOND,seconds);
//                    SessionManager.getInstance().setExpireDate(context,calendar);

                    try {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_MONTH,7);
                        SessionManager.getInstance().setExpireDate(context,calendar);


                        SessionManager.getInstance().setToken(context, response.body().getAccessToken());

                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getUser().getProfileId());

                        SessionManager.getInstance().setUserCoId(context, Long.parseLong(response.body().getUser().getSageCode()));
                        SessionManager.getInstance().setUserId(context, "" + Long.parseLong(response.body().getUser().getSageCode()));

                        SessionManager.getInstance().setUserCoId(context, Long.parseLong(response.body().getUser().getSageCode()));
                        SessionManager.getInstance().setUserId(context, "" + Long.parseLong(response.body().getUser().getSageCode()));

                        SessionManager.getInstance().setUserName(context, response.body().getUser().getFirstName() + "  " + response.body().getUser().getLastName());

                        Log.i("tokkkkkkkk",response.body().getAccessToken());


                        callback.authenticationSuccess();
                    }catch (Exception e){
                        Log.i("getToken Exception",e.getMessage());
                    }

                } else {
                    callback.authenticationFailed();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.authenticationFailed();
                Log.i("authentficate", "onFailure: ");
            }
        });

    }

    public void getUserProfil(Context context, String login, String pwd, String token, AuthenticationCallback callback) {
        AuthentificationWS api = RetrofitClient.getInstance().GetRetrofitClientWS("").create(AuthentificationWS.class);
        Call<User> call = api.getUser(login, pwd, "bearer " + token);
        call.clone().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    SessionManager.getInstance().setToken(context, token);

                    if (response.body().getProfil() == 0) {
                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getId());
                        SessionManager.getInstance().setUserCoId(context, response.body().getCoNo());
                        SessionManager.getInstance().setUserId(context, "" + response.body().getCoNo());
                        SessionManager.getInstance().setUserName(context, response.body().getNom() + "  " + response.body().getPrenom());
                        SessionManager.getInstance().setUserType(context, response.body().getProfil());
                        SessionManager.getInstance().setBD(context,response.body().getBd());

                    } else if (response.body().getProfil() == 1) {
                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getId());
                        SessionManager.getInstance().setUserCoId(context, response.body().getCoNo());
                        SessionManager.getInstance().setUserId(context, response.body().gettNum());
                        SessionManager.getInstance().setUserName(context, response.body().getNom() + "  " + response.body().getPrenom());
                        SessionManager.getInstance().setUserType(context, response.body().getProfil());
                        SessionManager.getInstance().setUserCategorie(context, response.body().getClientCat());

                    } else if (response.body().getProfil() ==2) {

                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getId());
                        SessionManager.getInstance().setUserCoId(context, response.body().getCoNo());
                        SessionManager.getInstance().setUserId(context, "" + response.body().getCoNo());
                        SessionManager.getInstance().setUserName(context, response.body().getNom() + "  " + response.body().getPrenom());
                        SessionManager.getInstance().setUserType(context, response.body().getProfil());
                        SessionManager.getInstance().setBD(context,response.body().getBd());


                    } else if (response.body().getProfil() == 3 ){

                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getId());
                        SessionManager.getInstance().setUserCoId(context, response.body().getCoNo());
                        SessionManager.getInstance().setUserId(context, "" + response.body().getCoNo());
                        SessionManager.getInstance().setUserName(context, response.body().getNom() + "  " + response.body().getPrenom());
                        SessionManager.getInstance().setUserType(context, response.body().getProfil());
                        SessionManager.getInstance().setBD(context,response.body().getBd());

                    } else if (response.body().getProfil() == 4 ){

                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getId());
                        SessionManager.getInstance().setUserCoId(context, response.body().getCoNo());
                        SessionManager.getInstance().setUserId(context, "" + response.body().getCoNo());
                        SessionManager.getInstance().setUserName(context, response.body().getNom() + "  " + response.body().getPrenom());
                        SessionManager.getInstance().setUserType(context, response.body().getProfil());
                        SessionManager.getInstance().setBD(context,response.body().getBd());

                    } else if (response.body().getProfil() == 5 ){

                        SessionManager.getInstance().setloginDate(context);
                        SessionManager.getInstance().setUserAuthId(context, response.body().getId());
                        SessionManager.getInstance().setUserCoId(context, response.body().getCoNo());
                        SessionManager.getInstance().setUserId(context, "" + response.body().getCoNo());
                        SessionManager.getInstance().setUserName(context, response.body().getNom() + "  " + response.body().getPrenom());
                        SessionManager.getInstance().setUserType(context, response.body().getProfil());
                        SessionManager.getInstance().setBD(context,response.body().getBd());

                    }

                    //callback.authenticationSuccess(response.body());
//                    setRegistrationId(context,
//                            SessionManager.getInstance().getRegistrationId(context),
//                            SessionManager.getInstance().getUserAuthId(context),
//                            token);
                } else {
                    callback.authenticationFailed();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.authenticationFailed();
                Log.i("authentficate", "onFailure: ");
            }
        });


    }


    public void setRegistrationId(Context context, String registrationId, int idUser, String token) {

        AuthentificationWS api = RetrofitClient.getInstance().GetRetrofitClientWS("").create(AuthentificationWS.class);
        Call call = api.setRegistrationId(idUser, registrationId, "bearer " + token);
        call.clone().enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body().toString().equals("OK")){
                    Log.i("tesssssssssst", "onResponse: "+response.body().toString());
                } else {
                    Log.i("tesssssssssst", "onResponse Empty: Failed !! ");
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("tesst", "onResponse: Failed !! ");
            }
        });


    }

}



