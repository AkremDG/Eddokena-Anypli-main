package com.digid.eddokenaCM.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class ClientClasses {


    @PrimaryKey(autoGenerate = true)
    @SerializedName("localId")
    @ColumnInfo(name = "localId")
    private Integer localId;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "dealTargetId")
    private Integer dealTargetId_Foreign;

    @ColumnInfo(name = "discountsTargerId_Foreign")
    private Integer discountsTargerId_Foreign;
    public ClientClasses(Integer id, Integer dealTargetId_Foreign, Integer discountsTargerId_Foreign) {
        this.id = id;
        this.dealTargetId_Foreign = dealTargetId_Foreign;
        this.discountsTargerId_Foreign= discountsTargerId_Foreign;
    }

    public Integer getDiscountsTargerId_Foreign() {
        return discountsTargerId_Foreign;
    }

    public void setDiscountsTargerId_Foreign(Integer discountsTargerId_Foreign) {
        this.discountsTargerId_Foreign = discountsTargerId_Foreign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDealTargetId_Foreign() {
        return dealTargetId_Foreign;
    }

    public void setDealTargetId_Foreign(Integer dealTargetId_Foreign) {
        this.dealTargetId_Foreign = dealTargetId_Foreign;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    @Override
    public String toString() {
        return "ClientClasses{" +
                "localId=" + localId +
                ", id=" + id +
                ", dealTargetId_Foreign=" + dealTargetId_Foreign +
                ", discountsTargerId_Foreign=" + discountsTargerId_Foreign +
                '}';
    }
}
