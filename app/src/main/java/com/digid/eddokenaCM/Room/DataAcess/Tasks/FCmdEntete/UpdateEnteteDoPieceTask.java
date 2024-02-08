package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import android.content.Context;
import android.os.AsyncTask;

import com.digid.eddokenaCM.Room.MyDB;

import java.util.HashMap;

public class UpdateEnteteDoPieceTask extends AsyncTask<Void, Void, Integer> {

    Context context;
    HashMap<Long, String> idDopeceMap;
    UpdateEnteteDoPieceCallback callback;

    public UpdateEnteteDoPieceTask(Context context, HashMap<Long, String> idDopeceMap, UpdateEnteteDoPieceCallback callback) {
        this.context = context;
        this.idDopeceMap = idDopeceMap;
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Void... voids) {


        try {

            for (Long idCmd : idDopeceMap.keySet()) {
                MyDB.getInstance(context).fCmdEnteteDAO().updateEnteteDopiece(idCmd, idDopeceMap.get(idCmd));
                MyDB.getInstance(context).fCmdEnteteDAO().updateEnteteLocal(idCmd,false, "confirmed");
                MyDB.getInstance(context).fCmdLigneDAO().updateLigneDopiece(idCmd, idDopeceMap.get(idCmd));
            }

            return 1;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        if (result == null) {
            callback.onUpdateCmdListFailure();
        } else {
            callback.onUpdateCmdListSuccess();
        }

    }
}
