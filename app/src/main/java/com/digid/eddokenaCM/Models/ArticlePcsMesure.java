package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ArticlePcsMesures")
public class ArticlePcsMesure {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "idArticle")
    private Long idArticle;

    @ColumnInfo(name = "packingType")
    @SerializedName("packingType")
    private String packingType;

    @ColumnInfo(name = "pcb")
    @SerializedName("pcb")
    private Double pcb;

    @ColumnInfo(name = "unitType")
    @SerializedName("unitType")
    private String unitType;

    @ColumnInfo(name = "unitLabelFr")
    @SerializedName("unitLabelFr")
    private String unitLabelFr;

    public ArticlePcsMesure() {
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

    public Double getPcb() {
        return pcb;
    }

    public void setPcb(Double pcb) {
        this.pcb = pcb;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitLabelFr() {
        return unitLabelFr;
    }

    public void setUnitLabelFr(String unitLabelFr) {
        this.unitLabelFr = unitLabelFr;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }
}
