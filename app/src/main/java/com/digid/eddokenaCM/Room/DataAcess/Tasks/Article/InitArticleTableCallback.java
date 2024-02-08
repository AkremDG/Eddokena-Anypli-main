package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import com.digid.eddokenaCM.Models.Article;

import java.util.List;

public interface InitArticleTableCallback extends InsertAllArticleCallBack {

    public void onArticleCallFailed();

    public void onArticleCallSuccess();

    // new method added getArticlesListCallback
    public void getArticlesListCallback(List<Article> articleList);

}
