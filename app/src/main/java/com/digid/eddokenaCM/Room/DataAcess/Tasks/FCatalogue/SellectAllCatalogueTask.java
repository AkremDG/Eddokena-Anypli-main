package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class SellectAllCatalogueTask extends AsyncTask<Void, Void, List<Catalog>> {

    Context context;
    SellectAllCatalogueCallBack callback;

    public SellectAllCatalogueTask(Context context, SellectAllCatalogueCallBack callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected List<Catalog> doInBackground(Void... voids) {


        try {
            return MyDB.getInstance(context).fCatalogueDAO().findAll();
        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return new ArrayList<Catalog>();
        }

    }

    @Override
    protected void onPostExecute(List<Catalog> fCatalogueList) {
        super.onPostExecute(fCatalogueList);

        callback.onSelectionSuccess(fCatalogueList);
    }
}
