package com.digid.eddokenaCM.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "DealTarget")
public class DealTarget {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("localId")
    @ColumnInfo(name = "localId")
    private Integer localId;

    @Ignore
    @SerializedName("zone")
    private Zone zone;

    @Ignore
    @SerializedName("clients")
    private List<Clients> dealTargetsClients;
    @Ignore
    @SerializedName("clientClasses")
    private List<ClientClasses> clientClasses;

    @Ignore
    @SerializedName("clientCategories")
    private List<ClientCategories> clientCategories;
    @ColumnInfo(name = "articleId_foreign")
    private long articleIdForeign;
    @ColumnInfo(name = "zoneId")
    private Integer zoneId;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private Integer id;



    public DealTarget(Long articleIdForeign, Integer zoneId, Integer id) {
        this.zone = zone;
        this.articleIdForeign = articleIdForeign;
        this.zoneId = zoneId;
        this.id = id;

    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public List<Clients> getDealTargetsClients() {
        return dealTargetsClients;
    }

    public void setDealTargetsClients(List<Clients> dealTargetsClients) {
        this.dealTargetsClients = dealTargetsClients;
    }

    public List<ClientClasses> getClientClasses() {
        return clientClasses;
    }

    public void setClientClasses(List<ClientClasses> clientClasses) {
        this.clientClasses = clientClasses;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public List<ClientCategories> getClientCategories() {
        return clientCategories;
    }

    public void setClientCategories(List<ClientCategories> clientCategories) {
        this.clientCategories = clientCategories;
    }

    public long getArticleIdForeign() {
        return articleIdForeign;
    }

    public void setArticleIdForeign(long articleIdForeign) {
        this.articleIdForeign = articleIdForeign;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DealTarget{" +
                "localId=" + localId +
                ", zone=" + zone +
                ", dealTargetsClients=" + dealTargetsClients +
                ", clientClasses=" + clientClasses +
                ", clientCategories=" + clientCategories +
                ", articleIdForeign=" + articleIdForeign +
                ", zoneId=" + zoneId +
                ", id=" + id +
                '}';
    }
}
