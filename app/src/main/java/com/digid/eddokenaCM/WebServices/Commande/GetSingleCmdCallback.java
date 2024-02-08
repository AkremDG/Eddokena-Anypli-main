package com.digid.eddokenaCM.WebServices.Commande;

import com.digid.eddokenaCM.Models.Order;

public interface GetSingleCmdCallback {

    public void getSingleCmdSuccess(Order order);

    public void getSingleCmdFailed();
}
