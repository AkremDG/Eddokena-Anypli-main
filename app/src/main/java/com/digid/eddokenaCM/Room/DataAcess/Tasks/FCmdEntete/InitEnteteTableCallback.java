package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

public interface InitEnteteTableCallback extends InsertAllEnteteCallback {

    public void onEnteteCallFailed();

    public void onEnteteCallSuccess();
}
