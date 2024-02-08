package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;

public class SelectEnteteByIdLocalTask extends AsyncTask<Void, Void, Order> {

    Context context;
    long idLocal;
    SelectEnteteByIdLocalCallback callback;

    public SelectEnteteByIdLocalTask(Context context, long idLocal, SelectEnteteByIdLocalCallback callback) {
        this.context = context;
        this.idLocal = idLocal;
        this.callback = callback;
    }


    @Override
    protected Order doInBackground(Void... voids) {
        try {
            Order order = MyDB.getInstance(context).fCmdEnteteDAO().findByIdLocal(idLocal);
            order.setClient(MyDB.getInstance(context).fComptetDAO().findById(order.getClientId()));

            return order;


        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Order order) {
        super.onPostExecute(order);

        callback.onSelectionSingleEnteteSuccess(order);
    }
}
