package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

public class OrderRequestObjectExtra {

    @SerializedName("idTab")
    private Long idTab;

    public OrderRequestObjectExtra(Long idTab) {
        this.idTab = idTab;
    }

    public Long getIdTab() {
        return idTab;
    }

    public void setIdTab(Long idTab) {
        this.idTab = idTab;
    }


    @Override
    public String toString() {
        return "OrderRequestObjectExtra{" +
                "idTab=" + idTab +
                '}';
    }
}
