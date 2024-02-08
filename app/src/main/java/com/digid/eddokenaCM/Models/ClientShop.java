package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class ClientShop {

    @SerializedName("id")
    private Long id;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    // Client name
    @SerializedName("name")
    private String name;

    @SerializedName("companyName")
    private String companyName;

    // Client name
    @SerializedName("taxRegistrationNumber")
    private String taxRegistrationNumber;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String logitude;

    public ClientShop(Long id, String createdAt, String updatedAt, String name, String companyName, String taxRegistrationNumber, String latitude, String logitude) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.companyName = companyName;
        this.taxRegistrationNumber = taxRegistrationNumber;
        this.latitude = latitude;
        this.logitude = logitude;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxRegistrationNumber() {
        return taxRegistrationNumber;
    }

    public void setTaxRegistrationNumber(String taxRegistrationNumber) {
        this.taxRegistrationNumber = taxRegistrationNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }
}
