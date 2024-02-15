package com.digid.eddokenaCM.Room.DataAcess.Tasks.Article;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Article;
import com.digid.eddokenaCM.Models.ArticleCatalog;
import com.digid.eddokenaCM.Models.ArticlePrice;
import com.digid.eddokenaCM.Models.ArticlePriceDiscount;
import com.digid.eddokenaCM.Models.ArticlePriceDiscountPcs;
import com.digid.eddokenaCM.Models.ArticlePricePcs;
import com.digid.eddokenaCM.Models.ArticleStock;
import com.digid.eddokenaCM.Models.ClientCategories;
import com.digid.eddokenaCM.Models.ClientClasses;
import com.digid.eddokenaCM.Models.Clients;
import com.digid.eddokenaCM.Models.Deal;
import com.digid.eddokenaCM.Models.DealTarget;
import com.digid.eddokenaCM.Models.DiscountsTarget;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class InsertAllArticlesTask extends AsyncTask<Void, Void, Void> {

    Context context;
    List<Article> aritcleList;
    InsertAllArticleCallBack callBack;

    List<ArticlePricePcs> recapList= new ArrayList<>();

    List<ArticlePriceDiscount> priceDiscountList= new ArrayList<>();

    public InsertAllArticlesTask(Context context, List<Article> aritcleList, InsertAllArticleCallBack insertAllArticleCallBack) {
        this.context = context;
        this.aritcleList = aritcleList;
        this.callBack = insertAllArticleCallBack;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            for (Article art: aritcleList) {


                if (art.getStocks()!= null) {
                    for (ArticleStock stock : art.getStocks()) {
                        stock.setIdArticle(art.getId());
                    }

                    MyDB.getInstance(context).fAritcleDAO().insertAllStock(art.getStocks());
                }

                long id = MyDB.getInstance(context).fAritcleDAO().insert(art);

                if (art.getDealTargets() != null){

                    if(art.getDealTargets().size()>0)
                    {

                        Log.i("ARTICLAAAA",art.toString());

                        if (art.getDealTargets().get(0).getZone() != null) {
                            MyDB.getInstance(context).dealTargetDAO().insertDealTarget(
                                    new DealTarget(id,
                                            art.getDealTargets().get(0).getZone().getId(),
                                            art.getDealTargets().get(0).getId()
                                    )
                            );
                        }
                        else {
                            MyDB.getInstance(context).dealTargetDAO().insertDealTarget(
                                    new DealTarget(id,
                                            null,
                                            art.getDealTargets().get(0).getId()
                                    )
                            );
                        }

                        if (art.getDealTargets().get(0).getClientClasses() != null) {
                            for (ClientClasses cl : art.getDealTargets().get(0).getClientClasses()
                            ) {
                                MyDB.getInstance(context).dealTargetDAO().insertClientClasses(new ClientClasses(
                                        cl.getId(),
                                        art.getDealTargets().get(0).getId(),null));
                            }
                        }

                        if(art.getDealTargets().get(0).getClientCategories().get(0)!=null)
                        {
                            for(ClientCategories clientCat : art.getDealTargets().get(0).getClientCategories())
                            {
                                MyDB.getInstance(context).dealTargetDAO().isertClientCategories(new ClientCategories(
                                        clientCat.getId(),
                                        art.getDealTargets().get(0).getId(),
                                        null
                                ));
                            }
                        }

                        if(art.getDealTargets().get(0).getDealTargetsClients()!=null)
                        {
                            for(Clients ourClients : art.getDealTargets().get(0).getDealTargetsClients())
                            {
                                MyDB.getInstance(context).dealTargetDAO().insertDealTargetClients(new Clients(
                                        ourClients.getId(),
                                        art.getDealTargets().get(0).getId(),
                                        null
                                ));
                            }
                        }

                    }
                }


                if (art.getArticlePcs() != null){

                    if (art.getArticlePcs().getCarton() != null){
                        art.getArticlePcs().getCarton().setIdArticle(id);
                        Log.i("TestArticleSelecttttiiiion", "doInBackground: "+ id + " - "
                        +art.getArticlePcs().getCarton().getPackingType());
                        MyDB.getInstance(context).fAritcleDAO().insertPcsMesure(art.getArticlePcs().getCarton());
                    }

                    if (art.getArticlePcs().getDisplay() != null){
                        art.getArticlePcs().getDisplay().setIdArticle(id);
                        MyDB.getInstance(context).fAritcleDAO().insertPcsMesure(art.getArticlePcs().getDisplay());
                    }

                    if (art.getArticlePcs().getUnit() != null){
                        art.getArticlePcs().getUnit().setIdArticle(id);
                        MyDB.getInstance(context).fAritcleDAO().insertPcsMesure(art.getArticlePcs().getUnit());
                    }

                    if (art.getArticlePcs().getPallet() != null){
                        art.getArticlePcs().getPallet().setIdArticle(id);
                        MyDB.getInstance(context).fAritcleDAO().insertPcsMesure(art.getArticlePcs().getPallet());
                    }
                }

                Log.i("TestArticleSelection", "-- Size prices : "+art.getArticlePrices().size());

                int i = 0;
                if (art.getArticlePrices() != null) {

                    for (ArticlePrice price : art.getArticlePrices()) {

                        for (ArticlePricePcs pcs : price.getPcsPrices()) {
                            i++;

                            pcs.setIdArticle(id);
                            pcs.setMaxOrderQty(price.getMaxOrderQty());
                            pcs.setMinOrderQty(price.getMinOrderQty());
                            pcs.setClientCategoryId(price.getClientCategoryId());

                            Log.i("TestArticleSelectiiiion", "-- : "+art.getNameFr()+ " - "+ price.getClientCategoryId()
                                    + " - "+
                                    pcs.getType() + " - "+ pcs.getAmount());

                            if (price.getDiscounts().size()> 0){

                                for (ArticlePriceDiscount priceDiscount : price.getDiscounts()) {

                                    pcs.setDiscountStartDate(priceDiscount.getStartDate());
                                    pcs.setDiscountEndDate(priceDiscount.getEndDate());
                                    pcs.setDiscountNameFr(priceDiscount.getNameFr());
                                    pcs.setDiscountStatus(priceDiscount.getStatus());
                                    pcs.setDiscountStock(priceDiscount.getDiscountStock());
                                    pcs.setDiscountRemainingStock(priceDiscount.getDiscountRemainingStock());


                                    if (priceDiscount.getDiscountPackingPrices().size() > 0){
                                        for (ArticlePriceDiscountPcs priceDiscountPcs: priceDiscount.getDiscountPackingPrices()) {

                                            if (pcs.getType().equals(priceDiscountPcs.getType())) {
                                                pcs.setDiscountType(priceDiscountPcs.getType());
                                                pcs.setDiscountPercentage(priceDiscountPcs.getPercentage());
                                            }

                                        }
                                    }
                                    long pcsId = MyDB.getInstance(context).fAritcleDAO().insertPricePcs(pcs);


                                    if (priceDiscount.getDiscountsTargetList() != null){

                                        if(priceDiscount.getDiscountsTargetList().size()>0){

                                            Integer disZoneId=null;

                                            if(priceDiscount.getDiscountsTargetList().get(0).getZone() != null)
                                            {
                                                disZoneId=priceDiscount.getDiscountsTargetList().get(0).getZone().getId();
                                            }

                                            Log.i("insertPcsId",String.valueOf(pcsId));

                                            DiscountsTarget discountsTarget = new DiscountsTarget(
                                                    pcsId,
                                                    disZoneId,
                                                    priceDiscount.getDiscountsTargetList().get(0).getId()
                                            );


                                            MyDB.getInstance(context).dealTargetDAO().insertDiscountsTarget(discountsTarget);

                                            if(priceDiscount.getDiscountsTargetList().get(0).getClientCategories()!=null){

                                                if(priceDiscount.getDiscountsTargetList().get(0).getClientCategories().size()>0){

                                                    for(ClientCategories clientCategories : priceDiscount.getDiscountsTargetList().get(0).getClientCategories()){

                                                            MyDB.getInstance(context).dealTargetDAO().isertClientCategories(
                                                                    new ClientCategories(
                                                                            clientCategories.getId(),
                                                                            null,
                                                                            priceDiscount.getDiscountsTargetList().get(0).getId()
                                                                    )
                                                            );

                                                    }


                                                }

                                            }



                                            if(priceDiscount.getDiscountsTargetList().get(0).getClientClasses()!=null){

                                                if(priceDiscount.getDiscountsTargetList().get(0).getClientClasses().size()>0){

                                                    for(ClientClasses clientClasses : priceDiscount.getDiscountsTargetList().get(0).getClientClasses() ){


                                                        MyDB.getInstance(context).dealTargetDAO().insertClientClasses(
                                                                new ClientClasses(
                                                                        clientClasses.getId(),
                                                                        null,
                                                                        priceDiscount.getDiscountsTargetList().get(0).getId()
                                                                )
                                                        );
                                                        Log.i("clientClasses","success");
                                                    }

                                                }
                                            }


                                            if(priceDiscount.getDiscountsTargetList().get(0).getDealTargetsClients()!=null){

                                                if(priceDiscount.getDiscountsTargetList().get(0).getDealTargetsClients().size()>0){

                                                    for(Clients clients : priceDiscount.getDiscountsTargetList().get(0).getDealTargetsClients() ){

                                                        MyDB.getInstance(context).dealTargetDAO().insertDealTargetClients(
                                                                new Clients(
                                                                        clients.getId(),
                                                                        null,
                                                                        priceDiscount.getDiscountsTargetList().get(0).getId()
                                                                )
                                                        );





                                                    }


                                                }
                                            }
                                        }
                                    }

                                }

                            }


                           MyDB.getInstance(context).fAritcleDAO().insertPricePcs(pcs);

                        }


                    }
                }

                if (art.getArticleCatalogs() != null){

                    for (ArticleCatalog cat: art.getArticleCatalogs()
                         ) {

                        cat.setIdArticle(art.getId());

                        MyDB.getInstance(context).fAritcleDAO().insertCatalog(cat);
                    }
                }

                if (art.getType().equals("deal")){
                    if (art.getDealItems().size() > 0){

                        for (Deal deal : art.getDealItems()) {

                            deal.setIdArticle(art.getId());
                            deal.setArticleName(deal.getArticle().getNameFr());
                            deal.setArticleDescription(deal.getArticle().getDescriptionFr());

                            try {
                                MyDB.getInstance(context).fAritcleDAO().insertDeal(deal);
                            }catch (Exception e){
                                Log.i("EXXXXXXXXXX",e.getMessage());
                            }

                        }
                    }
                }

                Log.i("TestArticleSelection", "-- Art - Id - Pcs : "+art.getNameFr()+ " - "+ id + " - "+ i);

                Log.i("TestArticleSelection", "-- Size stock : "+art.getStocks().size());


            }

            callBack.onArticleInsertionSuccess();

        } catch (Exception e) {
            Log.i("insertAllArticlesTask exception", e.getMessage());
        }
        return null;
    }
}
