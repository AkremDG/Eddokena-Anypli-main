package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class ClientCat {
    @SerializedName("id")
    private Long id;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("deletedAt")
    private String deletedAt;

    @SerializedName("nameAr")
    private String nameAr;

    @SerializedName("nameFr")
    private String nameFr;

    @SerializedName("sageId")
    private String sageId;

    @SerializedName("status")
    private String status;

    @SerializedName("minimumOrderPrice")
    private Float minimumOrderPrice;


    public ClientCat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getSageId() {
        return sageId;
    }

    public void setSageId(String sageId) {
        this.sageId = sageId;
    }
}
