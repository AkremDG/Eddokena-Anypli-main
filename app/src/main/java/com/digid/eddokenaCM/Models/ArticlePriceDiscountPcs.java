package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlePriceDiscountPcs {

    @SerializedName("type")
    private String type;

    @SerializedName("percentage")
    private Double percentage;

    public ArticlePriceDiscountPcs(String type, Double percentage) {
        this.type = type;
        this.percentage = percentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
