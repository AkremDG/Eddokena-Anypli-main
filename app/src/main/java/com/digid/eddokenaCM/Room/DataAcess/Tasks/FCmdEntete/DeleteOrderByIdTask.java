package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Menu.Commander.ConsultationCommandFragment;
import com.digid.eddokenaCM.Room.MyDB;

public class DeleteOrderByIdTask extends AsyncTask<Void,Void,Boolean> {
    Context context;
    DeleteOrderByIdCallback callback;
    long idOrder;
    Boolean result = false;

    public DeleteOrderByIdTask(Context context, DeleteOrderByIdCallback callback, long idOrder) {
        this.context = context;
        this.callback = callback;
        this.idOrder=idOrder;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            MyDB.getInstance(context).fCmdEnteteDAO().deleteOrderById(this.idOrder);
            result=true;
        }catch (Exception e)
        {
            result=false;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean==true){
            callback.onDeleteOrderByIdSuccess();
        }else {
            callback.onDeleteOrderByIdFailure();
        }
        super.onPostExecute(aBoolean);
    }
}
