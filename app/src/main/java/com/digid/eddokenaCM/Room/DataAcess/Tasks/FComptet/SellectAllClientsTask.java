package com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.List;

public class SellectAllClientsTask extends AsyncTask<Void, Void, List<Client>> {

    Context context;
    SellectAllClientsCallBack callback;

    public SellectAllClientsTask(Context context, SellectAllClientsCallBack callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected List<Client> doInBackground(Void... voids) {
        List<Client> list = MyDB.getInstance(context).fComptetDAO().findAll();

        for (Client clt : list
             ) {
            clt.setClientScopes(MyDB.getInstance(context).fComptetDAO().findAllScopes(clt.getId()));
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Client> clientsList) {
        super.onPostExecute(clientsList);
        callback.onSelectionSuccess(clientsList);
    }
}
