package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlePriceDiscount {

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("endDate")
    private String endDate;

    @SerializedName("nameFr")
    private String nameFr;

    @SerializedName("status")
    private String status;

    @SerializedName("discountStock")
    private Double discountStock;

    @SerializedName("discountRemainingStock")
    private Double discountRemainingStock;

    @SerializedName("discountPackingPrices")
    private List<ArticlePriceDiscountPcs> discountPackingPrices;

    //lista mtaa targetssssssssssssssssssssss
    @SerializedName("targets")
    private List<DiscountsTarget> discountsTargetList;

    public ArticlePriceDiscount(String startDate, String endDate, String nameFr, String status, Double discountStock, Double discountRemainingStock, List<ArticlePriceDiscountPcs> discountPackingPrices) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.nameFr = nameFr;
        this.status = status;
        this.discountStock = discountStock;
        this.discountRemainingStock = discountRemainingStock;
        this.discountPackingPrices = discountPackingPrices;
    }

    public List<DiscountsTarget> getDiscountsTargetList() {
        return discountsTargetList;
    }

    public void setDiscountsTargetList(List<DiscountsTarget> discountsTargetList) {
        this.discountsTargetList = discountsTargetList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDiscountStock() {
        return discountStock;
    }

    public void setDiscountStock(Double discountStock) {
        this.discountStock = discountStock;
    }

    public Double getDiscountRemainingStock() {
        return discountRemainingStock;
    }

    public void setDiscountRemainingStock(Double discountRemainingStock) {
        this.discountRemainingStock = discountRemainingStock;
    }

    public List<ArticlePriceDiscountPcs> getDiscountPackingPrices() {
        return discountPackingPrices;
    }

    public void setDiscountPackingPrices(List<ArticlePriceDiscountPcs> discountPackingPrices) {
        this.discountPackingPrices = discountPackingPrices;
    }

    @Override
    public String toString() {
        return "ArticlePriceDiscount{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", nameFr='" + nameFr + '\'' +
                ", status='" + status + '\'' +
                ", discountStock=" + discountStock +
                ", discountRemainingStock=" + discountRemainingStock +
                ", discountPackingPrices=" + discountPackingPrices +
                ", discountsTargetList=" + discountsTargetList +
                '}';
    }
}

