package com.digid.eddokenaCM.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Clients {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("localId")
    @ColumnInfo(name = "localId")
    private Integer localId;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;
    @ColumnInfo(name = "dealTargeId")
    private Integer DealTargetId_Foreign;

    @ColumnInfo(name = "discountsTargetId_Foreign")
    private Integer discountsTargetId_Foreign;

    public Clients(){

    }
    public Clients(Long id, Integer dealTargetId_Foreign,Integer discountsTargetId_Foreign) {
        this.id = id;
        DealTargetId_Foreign = dealTargetId_Foreign;
        this.discountsTargetId_Foreign = discountsTargetId_Foreign;
    }

    public Integer getDiscountsTargetId_Foreign() {
        return discountsTargetId_Foreign;
    }

    public void setDiscountsTargetId_Foreign(Integer discountsTargetId_Foreign) {
        this.discountsTargetId_Foreign = discountsTargetId_Foreign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDealTargetId_Foreign() {
        return DealTargetId_Foreign;
    }

    public void setDealTargetId_Foreign(Integer dealTargetId_Foreign) {
        DealTargetId_Foreign = dealTargetId_Foreign;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "localId=" + localId +
                ", id=" + id +
                ", DealTargetId_Foreign=" + DealTargetId_Foreign +
                ", discountsTargetId_Foreign=" + discountsTargetId_Foreign +
                '}';
    }
}
