package com.digid.eddokenaCM.Room.DataAcess.Tasks.FCmdEntete;

import com.digid.eddokenaCM.Models.Order;

import java.util.List;

public interface SelectEnteteLocalCallback {

    public void onLocalEnteteSelectionSuccess(List<Order> orderList);
}
