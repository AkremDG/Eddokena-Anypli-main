package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.digid.eddokenaCM.Models.OrderItem;
import com.digid.eddokenaCM.Room.MyDB;

import java.util.ArrayList;
import java.util.List;

public class SellectAllLigneByIdTask extends AsyncTask<Void, Void, List<OrderItem>> {

    Context context;
    String doPiece = "";
    long clientNum;
    long idLocalCmd;
    List<OrderItem> orderItemList = new ArrayList<>();
    SellectAllLigneByIdCallback callback;

    public SellectAllLigneByIdTask(Context context, String doPiece, long clientNum, long idLocalCmd, SellectAllLigneByIdCallback callback) {

        this.context = context;
        this.doPiece = doPiece;
        this.clientNum = clientNum;
        this.idLocalCmd = idLocalCmd;
        this.callback = callback;
    }

    @Override
    protected List<OrderItem> doInBackground(Void... voids) {
        try {

            if (doPiece.equals("")) {
                orderItemList = MyDB.getInstance(context).fCmdLigneDAO().findAllByIdCmdLocal(idLocalCmd);
            } else {
                orderItemList.addAll(MyDB.getInstance(context).fCmdLigneDAO().findAllByDoPiece(doPiece));
            }

            Log.i("TestModdifSelection", "doInBackground: "+orderItemList.size());
            Log.i("TestModdifSelection", "doInBackground: "+doPiece);
            Log.i("TestModdifSelection", "doInBackground: "+idLocalCmd);

            for (OrderItem ord: orderItemList
                 ) {
                ord.setArticleName(MyDB.getInstance(context).fAritcleDAO().findById(Integer.valueOf(ord.getArticleId())).getNameFr());
                ord.setArticle(MyDB.getInstance(context).fAritcleDAO().findById(ord.getArticleId()));

                ord.setClient(MyDB.getInstance(context).fComptetDAO().findById(clientNum));
                ord.getClient().setClientScopes(MyDB.getInstance(context).fComptetDAO().findAllScopes(clientNum));
            }

            return orderItemList;

        } catch (Exception e) {
            Log.i("doInBackground", "" + e);
            return new ArrayList<OrderItem>();
        }
    }

    @Override
    protected void onPostExecute(List<OrderItem> orderItems) {
        super.onPostExecute(orderItems);
        callback.onAllLigneSelectionSuccess(orderItems);
    }
}
