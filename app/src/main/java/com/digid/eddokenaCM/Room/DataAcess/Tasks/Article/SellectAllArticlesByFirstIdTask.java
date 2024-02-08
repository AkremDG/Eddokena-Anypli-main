package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.List;

public class SellectAllArticlesByFirstIdTask extends AsyncTask<Void, Void, List<Article>> {

    Context context;
    long id;
    SellectAllArticlesByFirstIdCallback callback;

    public SellectAllArticlesByFirstIdTask(Context context, long id, SellectAllArticlesByFirstIdCallback callback) {
        this.context = context;
        this.id = id;
        this.callback = callback;
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        return MyDB.getInstance(context).fAritcleDAO().findAllByLevelId(id);
    }

    @Override
    protected void onPostExecute(List<Article> articleList) {
        super.onPostExecute(articleList);
        callback.onFirstSelectionSuccess(articleList);
    }

}