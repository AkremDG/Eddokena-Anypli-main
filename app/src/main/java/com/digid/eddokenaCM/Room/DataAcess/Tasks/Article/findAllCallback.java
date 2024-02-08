package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import com.digid.eddokenaCM.Models.Article;

import java.util.List;

public interface findAllCallback {
    void onFindAllCallbackError();
    void onFindAllCallbackSuccess(List<Article> listArticles);
}
