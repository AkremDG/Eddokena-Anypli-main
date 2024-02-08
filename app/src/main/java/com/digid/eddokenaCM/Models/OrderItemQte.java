package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class OrderItemQte {

    @SerializedName("packingType")
    private String packingType;

    // qty is integer not double
    @SerializedName("qty")
    private double qty;

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "OrderItemQte{" +
                "packingType='" + packingType + '\'' +
                ", qty=" + qty +
                '}';
    }
}
