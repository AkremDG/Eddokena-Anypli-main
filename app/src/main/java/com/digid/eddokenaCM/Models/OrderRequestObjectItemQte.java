package com.digid.eddokenaCM.Models;


import com.google.gson.annotations.SerializedName;

public class OrderRequestObjectItemQte {

    @SerializedName("packingType")
    private String packingType;

    @SerializedName("qty")
    private Double qty;

    public OrderRequestObjectItemQte(String packingType, Double qty) {
        this.packingType = packingType;
        this.qty = qty;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderRequestObjectItemQte{" +
                "packingType='" + packingType + '\'' +
                ", qty=" + qty +
                '}';
    }
}
