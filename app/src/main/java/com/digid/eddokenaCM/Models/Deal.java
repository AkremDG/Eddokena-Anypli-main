package com.digid.eddokenaCM.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Deal")
public class Deal {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = "idArticle")
    private Long idArticle;

    @ColumnInfo(name = "quantity")
    @SerializedName("quantity")
    private Float quantity;

    @ColumnInfo(name = "originalPrice")
    @SerializedName("originalPrice")
    private Double originalPrice;

    @ColumnInfo(name = "discountedPrice")
    @SerializedName("discountedPrice")
    private Double discountedPrice;

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    private Double discount;

    @ColumnInfo(name = "packingType")
    @SerializedName("packingType")
    private String packingType;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "articleName")
    private String articleName;

    @ColumnInfo(name = "articleDescription")
    private String articleDescription;

    @Ignore
    @SerializedName("article")
    private Article article;

    public Deal() {
    }

    public Deal(@NonNull Long id, Long idArticle, Float quantity, Double originalPrice, Double discountedPrice, Double discount, String packingType, String type, String articleName, String articleDescription, Article article) {
        this.id = id;
        this.idArticle = idArticle;
        this.quantity = quantity;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.discount = discount;
        this.packingType = packingType;
        this.type = type;
        this.articleName = articleName;
        this.articleDescription = articleDescription;
        this.article = article;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
