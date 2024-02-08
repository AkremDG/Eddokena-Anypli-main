package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class SellectAllLigneTask extends AsyncTask<Void, Void, List<OrderItem>> {

    Context context;
    SellectAllLigneCallback callback;

    public SellectAllLigneTask(Context context, SellectAllLigneCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected List<OrderItem> doInBackground(Void... voids) {
        try {
            return MyDB.getInstance(context).fCmdLigneDAO().findAll();
        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return new ArrayList<OrderItem>();
        }

    }

    @Override
    protected void onPostExecute(List<OrderItem> fCatalogueList) {
        super.onPostExecute(fCatalogueList);

        callback.onLigneSelectionSuccess(fCatalogueList);
    }
}
