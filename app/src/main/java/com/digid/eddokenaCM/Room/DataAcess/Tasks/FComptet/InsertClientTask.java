package com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Client;
import com.digid.eddokenaCM.Room.MyDB;

public class InsertClientTask extends AsyncTask<Void, Void, Long> {

    Context context;
    Client fComptet;
    InsertClientsCallBack callback;

    public InsertClientTask(Context context, Client fComptet, InsertClientsCallBack callback) {
        this.context = context;
        this.fComptet = fComptet;
        this.callback = callback;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        return MyDB.getInstance(context).fComptetDAO().insert(fComptet);
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);

        if (result == -1) {
            callback.onInsertionError();
        } else {
            callback.onInsertionSuccess(result);
        }

    }
}
