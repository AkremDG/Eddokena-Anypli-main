package com.digid.eddokenaCM.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class ClientCategories {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("localId")
    @ColumnInfo(name = "localId")
    private Integer localId;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "dealTargetId")
    private Integer dealTargetId_foreign;

    @ColumnInfo(name = "discountsTargetId_Foreign")
    private Integer discountsTargetId_Foreign;
    @ColumnInfo(name = "nameFr")
    @SerializedName("nameFr")
    private String nameFr;

    @ColumnInfo(name = "nameAr")
    @SerializedName("nameAr")
    private String nameAr;

    public ClientCategories(Integer id, Integer dealTargetId_foreign, Integer discountsTargetId_Foreign) {
        this.id = id;
        this.dealTargetId_foreign = dealTargetId_foreign;
        this.discountsTargetId_Foreign = discountsTargetId_Foreign;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
    }

    public Integer getDiscountsTargetId_Foreign() {
        return discountsTargetId_Foreign;
    }

    public void setDiscountsTargetId_Foreign(Integer discountsTargetId_Foreign) {
        this.discountsTargetId_Foreign = discountsTargetId_Foreign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDealTargetId_foreign() {
        return dealTargetId_foreign;
    }

    public void setDealTargetId_foreign(Integer dealTargetId_foreign) {
        this.dealTargetId_foreign = dealTargetId_foreign;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    @Override
    public String toString() {
        return "ClientCategories{" +
                "localId=" + localId +
                ", id=" + id +
                ", dealTargetId_foreign=" + dealTargetId_foreign +
                ", discountsTargetId_Foreign=" + discountsTargetId_Foreign +
                ", nameFr='" + nameFr + '\'' +
                ", nameAr='" + nameAr + '\'' +
                '}';
    }
}
