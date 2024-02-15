package com.digid.eddokenaCM.Room.DataAcess.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Room.MyDB;

public class DeleteAllTask extends AsyncTask<Void, Void, Integer> {
    Context context;
    int action;//0 login, 1 historique, 2 sync
    DeleteAllCallback callback;

    public DeleteAllTask(Context context,int action, DeleteAllCallback callback) {
        this.context = context;
        this.action=action;
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        int resultEnt = -1;
        int resultLig = -1;
        int resultArt = -1;
        int resultArtPrice = -1;
        int resultArtPcs = -1;
        int resultClt = -1;
        int resultCat = -1;
        int resultCon = -1;
        //Newwwwwwwwwwwwwwwwwwwwwwwwwwwwww for action==1
        int resultArticleStock = -1;
        int resultArticle = -1;
        //Newwwwwwwwwwwwwwwwwwwwwwwwwwwwww for action==else
        int resultArticleStockSynch = -1;
        //Newwwwwwwwwwwwwwwwwwwwwwwwwwwwww for action==0
        int resultArticleStockLogin = -1;

        if (action==0){

            try {
                resultArtPrice = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePrice();
                resultArtPcs = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePcs();
                resultArt = MyDB.getInstance(context).fAritcleDAO().deleteAll();
                resultClt = MyDB.getInstance(context).fComptetDAO().deleteAll();
                resultCat = MyDB.getInstance(context).fCatalogueDAO().deleteAll();
                resultCon = MyDB.getInstance(context).conditionDao().deleteAll();
                resultArticleStockLogin = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlesStock();
                return resultArt+resultClt+resultCat+resultCon+resultArtPcs+resultArtPrice+resultArticleStockLogin;
            }catch (Exception e){
                return null;
            }



        } else if (action==1){
            Log.i("unnnnnnnnnnn", "ici");

            try{
                resultEnt = MyDB.getInstance(context).fCmdEnteteDAO().deleteAll();
                resultLig = MyDB.getInstance(context).fCmdLigneDAO().deleteAll();
                resultArticleStock = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlesStock();
                resultArticle = MyDB.getInstance(context).fAritcleDAO().deleteAll();

                return resultEnt + resultLig + resultArticleStock + resultArticle;
            }catch (Exception e){
                return null;
            }

        } else {
            Log.i("elseeeeeeeeeeeee", "ici");

            try {
                resultArtPrice = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePrice();
                resultArtPcs = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePcs();
                resultArt = MyDB.getInstance(context).fAritcleDAO().deleteAll();
                resultClt = MyDB.getInstance(context).fComptetDAO().deleteAll();
                resultCat = MyDB.getInstance(context).fCatalogueDAO().deleteAll();
                resultCon = MyDB.getInstance(context).conditionDao().deleteAll();
                resultArticleStockSynch = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlesStock();

                return resultArt+resultClt+resultCat+resultCon+resultArtPrice+resultArtPcs+resultArticleStockSynch;
            }catch (Exception e){
                return null;
            }

        }

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (action==0) {

            if(result == null){
                callback.onDelteError(result);
            }else {
                callback.onDeleteSucces();
            }

            /*OLD CHARFA
            if (result != -7) {
                Log.i("AAAAAAAAAAAAAAAK", String.valueOf(result));

            } else {
                callback.onDelteError(result);

            }

             */

        } else if (action==1) {
            if(result == null){
                callback.onDelteError(result);
            }else {
                callback.onDeleteSucces();
            }
            /*old charfaaa
            if (result != -4) {
                //Newwwwwwwwwwwwwwwwwwwww
                callback.onDeleteSucces();

            } else {
                callback.onDelteError(result);

            }

             */
        } else {

            if(result == null){
                callback.onDelteError(result);
            }else {
                callback.onDeleteSucces();
            }
            /*
            if (result != -7) {

                callback.onDeleteSucces();
                Log.i("sab3aaa", "true: ");
            } else {
                callback.onDelteError(result);
                Log.i("sab3aaa", "false: ");

            }
             */
        }

    }
}
