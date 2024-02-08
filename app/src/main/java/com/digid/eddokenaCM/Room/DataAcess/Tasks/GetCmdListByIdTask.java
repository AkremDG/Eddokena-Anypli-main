package com.digid.eddokenaCM.Room.DataAcess.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Models.Order;
import com.digid.eddokenaCM.Room.MyDB;
import com.digid.eddokenaCM.Utils.Preferences;

import java.util.List;

public class GetCmdListByIdTask extends AsyncTask<Void, Void, List<Order>> {

    Context context;
    GetCmdListByIdCallback callback;
    long deleteEntetResult = -1;
    int deleteLigneResult = -1;

    public GetCmdListByIdTask(Context context, GetCmdListByIdCallback callback) {
        this.context = context;
        this.callback = callback;
    }


    @Override
    protected List<Order> doInBackground(Void... voids) {
        try {

            List<Order> cmdList = MyDB.getInstance(context).fCmdEnteteDAO().getCmdListById(Preferences.getInstance().getAllIdCmdToSync(context));
            for (int i = 0; i < cmdList.size(); i++) {
                cmdList.get(i).setLigneList(MyDB.getInstance(context).fCmdLigneDAO().findAllByIdCmdLocal(cmdList.get(i).getIdOrder()));
            }
            return cmdList;


        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<Order> orderList) {
        super.onPostExecute(orderList);

        callback.onGetCmdListSuccess(orderList);

    }
}
