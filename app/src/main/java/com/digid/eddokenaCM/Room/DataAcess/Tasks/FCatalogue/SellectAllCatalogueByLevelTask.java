package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Catalog;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class SellectAllCatalogueByLevelTask extends AsyncTask<Void, Void, List<Catalog>> {

    private Context context;
    private Long id;
    private List<Long> list= new ArrayList<>();
    private SellectAllCatalogueByLevelCallback callback;

    public SellectAllCatalogueByLevelTask(Context context, Long id, List<Long> list, SellectAllCatalogueByLevelCallback callback) {
        this.context = context;
        this.id = id;
        this.list = list;
        this.callback = callback;
    }

    @Override
    protected List<Catalog> doInBackground(Void... voids) {

        try {
            if (id ==null) {
                if (list != null) {
                    if (list.size() != 0)
                        return MyDB.getInstance(context).fCatalogueDAO().findAllByScope(list);
                    else
                        return MyDB.getInstance(context).fCatalogueDAO().findAll();
                }
                else
                    return MyDB.getInstance(context).fCatalogueDAO().findAll();
            }
            else
                return MyDB.getInstance(context).fCatalogueDAO().findAllById(id);
        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return new ArrayList<Catalog>();
        }

    }

    @Override
    protected void onPostExecute(List<Catalog> fCatalogueList) {
        super.onPostExecute(fCatalogueList);

        callback.onSelectionCatalogueSuccess(fCatalogueList);
    }
}
