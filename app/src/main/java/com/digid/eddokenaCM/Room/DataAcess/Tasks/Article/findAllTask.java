package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class findAllTask extends AsyncTask<Void,Void, List<Article>> {

    private Context context;
    private findAllCallback callback;
    private List<Long> longList;

    public findAllTask(Context context, List<Long> longList, findAllCallback callback) {
        this.context = context;
        this.callback = callback;
        this.longList = longList;
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        List<Article> resArticles= new ArrayList<>();

        try {
            resArticles =  MyDB.getInstance(context).fAritcleDAO().findAll(this.longList);

        }catch (Exception e){
            callback.onFindAllCallbackError();
            resArticles = null;

        }
        return resArticles;
    }

    @Override
    protected void onPostExecute(List<Article> articleList) {
        super.onPostExecute(articleList);

        if(articleList!=null){
            callback.onFindAllCallbackSuccess(articleList);

        }else
        callback.onFindAllCallbackError();
    }


}
