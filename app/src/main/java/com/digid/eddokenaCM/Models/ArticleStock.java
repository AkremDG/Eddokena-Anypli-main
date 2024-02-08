package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ArticleStock")
public class ArticleStock {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private Long id;

    @ColumnInfo(name = "idArticle")
    private Long idArticle;

    @SerializedName("realStock")
    @ColumnInfo(name="realStock")
    private Double realStock;

    @SerializedName("warehouseId")
    @ColumnInfo(name="warehouseId")
    private int warehouseId;

    @SerializedName("currentStock")
    @ColumnInfo(name="currentStock")
    private Double currentStock;

    public ArticleStock(@NonNull Long id, Long idArticle, Double realStock, int warehouseId, Double currentStock) {
        this.id = id;
        this.idArticle = idArticle;
        this.realStock = realStock;
        this.warehouseId = warehouseId;
        this.currentStock = currentStock;
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

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
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
}
