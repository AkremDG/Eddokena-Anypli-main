package com.digid.eddokenaCM.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Article")
public class  Article {

    @ColumnInfo(name = "isSelected")
    private boolean isSelected;
    @Ignore
    @SerializedName("dealTargets")
    private List<DealTarget> dealTargets;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = "idCatalog")
    private Long idCatalog;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String type;
    @ColumnInfo(name = "nameFr")
    @SerializedName("nameFr")
    private String nameFr;
    @ColumnInfo(name = "descriptionFr")
    @SerializedName("descriptionFr")
    private String descriptionFr;

    @ColumnInfo(name = "sageReference")
    @SerializedName("sageReference")
    private String sageReference;

    @Ignore
    @SerializedName("stocks")
    private List<ArticleStock> stocks;

    @ColumnInfo(name = "dealRemainingStock")
    private Double dealRemainingStock;

    @ColumnInfo(name = "realStock")
    private Double realStock;

    @ColumnInfo(name = "currentStock")
    private Double currentStock;

    @Ignore
    @SerializedName("prices")
    private List<ArticlePrice> articlePrices;

    @Ignore
    @SerializedName("pcbsObject")
    private ArticlePcs articlePcs;

    @Ignore
    @SerializedName("catalogs")
    private List<ArticleCatalog> articleCatalogs;

    @Ignore
    @SerializedName("dealItems")
    private List<Deal> dealItems;
    /*
    @Ignore
    @SerializedName("dealTargets")
    private List<ArticleCatalog> dealTargets;
    */

    private String selectedCondition;
    private double selectedConditionQte;
    private double selectedPrice;
    private double selectedQte;
    private double selectedTotalPrice;
    private double selectedTotalQte;

    private double discountedPrice;

    public Article() {
    }

    public Article(@NonNull Long id, String nameFr, String selectedCondition, double selectedQte, double selectedPrice, double selectedTotalPrice, long idCatalog) {
        this.id = id;
        this.nameFr = nameFr;
        this.selectedCondition = selectedCondition;
        this.selectedQte = selectedQte;
        this.selectedPrice = selectedPrice;
        this.selectedTotalPrice = selectedTotalPrice;
        this.idCatalog = id;
    }

    public Long getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(Long idCatalog) {
        this.idCatalog = idCatalog;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getDescriptionFr() {
        return descriptionFr;
    }

    public void setDescriptionFr(String descriptionFr) {
        this.descriptionFr = descriptionFr;
    }

    public String getSageReference() {
        return sageReference;
    }

    public void setSageReference(String sageReference) {
        this.sageReference = sageReference;
    }

    public Double getRealStock() {
        return realStock;
    }

    public void setRealStock(Double realStock) {
        this.realStock = realStock;
    }

    public Double getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Double currentStock) {
        this.currentStock = currentStock;
    }

    public List<ArticlePrice> getArticlePrices() {
        return articlePrices;
    }

    public void setArticlePrices(List<ArticlePrice> articlePrices) {
        this.articlePrices = articlePrices;
    }

    public ArticlePcs getArticlePcs() {
        return articlePcs;
    }

    public void setArticlePcs(ArticlePcs articlePcs) {
        this.articlePcs = articlePcs;
    }

    public String getSelectedCondition() {
        return selectedCondition;
    }

    public void setSelectedCondition(String selectedCondition) {
        this.selectedCondition = selectedCondition;
    }

    public double getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(double selectedPrice) {
        this.selectedPrice = selectedPrice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public double getSelectedQte() {
        return selectedQte;
    }

    public void setSelectedQte(double selectedQte) {
        this.selectedQte = selectedQte;
    }

    public double getSelectedTotalPrice() {
        return selectedTotalPrice;
    }

    public void setSelectedTotalPrice(double selectedTotalPrice) {
        this.selectedTotalPrice = selectedTotalPrice;
    }

    public double getSelectedTotalQte() {
        return selectedTotalQte;
    }

    public void setSelectedTotalQte(double selectedTotalQte) {
        this.selectedTotalQte = selectedTotalQte;
    }

    public double getSelectedConditionQte() {
        return selectedConditionQte;
    }

    public void setSelectedConditionQte(double selectedConditionQte) {
        this.selectedConditionQte = selectedConditionQte;
    }

    public List<ArticleStock> getStocks() {
        return stocks;
    }

    public void setStocks(List<ArticleStock> stocks) {
        this.stocks = stocks;
    }

    public List<ArticleCatalog> getArticleCatalogs() {
        return articleCatalogs;
    }

    public void setArticleCatalogs(List<ArticleCatalog> articleCatalogs) {
        this.articleCatalogs = articleCatalogs;
    }

    public Double getDealRemainingStock() {
        return dealRemainingStock;
    }

    public void setDealRemainingStock(Double dealRemainingStock) {
        this.dealRemainingStock = dealRemainingStock;
    }

    public List<Deal> getDealItems() {
        return dealItems;
    }

    public void setDealItems(List<Deal> dealItems) {
        this.dealItems = dealItems;
    }

    public List<DealTarget> getDealTargets() {
        return dealTargets;
    }

    public void setDealTargets(List<DealTarget> dealTargets) {
        this.dealTargets = dealTargets;
    }

    @Override
    public String toString() {
        return "Article{" +
                "isSelected=" + isSelected +
                ", dealTargets=" + dealTargets +
                ", id=" + id +
                ", idCatalog=" + idCatalog +
                ", type='" + type + '\'' +
                ", nameFr='" + nameFr + '\'' +
                ", descriptionFr='" + descriptionFr + '\'' +
                ", sageReference='" + sageReference + '\'' +
                ", stocks=" + stocks +
                ", dealRemainingStock=" + dealRemainingStock +
                ", realStock=" + realStock +
                ", currentStock=" + currentStock +
                ", articlePrices=" + articlePrices +
                ", articlePcs=" + articlePcs +
                ", articleCatalogs=" + articleCatalogs +
                ", dealItems=" + dealItems +
                ", selectedCondition='" + selectedCondition + '\'' +
                ", selectedConditionQte=" + selectedConditionQte +
                ", selectedPrice=" + selectedPrice +
                ", selectedQte=" + selectedQte +
                ", selectedTotalPrice=" + selectedTotalPrice +
                ", selectedTotalQte=" + selectedTotalQte +
                ", discountedPrice=" + discountedPrice +
                '}';
    }
}
