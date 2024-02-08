package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

public interface InsertArticleCallBack {

    public void onArticleInsertionError();

    public void onArticleInsertionSuccess(long id);
}
