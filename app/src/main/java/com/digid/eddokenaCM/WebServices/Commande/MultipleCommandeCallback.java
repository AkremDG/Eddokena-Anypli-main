package com.digid.eddokenaCM.WebServices.Commande;

import java.util.HashMap;

public interface MultipleCommandeCallback {

    public void addCmdListSuccess(HashMap<Long, String> doPieceMap);

    public void addCmdListFailed(String error);
}
