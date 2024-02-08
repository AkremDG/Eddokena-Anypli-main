package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.ArticlePcs;
import com.digid.eddokenaCM.Models.ArticlePcsMesure;
import com.digid.eddokenaCM.Models.ArticlePrice;
import com.digid.eddokenaCM.Models.ArticlePricePcs;
import com.digid.eddokenaCM.Models.ArticleStock;
import com.digid.eddokenaCM.Models.DealTarget;
import com.digid.eddokenaCM.Models.DealTargetFilter;
import com.digid.eddokenaCM.Models.DiscountsTarget;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class SellectAllArticlesWithConditionTask extends AsyncTask<Void, Void, List<Article>>
         {

    private Context context;
    private DealTargetFilter dealTargetFilter;
    private Long cat;
    private Long id; // kif kif catId
    private Long catId; // kif kif id
    private List<Long> list= new ArrayList<>();
    private Integer level;
    private String frag;
    private SellectAllArticlesCallBack callback;

    private List<DiscountsTarget> lisDiscountsTargets = new ArrayList<>();


    public SellectAllArticlesWithConditionTask(Context context, DealTargetFilter dealTargetFilter, Long id, Long catId, Integer level , Long cat, String frag, List<Long> list, SellectAllArticlesCallBack callback) {
        this.context = context;
        this.dealTargetFilter = dealTargetFilter;
        this.level = level;
        this.id = id;
        this.catId = catId;
        this.cat = cat;
        this.frag = frag;
        this.list = list;
        this.callback = callback;
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {

        if (frag.equals("Menu")){
            List<Article> list = MyDB.getInstance(context).fAritcleDAO().findAllDeals();
            for (Article art : list) {
                if (art.getType().equals("deal")){
                    art.setDealItems( MyDB.getInstance(context).fAritcleDAO().findAllArticleDeals(art.getId()));
                }
            }

            return list;
        }
        else {
            List<Long> listIds = new ArrayList<>();
            if (list != null){
                if (list.size() != 0) {
                    if (id != null)
                        listIds = MyDB.getInstance(context).fAritcleDAO().findAllArticleByLevelIdScope(level, id, list);
                    else
                        listIds = MyDB.getInstance(context).fAritcleDAO().findAllArticleByLevelScope(level, list);
                } else {
                    if (id != null)
                        listIds = MyDB.getInstance(context).fAritcleDAO().findAllArticleByLevelId(level, id);
                    else
                        listIds = MyDB.getInstance(context).fAritcleDAO().findAllArticleByLevel(level);
                }
            } else {
                if (id != null)
                    listIds = MyDB.getInstance(context).fAritcleDAO().findAllArticleByLevelId(level, id);
                else
                    listIds = MyDB.getInstance(context).fAritcleDAO().findAllArticleByLevel(level);
            }

            List<Article> articleList = MyDB.getInstance(context).fAritcleDAO().findAll(listIds);
            List<Article> articleListForRemove = new ArrayList<>();


            //articleList.removeAll(articleListForRemove)

            for (Article art : articleList) {

                long warehouseId=0;

                if (id !=null) {

                    warehouseId = MyDB.getInstance(context).fAritcleDAO().getWarehouseIdByCategory(catId);

                    ArticleStock articleStock = MyDB.getInstance(context).fAritcleDAO().geArticleStocks(warehouseId, art.getId());

                    if(articleStock!=null)
                    {
                        art.setCurrentStock(articleStock.getCurrentStock());
                    }else
                    {
                        Log.i("xxx",String.valueOf(id) );
                        Log.i("xxx",String.valueOf(art.getId()));
                        Log.i("xxx",String.valueOf(warehouseId));

                        Log.i("MyARRRTicle",String.valueOf(art.getNameFr()));
                        art.setCurrentStock(0d);
                    }

                }
                else {


                        // dima -1
                    art.setCurrentStock(-1d);

                    long idCatalog=     MyDB.getInstance(context).fAritcleDAO().articleGetIdCatalogByArticleId(art.getId());
                    long warehouseIdClient = MyDB.getInstance(context).fAritcleDAO().getWarehouseIdByCategory(idCatalog);

                    ArticleStock articleStock = MyDB.getInstance(context).fAritcleDAO().geArticleStocks(warehouseIdClient, art.getId());

                        if(articleStock != null){
                            art.setCurrentStock(articleStock.getCurrentStock());
                        }else{
                            art.setCurrentStock(0d);

                        }


                    /* VERSION NJIBOU LES ARTICLES LKOLL M LOWEL
                    List<String> clientsCategories = new ArrayList<>();


                    clientsCategories = MyDB.getInstance(context).fAritcleDAO().getListCatalogueIdByClient(dealTargetFilter.getClientId());

                    List<Long> covertedToLongList = new ArrayList<>();


                    for(String valString : clientsCategories){
                        covertedToLongList.add(Long.valueOf(valString));
                        Log.i("valllerSTT", String.valueOf(valString));
                    }

                    List<Long> longs = new ArrayList<>();
                    longs.add(1l);
                    longs.add(6l);
                    longs.add(39l);
                    longs.add(44l);
                    longs.add(79l);

                    List<Long> listA;

                    listA = MyDB.getInstance(context).fAritcleDAO().getListWarehouseIdByCategory(covertedToLongList);

                    List<ArticleStock> articleStockList = MyDB.getInstance(context).fAritcleDAO().geArticleStocksList(listA, art.getId());

                     for(ArticleStock stockArt : articleStockList){

                         long articleWarehouseId =  stockArt.getWarehouseId();

                         long idCatalog=     MyDB.getInstance(context).fAritcleDAO().articleGetIdCatalogByArticleId(art.getId());
                         long warehouseIdClient = MyDB.getInstance(context).fAritcleDAO().getWarehouseIdByCategory(idCatalog);



                         if(stockArt!=null)
                        {
                            if(stockArt.getCurrentStock()==0){
                                art.setCurrentStock(0d);
                            }
                            art.setCurrentStock(stockArt.getCurrentStock());

                        }else
                        {

                            Log.i("MyARRRTicle",String.valueOf(art.getNameFr()));
                            art.setCurrentStock(0d);
                        }
                        art.setCurrentStock(stockArt.getCurrentStock());



                    }
                     */

                }

                ArticlePcsMesure carton = MyDB.getInstance(context).fAritcleDAO().findPcsByArtIdMesure(art.getId(), "carton");
                ArticlePcsMesure display = MyDB.getInstance(context).fAritcleDAO().findPcsByArtIdMesure(art.getId(), "display");
                ArticlePcsMesure unit = MyDB.getInstance(context).fAritcleDAO().findPcsByArtIdMesure(art.getId(), "unit");
                ArticlePcsMesure pallet = MyDB.getInstance(context).fAritcleDAO().findPcsByArtIdMesure(art.getId(), "pallet");

                art.setArticlePcs(new ArticlePcs(unit,display,carton,pallet));

                art.setArticleCatalogs(MyDB.getInstance(context).fAritcleDAO().findAllArticleCatalog(art.getId()));
                
                if (art.getType().equals("deal")){

                    art.setDealItems( MyDB.getInstance(context).fAritcleDAO().findAllArticleDeals(art.getId()));

                    List<DealTarget> listDealTargets = new ArrayList<>();
                    listDealTargets = MyDB.getInstance(context).dealTargetDAO().getDealTargetsByArticleId(art.getId());


                    if(dealTargetFilter!=null) {

                        if(listDealTargets.size()>0) {

                            /*
                            for (DiscountsTarget discountsTarget : lisDiscountsTargets) {
                                int resultDiscounts = 0;

                                Log.i("discountsTargetSELLLECT",String.valueOf(discountsTarget.toString()));


                                if (discountsTarget.getZone() != null) {
                                    if (dealTargetFilter.getZoneId() == discountsTarget.getZoneId())
                                        resultDiscounts = 1;
                                }


                                if (resultDiscounts == 0) {

                                    resultDiscounts = MyDB.getInstance(context).dealTargetDAO().getCountClassDiscounts(discountsTarget.getId(), dealTargetFilter.getClassId());

                                    if (resultDiscounts == 0) {

                                        resultDiscounts = MyDB.getInstance(context).dealTargetDAO().getCountCategorieDiscounts(discountsTarget.getId(),
                                                dealTargetFilter.getCategorieId());

                                        if (resultDiscounts == 0) {
                                            resultDiscounts = MyDB.getInstance(context).dealTargetDAO().getCountClientsDiscounts(discountsTarget.getId(),
                                                    dealTargetFilter.getClientId());
                                        }

                                    }
                                }

                                if (resultDiscounts == 0)
                                    articleListForRemove.add(art);

                                if (resultDiscounts > 0) {
                                    Log.i("AA", String.valueOf(art.toString()));
                                } else {
                                    Log.i("AA", "Has no bundle");
                                }


                            }
                             */

                        for (DealTarget dealTarget : listDealTargets) {
                            int result = 0;

                            if (dealTarget.getZone() != null) {
                                if (dealTargetFilter.getZoneId() == dealTarget.getZoneId())
                                    result = 1;
                            }

                            if (result == 0) {

                                result = MyDB.getInstance(context).dealTargetDAO().getCountClass(dealTarget.getId(), dealTargetFilter.getClassId());


                                if (result == 0) {

                                    result = MyDB.getInstance(context).dealTargetDAO().getCountCategorie(dealTarget.getId(),
                                            dealTargetFilter.getCategorieId());

                                    if (result == 0) {
                                        result = MyDB.getInstance(context).dealTargetDAO().getCountClients(dealTarget.getId(),
                                                dealTargetFilter.getClientId());
                                    }

                                }
                            }

                            if (result == 0)
                                articleListForRemove.add(art);

                            if (result > 0) {
                                Log.i("BBBBBBBBBBBB", String.valueOf(art.toString()));
                            } else {
                                Log.i("BBBBBBBBBBBB", "Has no bundle");
                            }


                        }

                        }


                    }
                }

                if (cat != null){

                    List<ArticlePrice> prices = new ArrayList<>();
                    List<ArticlePricePcs> pricesPcs = MyDB.getInstance(context).fAritcleDAO().findPricePcsByCat(art.getId(),cat);

                    for(ArticlePricePcs articlePricePcs : pricesPcs){

                        lisDiscountsTargets = MyDB.getInstance(context).dealTargetDAO().getDiscountTargetsByPcsId(
                                articlePricePcs.getId()
                        );

                        Log.i("TAILLLLLLLLLLLEAAAAA",String.valueOf(
                                lisDiscountsTargets.size()
                        ));

                        for (DiscountsTarget discountsTarget : lisDiscountsTargets) {
                            int resultDiscounts = 0;


                            if (discountsTarget.getZone() != null) {
                                if (dealTargetFilter.getZoneId() == discountsTarget.getZoneId())
                                    resultDiscounts = 1;
                            }

                            if (resultDiscounts == 0) {

                                resultDiscounts = MyDB.getInstance(context).dealTargetDAO().getCountClassDiscounts(discountsTarget.getId(), dealTargetFilter.getClassId());

                                if (resultDiscounts == 0) {

                                    resultDiscounts = MyDB.getInstance(context).dealTargetDAO().getCountCategorieDiscounts(discountsTarget.getId(),
                                            dealTargetFilter.getCategorieId());

                                    if (resultDiscounts == 0) {
                                        resultDiscounts = MyDB.getInstance(context).dealTargetDAO().getCountClientsDiscounts(discountsTarget.getId(),
                                                dealTargetFilter.getClientId());
                                    }

                                }
                            }

                            if (resultDiscounts == 0) {

                                articlePricePcs.setDiscountEndDate(null);
                                articlePricePcs.setDiscountStartDate(null);
                                articlePricePcs.setDiscountNameFr(null);
                                articlePricePcs.setDiscountPercentage(null);
                                articlePricePcs.setDiscountRemainingStock(null);
                                articlePricePcs.setDiscountType(null);
                                articlePricePcs.setDiscountStock(null);
                                articlePricePcs.setDiscountStatus(null);

                            }

                        }


                    }

                    prices.add(new ArticlePrice(cat, pricesPcs));
                    art.setArticlePrices(prices);

                }

            }

            if (articleListForRemove.size() != 0)
                articleList.removeAll(articleListForRemove);


            return articleList;
        }
    }
    @Override
    protected void onPostExecute(List<Article> articleList) {
        super.onPostExecute(articleList);
        callback.onSelectionSuccess(articleList);
    }


}