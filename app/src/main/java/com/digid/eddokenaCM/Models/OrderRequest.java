package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderRequest {

    @SerializedName("orders")
    private List<OrderRequestObject> orderRequestObjects = new ArrayList<>();

    public OrderRequest(OrderRequestObject orderRequestObject) {
        this.orderRequestObjects.add(orderRequestObject);
    }

    public List<OrderRequestObject> getOrderRequestObjects() {
        return orderRequestObjects;
    }

    public void setOrderRequestObjects(List<OrderRequestObject> orderRequestObjects) {
        this.orderRequestObjects = orderRequestObjects;
    }
}