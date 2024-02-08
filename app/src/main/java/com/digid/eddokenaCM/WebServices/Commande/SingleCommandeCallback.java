package com.digid.eddokenaCM.WebServices.Commande;

public interface SingleCommandeCallback {

    public void addCmdSuccess(Long idCmdLocal, String doPiece);

    public void addCmdFailed(String error);
}
