package com.digid.eddokenaCM.Room.DataAcess.Tasks.FComptet;

import com.digid.eddokenaCM.Models.Client;

public interface SellectClientByIdCallback {

    public void onSellectClientByIdSuccess(Client client);

    public void onSellectClientByIdFailed();
}
