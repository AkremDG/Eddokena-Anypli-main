package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Room.MyDB;

public class InsertArticleTask extends AsyncTask<Void, Void, Long> {
    Context context;
    Article article;
    InsertArticleCallBack callback;

    public InsertArticleTask(Context context, Article article, InsertArticleCallBack callback) {
        this.context = context;
        this.article = article;
        this.callback = callback;

    }

    @Override
    protected Long doInBackground(Void... voids) {
        return MyDB.getInstance(context).fAritcleDAO().insert(article);
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);

        if (result == -1) {
            callback.onArticleInsertionError();
        } else {
            callback.onArticleInsertionSuccess(result);
        }

    }
}
