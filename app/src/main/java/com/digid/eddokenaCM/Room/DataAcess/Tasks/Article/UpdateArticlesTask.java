package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Room.MyDB;

public class UpdateArticlesTask extends AsyncTask<Void, Void, Integer> {

    Context context;
    Article article;
    UpdateArticlesCallBack callBack;

    public UpdateArticlesTask(Context context, Article article, UpdateArticlesCallBack callBack) {
        this.context = context;
        this.article = article;
        this.callBack = callBack;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        return MyDB.getInstance(context).fAritcleDAO().update(article);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if (integer == -1) {
            callBack.onUpdateError();
        } else {
            callBack.onUpdateSuccess(integer);
        }
    }
}
