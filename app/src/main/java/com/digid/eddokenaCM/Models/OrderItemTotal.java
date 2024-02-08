package com.digid.eddokenaCM.Models;


import com.google.gson.annotations.SerializedName;

public class OrderItemTotal {

    // DocEntete reference code
    @SerializedName("taxFreeAmount")
    private float taxFreeAmount;
    @SerializedName("totalAmount")
    private float totalAmount;

    public float getTaxFreeAmount() {
        return taxFreeAmount;
    }

    public void setTaxFreeAmount(float taxFreeAmount) {
        this.taxFreeAmount = taxFreeAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderItemTotal{" +
                "taxFreeAmount=" + taxFreeAmount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
