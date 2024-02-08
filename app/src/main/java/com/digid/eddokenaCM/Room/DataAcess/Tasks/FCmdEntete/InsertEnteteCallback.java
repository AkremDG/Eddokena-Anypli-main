package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

public interface InsertEnteteCallback {

    public void onEnteteInsertionError();

    public void onEnteteInsertionSuccess(long id);
}
