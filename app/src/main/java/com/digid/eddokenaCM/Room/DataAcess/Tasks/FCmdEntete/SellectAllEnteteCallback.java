package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import com.digid.eddokenaCM.Models.Order;

import java.util.List;

public interface SellectAllEnteteCallback {
    public void onEnteteSelectionSuccess(List<Order> orderList);
}