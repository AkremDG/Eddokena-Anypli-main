package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne.InsertAllLigneCallback;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.List;

public class InsertAllEnteteTask extends AsyncTask<Void, Void, Boolean> {
    Context context;
    List<Order> orderList;

    InsertAllEnteteCallback callback;
    InsertAllLigneCallback callbackLignes;

    Boolean result=false;


    public InsertAllEnteteTask(Context context, List<Order> orderList, InsertAllEnteteCallback callback,InsertAllLigneCallback lignesCallback) {
        this.context = context;
        this.orderList = orderList;
        this.callback = callback;
        this.callbackLignes=lignesCallback;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {

            for (Order order : this.orderList) {
                Order newOrder = order;


;                newOrder.setTotalAmount(order.getTotal().getTotalAmount());

                long  id = MyDB.getInstance(context).fCmdEnteteDAO().insert(newOrder);

                for (OrderItem orderItem : newOrder.getLigneList()) {
                    OrderItem newOrderItem = orderItem;
                    newOrderItem.setIdCmdLocal(id);
                    newOrderItem.setIdBo(newOrder.getIdBo());
                    newOrderItem.setRef(newOrder.getRef());

                    newOrderItem.setPackingType(newOrderItem.getQuantities().get(0).getPackingType());
                    newOrderItem.setQty(newOrderItem.getQuantities().get(0).getQty());

                    newOrderItem.setTotalAmount(newOrderItem.getTotal().getTotalAmount());

                    MyDB.getInstance(context).fCmdLigneDAO().insert(newOrderItem);
                }
            }
            result=true;
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
            result=false;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(result==true){
            Log.i("historique", "onPostExecute: X "+ orderList.size());
            callback.onAllEnteteInsertionSuccess();
            callbackLignes.onLigneInsertionSuccess();
        }else {
            callback.onAllEnteteInsertionError();
            callbackLignes.onLigneInsertionError();
        }
    }

}
