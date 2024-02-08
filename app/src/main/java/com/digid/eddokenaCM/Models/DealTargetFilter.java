package com.digid.eddokenaCM.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DealTargetFilter {
    private Long clientId;
    private Long categorieId;
    private Integer zoneId;
    private Integer classId;

    public DealTargetFilter(Long clientId, Long categorieId, Integer zoneId, Integer classId) {
        this.clientId = clientId;
        this.categorieId = categorieId;
        this.zoneId = zoneId;
        this.classId = classId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
