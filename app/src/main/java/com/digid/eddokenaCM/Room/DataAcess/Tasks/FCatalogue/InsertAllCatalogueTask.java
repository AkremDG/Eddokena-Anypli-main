package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.List;

public class InsertAllCatalogueTask extends AsyncTask<Void, Void, long[]> {
    Context context;
    List<Catalog> catalogueList;
    InsertAllCatalogueCallBack callBack;

    public InsertAllCatalogueTask(Context context, List<Catalog> catalogueList, InsertAllCatalogueCallBack insertAllCatalogueCallBack) {
        this.context = context;
        this.catalogueList = catalogueList;
        this.callBack = insertAllCatalogueCallBack;
    }


    @Override
    protected long[] doInBackground(Void... voids) {
        try {
            insertRecursiveCatalog(catalogueList, null);

            long[] done = {};

            return done;

        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            long[] faute = {-11};
            return faute;
        }
    }

    public void insertRecursiveCatalog(List<Catalog> catalogList, Long id) {
        for (Catalog catalog : catalogList) {
            Long idCat= id;

            if (id ==null) {
                idCat = MyDB.getInstance(context).fCatalogueDAO().insert(catalog);

                insertRecursiveCatalog(catalog.getChildren(), idCat);
            }
            else {
                catalog.setIdParent(idCat);

                idCat = MyDB.getInstance(context).fCatalogueDAO().insert(catalog);

                insertRecursiveCatalog(catalog.getChildren(), idCat);
            }
        }
    }

    @Override
    protected void onPostExecute(long[] result) {
        super.onPostExecute(result);

        if (result.length == 1 && result[0] == -1) {
            Log.i("doInBackground", "" + result);
            callBack.onCatalogueInsertionError();
        } else {
            callBack.onCatalogueInsertionSuccess(result);
        }
    }

}
