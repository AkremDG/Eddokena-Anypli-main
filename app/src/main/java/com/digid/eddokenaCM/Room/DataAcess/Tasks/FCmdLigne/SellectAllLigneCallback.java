package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdLigne;

import com.digid.eddokenaCM.Models.OrderItem;

import java.util.List;

public interface SellectAllLigneCallback {

    public void onLigneSelectionSuccess(List<OrderItem> articleList);
}
