package com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Room.MyDB;

public class SellectClientByIdTask extends AsyncTask<Void, Void, Client> {

    Context context;
    Long ctNum;
    SellectClientByIdCallback callback;

    public SellectClientByIdTask(Context context, Long ctNum, SellectClientByIdCallback callback) {
        this.context = context;
        this.ctNum = ctNum;
        this.callback = callback;
    }

    @Override
    protected Client doInBackground(Void... voids) {
        return MyDB.getInstance(context).fComptetDAO().findById(ctNum);
    }

    @Override
    protected void onPostExecute(Client client) {
        super.onPostExecute(client);

        callback.onSellectClientByIdSuccess(client);
    }
}
