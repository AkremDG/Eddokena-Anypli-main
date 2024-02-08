package com.digid.eddokenaCM.Models;

public class Statut {
    private String text;
    private int color;

    public Statut(String text, Integer color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
