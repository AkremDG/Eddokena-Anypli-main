package com.digid.eddokenaCM.Models;


import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequestObject {

    @ColumnInfo(name = "originOrderId")
    @SerializedName("originOrderId")
    private long originOrderId;

    @SerializedName("orderId")
    private Long orderId;

    @SerializedName("items")
    private List<OrderRequestObjectItem> items;

    @SerializedName("orderDate")
    private String date;

    @SerializedName("ref")
    private String ref;

    @SerializedName("clientId")
    private Long clientId;

    @SerializedName("shopId")
    private long shopId;

    @SerializedName("note")
    private String note;

    // DocEntete reference code
    @SerializedName("expectedTotalAmount")
    private Double expectedTotalAmount;

    // DocEntete creation date
    @SerializedName("status")
    private String status;

    // DocEntete creation date
    @SerializedName("extraData")
    private OrderRequestObjectExtra extra;



    public OrderRequestObject( String date, Long orderId, Long clientId, long shopId, String note, Double expectedTotalAmount, String status) {
        this.orderId = orderId;
        this.date = date;
        this.clientId = clientId;
        this.shopId = shopId;
        this.note = note;
        this.expectedTotalAmount = expectedTotalAmount;
        this.status = status;
    }

    public long getOriginOrderId() {
        return originOrderId;
    }

    public void setOriginOrderId(long originOrderId) {
        this.originOrderId = originOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getExpectedTotalAmount() {
        return expectedTotalAmount;
    }

    public void setExpectedTotalAmount(Double expectedTotalAmount) {
        this.expectedTotalAmount = expectedTotalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public OrderRequestObjectExtra getExtra() {
        return extra;
    }

    public void setExtra(OrderRequestObjectExtra extra) {
        this.extra = extra;
    }

    public List<OrderRequestObjectItem> getItems() {
        return items;
    }

    public void setItems(List<OrderRequestObjectItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderRequestObject{" +
                "originOrderId=" + originOrderId +
                ", orderId=" + orderId +
                ", items=" + items +
                ", date='" + date + '\'' +
                ", ref='" + ref + '\'' +
                ", clientId=" + clientId +
                ", shopId=" + shopId +
                ", note='" + note + '\'' +
                ", expectedTotalAmount=" + expectedTotalAmount +
                ", status='" + status + '\'' +
                ", extra=" + extra +
                '}';
    }
}
