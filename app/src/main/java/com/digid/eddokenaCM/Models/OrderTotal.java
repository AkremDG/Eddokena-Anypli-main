package com.digid.eddokenaCM.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderTotal {

    // DocEntete reference code
    @SerializedName("taxFreeAmount")
    private float taxFreeAmount;

    // DocEntete reference code
    @SerializedName("subAmount")
    private float subAmount;

    // DocEntete reference code
    @SerializedName("shippingFee")
    private float shippingFee;

    // DocEntete reference code
    @SerializedName("discountAmount")
    private float discountAmount;

    // DocEntete reference code
    @SerializedName("totalAmount")
    private float totalAmount;

    public float getTaxFreeAmount() {
        return taxFreeAmount;
    }

    public void setTaxFreeAmount(float taxFreeAmount) {
        this.taxFreeAmount = taxFreeAmount;
    }

    public float getSubAmount() {
        return subAmount;
    }

    public void setSubAmount(float subAmount) {
        this.subAmount = subAmount;
    }

    public float getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(float shippingFee) {
        this.shippingFee = shippingFee;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
