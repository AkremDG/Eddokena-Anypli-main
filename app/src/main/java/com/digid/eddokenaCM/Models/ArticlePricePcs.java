package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ArticlePricePcs")
public class ArticlePricePcs {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "idArticle")
    private Long idArticle;

    @ColumnInfo(name = "clientCategoryId")
    private Long clientCategoryId;

    @ColumnInfo(name = "maxOrderQty")
    private Double maxOrderQty;

    @ColumnInfo(name = "minOrderQty")
    private Double minOrderQty;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;

    @ColumnInfo(name = "amount")
    @SerializedName("amount")
    private Double amount;

    @ColumnInfo(name = "discountStartDate")
    private String discountStartDate;

    @ColumnInfo(name = "discountEndDate")
    private String discountEndDate;

    @ColumnInfo(name = "discountNameFr")
    private String discountNameFr;

    @ColumnInfo(name = "discountStatus")
    private String discountStatus;

    @ColumnInfo(name = "discountStock")
    private Double discountStock;

    @ColumnInfo(name = "discountRemainingStock")
    private Double discountRemainingStock;

    @ColumnInfo(name = "discountType")
    private String discountType;

    @ColumnInfo(name = "discountAmount")
    private Double discountPercentage;

    public ArticlePricePcs(Long idArticle, Long clientCategoryId, Double maxOrderQty, Double minOrderQty, String type, Double amount, String discountStartDate, String discountEndDate, String discountNameFr, String discountStatus, Double discountStock, Double discountRemainingStock, String discountType, Double discountPercentage) {
        this.idArticle = idArticle;
        this.clientCategoryId = clientCategoryId;
        this.maxOrderQty = maxOrderQty;
        this.minOrderQty = minOrderQty;
        this.type = type;
        this.amount = amount;
        this.discountStartDate = discountStartDate;
        this.discountEndDate = discountEndDate;
        this.discountNameFr = discountNameFr;
        this.discountStatus = discountStatus;
        this.discountStock = discountStock;
        this.discountRemainingStock = discountRemainingStock;
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDiscountStartDate() {
        return discountStartDate;
    }

    public void setDiscountStartDate(String discountStartDate) {
        this.discountStartDate = discountStartDate;
    }

    public String getDiscountEndDate() {
        return discountEndDate;
    }

    public void setDiscountEndDate(String discountEndDate) {
        this.discountEndDate = discountEndDate;
    }

    public String getDiscountNameFr() {
        return discountNameFr;
    }

    public void setDiscountNameFr(String discountNameFr) {
        this.discountNameFr = discountNameFr;
    }

    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}

