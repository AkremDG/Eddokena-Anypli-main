package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SellectAllEnteteNotNullByDateTask extends AsyncTask<Void, Void, List<Order>> {

    Context context;
    Calendar date;
    Long id;
    SellectAllEnteteNotNullByDateCallback callback;
    List<Order> orderList = new ArrayList<>();

    public SellectAllEnteteNotNullByDateTask(Context context, Long id, Calendar date, SellectAllEnteteNotNullByDateCallback callback) {
        this.context = context;
        this.id = id;
        this.date = date;
        this.callback = callback;
    }

    @Override
    protected List<Order> doInBackground(Void... voids) {

        try {
            orderList.addAll(MyDB.getInstance(context).fCmdEnteteDAO().findByDateCoAll());
//orderList.addAll(MyDB.getInstance(context).fCmdEnteteDAO().findByLastDateCo(Utilities.getInstance().getLastWeekDateString(date), id));
//orderList.addAll(MyDB.getInstance(context).fCmdEnteteDAO().findByLastTwoDateCo(Utilities.getInstance().getLastTwoWeekDateString(date), id));

            for (Order order: orderList
            ) {
                Log.i("ttttttttttttttttttt", "doInBackground: "+ order.getRef());
                Log.i("ttttttttttttttttttt", "doInBackground: "+ order.getIdBo());
                Log.i("ttttttttttttttttttt", "doInBackground: "+ order.getCoNo());
                Log.i("ttttttttttttttttttt", "doInBackground: "+ order.getStatus());
                Log.i("ttttttttttttttttttt", "doInBackground: "+ order.getDoDate());


                order.setClient(MyDB.getInstance(context).fComptetDAO().findById(order.getClientId()));
                Log.i("ORDER LOCAL",order.toString());

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
        callback.onSelectionEnteteNotNullSuccess(orderList);
    }
}
