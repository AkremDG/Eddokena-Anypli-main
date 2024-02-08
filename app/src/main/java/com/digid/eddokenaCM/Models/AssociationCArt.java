package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "AssociationCArt")
public class AssociationCArt {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "Ct_num")
    @SerializedName("Ct_num")
    private String ctNum;

    @ColumnInfo(name = "Ar_ref")
    @SerializedName("Ar_ref")
    private String arRef;

    public AssociationCArt(String ctNum, String arRef) {
        this.ctNum = ctNum;
        this.arRef = arRef;
    }

    public AssociationCArt() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCtNum() {
        return ctNum;
    }

    public void setCtNum(String ctNum) {
        this.ctNum = ctNum;
    }

    public String getArRef() {
        return arRef;
    }

    public void setArRef(String arRef) {
        this.arRef = arRef;
    }
}
