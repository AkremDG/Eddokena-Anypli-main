package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import com.digid.eddokenaCM.Models.Article;

import java.util.List;

public interface SellectAllArticlesByFirstIdCallback {
    public void onFirstSelectionSuccess(List<Article> articleList);
}
