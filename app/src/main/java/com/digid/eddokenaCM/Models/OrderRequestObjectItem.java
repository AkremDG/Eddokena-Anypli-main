package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequestObjectItem {

    @SerializedName("articleId")
    private Integer articleId;

    @SerializedName("quantities")
    private List<OrderRequestObjectItemQte> quantities;

    // new attribute PrixUnitaire
    private Double PrixUnitaire;
    public OrderRequestObjectItem(Integer articleId, List<OrderRequestObjectItemQte> quantities,Double PrixUnitaire) {
        this.articleId = articleId;
        this.quantities = quantities;
        this.PrixUnitaire=PrixUnitaire;
    }

    public Double getPrixUnitaire() {
        return PrixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        PrixUnitaire = prixUnitaire;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public List<OrderRequestObjectItemQte> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<OrderRequestObjectItemQte> quantities) {
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        return "OrderRequestObjectItem{" +
                "articleId=" + articleId +
                ", quantities=" + quantities +
                ", PrixUnitaire=" + PrixUnitaire +
                '}';
    }
}

