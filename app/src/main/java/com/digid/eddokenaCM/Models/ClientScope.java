package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ClientScope")
public class ClientScope {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="id")
    private Long id;

    @ColumnInfo(name="warehouseId")
    @SerializedName("warehouseId")
    private int warehouseId;

    @ColumnInfo(name="clientId")
    @SerializedName("clientId")
    private String clientId;

    @ColumnInfo(name="catalogId")
    @SerializedName("catalogId")
    private String catalogId;

    public ClientScope(String clientId, int warehouseId, String catalogId) {
        this.clientId = clientId;
        this.warehouseId = warehouseId;
        this.catalogId = catalogId;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }


    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
}
