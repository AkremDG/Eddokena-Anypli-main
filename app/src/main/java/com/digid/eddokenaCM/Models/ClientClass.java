package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

public class ClientClass {
    @SerializedName("id")
    private Long id;

    @SerializedName("nameFr")
    private String nameFr;


    public ClientClass() {
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
}
