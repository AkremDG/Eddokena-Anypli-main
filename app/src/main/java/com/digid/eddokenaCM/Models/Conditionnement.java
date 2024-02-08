package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Conditionnement")
public class Conditionnement {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Row")
    @SerializedName("Row")
    private int id;

    // Article referenc code
    @ColumnInfo(name = "AR_Ref")
    @SerializedName("AR_Ref")
    private String AR_Ref;

    //Article name
    @ColumnInfo(name = "AR_Design")
    @SerializedName("AR_Design")
    private String AR_Design;

    // Article price
    @ColumnInfo(name = "prix_veNte")
    @SerializedName("prix_veNte")
    private float prix_veNte;

    // Article tariff category
    @ColumnInfo(name = "cat")
    @SerializedName("cat")
    private int cat;


    @ColumnInfo(name = "TC_RefCF")
    @SerializedName("TC_RefCF")
    private String TC_RefCF;

    // Nombre of pieces in the condition
    @ColumnInfo(name = "EC_Quantite")
    @SerializedName("EC_Quantite")
    private float EC_Quantite;

    // Article sell condition (Piece, Pack, ..)
    @ColumnInfo(name = "EC_Enumere")
    @SerializedName("EC_Enumere")
    private String EC_Enumere;


    public Conditionnement(int id, String AR_Ref, String AR_Design, float prix_veNte, int cat, String TC_RefCF, float EC_Quantite, String EC_Enumere) {
        this.id = id;
        this.AR_Ref = AR_Ref;
        this.AR_Design = AR_Design;
        this.prix_veNte = prix_veNte;
        this.cat = cat;
        this.TC_RefCF = TC_RefCF;
        this.EC_Quantite = EC_Quantite;
        this.EC_Enumere = EC_Enumere;
    }


    @Ignore
    public Conditionnement(String EC_Enumere) {
        this.EC_Enumere = EC_Enumere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAR_Ref() {
        return AR_Ref;
    }

    public void setAR_Ref(String AR_Ref) {
        this.AR_Ref = AR_Ref;
    }

    public String getAR_Design() {
        return AR_Design;
    }

    public void setAR_Design(String AR_Design) {
        this.AR_Design = AR_Design;
    }

    public float getPrix_veNte() {
        return prix_veNte;
    }

    public void setPrix_veNte(float prix_veNte) {
        this.prix_veNte = prix_veNte;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public String getTC_RefCF() {
        return TC_RefCF;
    }

    public void setTC_RefCF(String TC_RefCF) {
        this.TC_RefCF = TC_RefCF;
    }

    public float getEC_Quantite() {
        return EC_Quantite;
    }

    public void setEC_Quantite(float EC_Quantite) {
        this.EC_Quantite = EC_Quantite;
    }

    public String getEC_Enumere() {
        return EC_Enumere;
    }

    public void setEC_Enumere(String EC_Enumere) {
        this.EC_Enumere = EC_Enumere;
    }
}
