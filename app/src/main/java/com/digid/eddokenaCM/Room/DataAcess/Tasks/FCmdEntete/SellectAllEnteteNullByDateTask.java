package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;
import com.digid.eddokenaCM.Utils.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SellectAllEnteteNullByDateTask extends AsyncTask<Void, Void, List<Order>> {

    Context context;
    Calendar date;
    Long id;
    SellectAllEnteteNullByDateCallback callback;
    List<Order> orderList = new ArrayList<>();

    public SellectAllEnteteNullByDateTask(Context context, Long id, Calendar date, SellectAllEnteteNullByDateCallback callback) {
        this.context = context;
        this.id = id;
        this.date = date;
        this.callback = callback;
    }

    @Override
    protected List<Order> doInBackground(Void... voids) {

        try {
            orderList.addAll(MyDB.getInstance(context).fCmdEnteteDAO().findLocalByDateCo(Utilities.getInstance().getStringFromCalendar(date), id));
            orderList.addAll(MyDB.getInstance(context).fCmdEnteteDAO().findLocalByLastDateCo(Utilities.getInstance().getLastWeekDateString(date), id));
            orderList.addAll(MyDB.getInstance(context).fCmdEnteteDAO().findLocalByLastTwoDateCo(Utilities.getInstance().getLastTwoWeekDateString(date), id));

            for (Order order: orderList) {
                order.setClient(MyDB.getInstance(context).fComptetDAO().findById(order.getClientId()));
                Log.i("rrrrrrrrr", "doInBackground: "+order.getClient() );
            }
            return orderList;
        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return new ArrayList<Order>();
        }

    }
    @Override
    protected void onPostExecute(List<Order> orderList) {
        super.onPostExecute(orderList);
        callback.onSelectionEnteteNullSuccess(orderList);
    }
}
