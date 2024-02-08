package com.digid.eddokenaCM.Models;

public class CMDItem {

    private String selectedCondition;
    private Double selectedConditionQte;
    private double selectedConditionPrice;
    private double selectedPrice;
    private double selectedQte;
    private double selectedTotalPrice;
    private double selectedTotalQte;
    private double maxQte;
    private double minQte;
    private double discountPercentage;

    public CMDItem() {
    }

    public String getSelectedCondition() {
        return selectedCondition;
    }

    public void setSelectedCondition(String selectedCondition) {
        this.selectedCondition = selectedCondition;
    }

    public Double getSelectedConditionQte() {
        return selectedConditionQte;
    }

    public void setSelectedConditionQte(double selectedConditionQte) {
        this.selectedConditionQte = selectedConditionQte;
    }

    public double getSelectedConditionPrice() {
        return selectedConditionPrice;
    }

    public void setSelectedConditionPrice(double selectedConditionPrice) {
        this.selectedConditionPrice = selectedConditionPrice;
    }

    public double getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(double selectedPrice) {
        this.selectedPrice = selectedPrice;
    }

    public double getSelectedQte() {
        return selectedQte;
    }

    public void setSelectedQte(double selectedQte) {
        this.selectedQte = selectedQte;
    }

    public double getSelectedTotalPrice() {
        return selectedTotalPrice;
    }

    public void setSelectedTotalPrice(double selectedTotalPrice) {
        this.selectedTotalPrice = selectedTotalPrice;
    }

    public double getSelectedTotalQte() {
        return selectedTotalQte;
    }

    public void setSelectedTotalQte(double selectedTotalQte) {
        this.selectedTotalQte = selectedTotalQte;
    }

    public double getMaxQte() {
        return maxQte;
    }

    public void setMaxQte(double maxQte) {
        this.maxQte = maxQte;
    }

    public double getMinQte() {
        return minQte;
    }

    public void setMinQte(double minQte) {
        this.minQte = minQte;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "CMDItem{" +
                "selectedCondition='" + selectedCondition + '\'' +
                ", selectedConditionQte=" + selectedConditionQte +
                ", selectedConditionPrice=" + selectedConditionPrice +
                ", selectedPrice=" + selectedPrice +
                ", selectedQte=" + selectedQte +
                ", selectedTotalPrice=" + selectedTotalPrice +
                ", selectedTotalQte=" + selectedTotalQte +
                ", maxQte=" + maxQte +
                ", minQte=" + minQte +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}

