package com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.List;

public class InsertAllClientsTask extends AsyncTask<Void, Void, Void> {
    Context context;
    List<Client> fComptetList;
    InsertAllClientsCallBack callBack;

    public InsertAllClientsTask(Context context, List<Client> fComptetList, InsertAllClientsCallBack insertAllClientsCallBack) {
        this.context = context;
        this.fComptetList = fComptetList;
        this.callBack = insertAllClientsCallBack;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //MyDB.getInstance(context).fComptetDAO().insertAll(fComptetList);
            for (Client clt : fComptetList
            ) {
                if (clt.getDefaultShop() != null){
                    clt.setShopId(clt.getDefaultShop().getId());
                    clt.setShopName(clt.getDefaultShop().getName());
                    clt.setShopLat(clt.getDefaultShop().getLatitude());
                    clt.setShopLon(clt.getDefaultShop().getLogitude());
                }
                if (clt.getClientCat() != null) {
                    clt.setCategoryId(clt.getClientCat().getId());
                    clt.setCategoryName(clt.getClientCat().getNameFr());
                }
                if (clt.getClientClass() != null) {
                    clt.setCltClass(clt.getClientClass().getNameFr());
                }
                if (clt.getClientScopes() != null){
                    MyDB.getInstance(context).fComptetDAO().insertAllScopes(clt.getClientScopes());
                }
                MyDB.getInstance(context).fComptetDAO().insert(clt);
            }

            callBack.onClientInsertionSuccess();
        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            long[] faute = {-11};
        }

        return null;
    }
}
