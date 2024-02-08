package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCatalogue;

public interface InsertAllCatalogueCallBack {

    public void onCatalogueInsertionError();

    public void onCatalogueInsertionSuccess(long[] id);

}
