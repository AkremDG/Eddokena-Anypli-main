package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticlePrice {

    @SerializedName("clientCategoryId")
    private Long clientCategoryId;

    @SerializedName("maxOrderQty")
    private Double maxOrderQty;

    @SerializedName("minOrderQty")
    private Double minOrderQty;

    @SerializedName("discounts")
    private List<ArticlePriceDiscount> discounts;

    @SerializedName("packingPrices")
    private List<ArticlePricePcs> pcsPrices;

    public ArticlePrice(Long clientCategoryId, List<ArticlePricePcs> pcsPrices) {
        this.clientCategoryId = clientCategoryId;
        this.pcsPrices = pcsPrices;
    }

    public Long getClientCategoryId() {
        return clientCategoryId;
    }

    public void setClientCategoryId(Long clientCategoryId) {
        this.clientCategoryId = clientCategoryId;
    }

    public Double getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(Double maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public Double getMinOrderQty() {
        return minOrderQty;
    }

    public void setMinOrderQty(Double minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public List<ArticlePriceDiscount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<ArticlePriceDiscount> discounts) {
        this.discounts = discounts;
    }

    public List<ArticlePricePcs> getPcsPrices() {
        return pcsPrices;
    }

    public void setPcsPrices(List<ArticlePricePcs> pcsPrices) {
        this.pcsPrices = pcsPrices;
    }

    @Override
    public String toString() {
        return "ArticlePrice{" +
                "clientCategoryId=" + clientCategoryId +
                ", maxOrderQty=" + maxOrderQty +
                ", minOrderQty=" + minOrderQty +
                ", discounts=" + discounts +
                ", pcsPrices=" + pcsPrices +
                '}';
    }
}
