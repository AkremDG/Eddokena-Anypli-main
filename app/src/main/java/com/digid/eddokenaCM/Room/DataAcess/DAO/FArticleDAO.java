package com.digid.eddokenaCM.Room.DataAcess.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.ArticleCatalog;
import com.digid.eddokenaCM.Models.ArticlePcsMesure;
import com.digid.eddokenaCM.Models.ArticlePricePcs;
import com.digid.eddokenaCM.Models.ArticleStock;
import com.digid.eddokenaCM.Models.Deal;

import java.util.List;

@Dao
public interface FArticleDAO {


    @Query("SELECT * FROM Article where id=:id AND isSelected=:isSelected")
    List<Article> getAllArticlesByIdAndState(long id,Boolean isSelected);

    @Query("SELECT * FROM Article WHERE id=:id LIMIT 1")
    Article findById(Integer id);

    @Query("SELECT * FROM Article WHERE id IN (SELECT idArticle FROM ArticleCatalog WHERE level=:level)")
    List<Article> findAllByLevelId(long level);

    @Query("SELECT * FROM ArticlePcsMesures WHERE idArticle = :arRef AND packingType=:mesure")
    ArticlePcsMesure findPcsByArtIdMesure(Long arRef, String mesure);

    @Query("SELECT * FROM ArticlePricePcs WHERE idArticle = :id AND clientCategoryId=:cat")
    List<ArticlePricePcs> findPricePcsByCat(Long id, Long cat);

    @Query("SELECT * FROM Article where id IN (:list)")
    List<Article> findAll(List<Long> list);

    @Query("SELECT * FROM Article where type ='deal'")
    List<Article> findAllDeals();

    @Query("SELECT * FROM ArticlePricePcs")
    List<ArticlePricePcs> findAllArticlePricePcs();

    @Query("SELECT * FROM ArticleCatalog WHERE idArticle=:id")
    List<ArticleCatalog> findAllArticleCatalog(Long id);

    @Query("SELECT * FROM Deal WHERE idArticle=:id")
    List<Deal> findAllArticleDeals(Long id);

    @Query("SELECT * FROM ArticlePcsMesures")
    List<ArticlePcsMesure> findAllArticlePcs();

    @Query("SELECT * FROM ArticlePcsMesures WHERE idArticle=:id")
    List<ArticlePcsMesure> findByArt(long id);

    @Query("SELECT idArticle FROM ArticleCatalog WHERE level= :level and id=:id")
    List<Long> findAllArticleByLevelId(int level, Long id);

    @Query("SELECT idArticle FROM ArticleCatalog WHERE level= :level and id=:id and id in (:list)")
    List<Long> findAllArticleByLevelIdScope(int level, Long id, List<Long> list);

    @Query("SELECT idArticle FROM ArticleCatalog WHERE level= :level")
    List<Long> findAllArticleByLevel(int level);

    @Query("SELECT idArticle FROM ArticleCatalog WHERE level= :level and id in (:list)")
    List<Long> findAllArticleByLevelScope(int level, List<Long> list);

    @Query("SELECT * FROM ArticleStock WHERE idArticle= :idArticle and id = :id")
    ArticleStock findAllArticleStockByWarehouse(long idArticle, int id);

    @Query("SELECT * FROM ArticleStock WHERE warehouseId=:warehouseId AND idArticle=:articleId ")
    ArticleStock geArticleStocks(long warehouseId, long articleId);


    @Query("SELECT * FROM ArticleStock WHERE warehouseId  in (:warehouseIdList) AND idArticle=:articleId ")
   List<ArticleStock>  geArticleStocksList(List<Long> warehouseIdList, long articleId);


    @Query("SELECT warehouseId from ClientScope where catalogId=:categoryId")
    long getWarehouseIdByCategory(long categoryId);


    @Query("SELECT idCatalog FROM Article where id=:articleId")
    long articleGetIdCatalogByArticleId(long articleId);

    @Query("SELECT catalogId FROM ClientScope WHERE clientId =:clientId")
    List<String> getListCatalogueIdByClient(Long clientId);


    @Query("SELECT warehouseId FROM ClientScope WHERE catalogId IN (:listCats)")
    List<Long> getListWarehouseIdByCategory(List<Long> listCats);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Article> fArticleList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllStock(List<ArticleStock> fArticleStockList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAllDeals(List<Deal> dealList);





    @Insert
    long insert(Article article);

    @Insert
    long insertDeal(Deal deal);

    @Insert
    void insertPcsMesure(ArticlePcsMesure articlePcsMesure);

    @Insert
    long insertPricePcs(ArticlePricePcs articlePricePcs);

    @Query("SELECT id FROM ArticlePricePcs WHERE idArticle =:articleId")
    long  getArticlePricePcsByArticleId(long articleId);

    @Insert
    long insertCatalog(ArticleCatalog articleCatalog);

    @Update
    int update(Article fArticle);

    @Delete
    int delete(Article fArticle);

    @Query("DELETE FROM Article")
    int deleteAll();

    @Query("DELETE FROM ArticlePricePcs")
    int deleteAllArticlePrice();

    @Query("DELETE FROM ArticlePcsMesures")
    int deleteAllArticlePcs();

    @Query("DELETE FROM Deal")
    int deleteAllDeals();


}
