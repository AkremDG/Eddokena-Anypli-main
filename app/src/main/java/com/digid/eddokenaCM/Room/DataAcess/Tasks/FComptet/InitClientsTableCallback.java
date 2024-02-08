package com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet;

public interface InitClientsTableCallback extends InsertAllClientsCallBack {

    public void onClientCallFailed();

    public void onClientCallSuccess();
}
