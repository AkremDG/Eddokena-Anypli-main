package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne;

public interface InitLigneTableCallback extends InsertAllLigneCallback {

    public void onLigneCallFailed();

    public void onLigneCallSuccess();
}
