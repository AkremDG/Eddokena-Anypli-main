package com.digid.eddokenaCM.Room.DataAcess.Tasks;

import com.digid.eddokenaCM.Models.Order;

import java.util.List;

public interface GetCmdListByIdCallback {

    public void onGetCmdListSuccess(List<Order> orderList);
}
