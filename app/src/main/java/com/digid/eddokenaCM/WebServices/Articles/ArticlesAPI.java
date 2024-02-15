package com.digid.eddokenaCM.WebServices.Articles;


import android.content.Context;
import android.util.Log;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.InitArticleTableCallback;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.Article.InsertAllArticlesTask;
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

interface ArticlesWs {
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/dokana/articles")
    Call<List<Article>> GetArticles( @Header("Authorization") String auth);

}

public class ArticlesAPI {

    public void getArticles(Context context, InitArticleTableCallback callback) {

        ArticlesWs api = RetrofitClient.getInstance().GetRetrofitClientWS(SessionManager.getInstance().getBD(context)+"/").create(ArticlesWs.class);
        Call<List<Article>> call = api.GetArticles("bearer " + SessionManager.getInstance().getToken(context));
        call.clone().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articleList = new ArrayList<>();
                if (response.isSuccessful()) {
                    try {

                        for(Article article : response.body()){

                            article.setIdCatalog(article.getArticleCatalogs().get(0).getIdBo());

                            articleList.add(article);

                        }
                        callback.getArticlesListCallback(response.body());
                        callback.onArticleCallSuccess();
                        new InsertAllArticlesTask(context, articleList, callback).execute();

                    }catch (Exception e){
                        Log.d("GetArticles Exception",e.getMessage());
                    }

                } else {
                    callback.onArticleCallFailed();
                }


            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.i("TestArticleSelection", "onFailure: "+t.getMessage());
                callback.onArticleCallFailed();
            }
        });


    }
}
