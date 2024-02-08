package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue;

public interface InitCatalogueTableCallback extends InsertAllCatalogueCallBack {

    public void onCatalogueCallFailed();

    public void onCatalogueCallSuccess();

}
