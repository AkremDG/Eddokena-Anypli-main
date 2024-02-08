package com.digid.eddokenaCM.Models;

import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;


public class ArticlePcs {


    @SerializedName("unit")
    private ArticlePcsMesure unit;

    @SerializedName("display")
    private ArticlePcsMesure display;

    @Ignore
    @SerializedName("carton")
    private ArticlePcsMesure carton;

    @Ignore
    @SerializedName("pallet")
    private ArticlePcsMesure pallet;

    public ArticlePcs(ArticlePcsMesure unit, ArticlePcsMesure display, ArticlePcsMesure carton, ArticlePcsMesure pallet) {
        this.unit = unit;
        this.display = display;
        this.carton = carton;
        this.pallet = pallet;
    }

    public ArticlePcs() {
    }

    public ArticlePcsMesure getUnit() {
        return unit;
    }

    public void setUnit(ArticlePcsMesure unit) {
        this.unit = unit;
    }

    public ArticlePcsMesure getDisplay() {
        return display;
    }

    public void setDisplay(ArticlePcsMesure display) {
        this.display = display;
    }

    public ArticlePcsMesure getPalletgetCarton() {
        return carton;
    }

    public ArticlePcsMesure getCarton() {
        return carton;
    }

    public void setCarton(ArticlePcsMesure carton) {
        this.carton = carton;
    }

    public ArticlePcsMesure getPallet() {
        return pallet;
    }

    public void setPallet(ArticlePcsMesure pallet) {
        this.pallet = pallet;
    }

    @Override
    public String toString() {
        return "ArticlePcs{" +
                "unit=" + unit +
                ", display=" + display +
                ", carton=" + carton +
                ", pallet=" + pallet +
                '}';
    }
}
