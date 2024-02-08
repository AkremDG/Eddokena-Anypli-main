package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

public class User {

    // User ID
    @SerializedName("id")
    private int id;

    // User attached commercial code
    @SerializedName("CO_NO")
    private long coNo;

    // User profile
    @SerializedName("PROFIL")
    private int profil;

    // User Last name
    @SerializedName("Nom")
    private String nom;

    // User name
    @SerializedName("Prenom")
    private String prenom;

    // User reference code
    @SerializedName("CT_Num")
    private String tNum;

    // User tariff category
    @SerializedName("N_Cattarif")
    private int clientCat;

    // User BD
    @SerializedName("bd")
    private String bd;


    public User(long coNo, int profil, String nom, String prenom, String tNum, int clientCat, String bd) {
        this.coNo = coNo;
        this.profil = profil;
        this.nom = nom;
        this.prenom = prenom;
        this.tNum = tNum;
        this.clientCat = clientCat;
        this.bd = bd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCoNo() {
        return coNo;
    }

    public void setCoNo(long coNo) {
        this.coNo = coNo;
    }

    public int getProfil() {
        return profil;
    }

    public void setProfil(int profil) {
        this.profil = profil;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String gettNum() {
        return tNum;
    }

    public void settNum(String tNum) {
        this.tNum = tNum;
    }

    public int getClientCat() {
        return clientCat;
    }

    public void setClientCat(int clientCat) {
        this.clientCat = clientCat;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }
}
