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

        if (action==0){

            resultArtPrice = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePrice();
            resultArtPcs = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePcs();
            resultArt = MyDB.getInstance(context).fAritcleDAO().deleteAll();
            resultClt = MyDB.getInstance(context).fComptetDAO().deleteAll();

            resultCat = MyDB.getInstance(context).fCatalogueDAO().deleteAll();

            resultCon = MyDB.getInstance(context).conditionDao().deleteAll();

            Log.i("CATEEEEEEEEEEEEEE",String.valueOf(resultCat));

            return resultArt+resultClt+resultCat+resultCon+resultArtPcs+resultArtPrice;
        } else if (action==1){

            resultEnt = MyDB.getInstance(context).fCmdEnteteDAO().deleteAll();
            resultLig = MyDB.getInstance(context).fCmdLigneDAO().deleteAll();
            return resultEnt + resultLig;
        } else {
            resultArtPrice = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePrice();
            resultArtPcs = MyDB.getInstance(context).fAritcleDAO().deleteAllArticlePcs();
            resultArt = MyDB.getInstance(context).fAritcleDAO().deleteAll();
            resultClt = MyDB.getInstance(context).fComptetDAO().deleteAll();
            resultCat = MyDB.getInstance(context).fCatalogueDAO().deleteAll();
            resultCon = MyDB.getInstance(context).conditionDao().deleteAll();

            return resultArt+resultClt+resultCat+resultCon+resultArtPrice+resultArtPcs;
        }

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (action==0) {
            if (result != -6) {
                callback.onDeleteSucces();

            } else {
                callback.onDelteError(result);
            }
        } else if (action==1) {
            if (result != -2) {
                callback.onDeleteSucces();

            } else {
                callback.onDelteError(result);
            }
        } else {
            if (result != -6) {
                callback.onDeleteSucces();

            } else {
                callback.onDelteError(result);
            }
        }

    }
}