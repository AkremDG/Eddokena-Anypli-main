package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Client")
public class Client {

    // Client reference code
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;

    // Client name
    @ColumnInfo(name = "firstName")
    @SerializedName("firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    @SerializedName("lastName")
    private String lastName;

    @ColumnInfo(name = "email")
    @SerializedName("email")
    private String email;

    // Client phone number
    @ColumnInfo(name = "phoneNumber")
    @SerializedName("phoneNumber")
    private String phoneNumber;


    @ColumnInfo(name = "clientRisk")
    @SerializedName("clientRisk")
    private String clientRisk;


    @ColumnInfo(name = "clientStatus")
    @SerializedName("clientStatus")
    private String clientStatus;


    @ColumnInfo(name = "paymentMethod")
    @SerializedName("paymentMethod")
    private String paymentMethod;

    @ColumnInfo(name = "budgetLimit")
    @SerializedName("budgetLimit")
    private String budgetLimit;


    @ColumnInfo(name = "active")
    @SerializedName("active")
    private String active;
    // Client phone number
    @ColumnInfo(name = "sageCode")
    @SerializedName("sageCode")
    private String sageCode;

    @ColumnInfo(name = "Shopid")
    private Long shopId;

    @ColumnInfo(name = "ShopName")
    private String shopName;

    @ColumnInfo(name = "ShopLat")
    private String shopLat;

    @ColumnInfo(name = "ShopLon")
    private String shopLon;

    @ColumnInfo(name = "CategoryName")
    private String categoryName;

    @ColumnInfo(name = "Class")
    private String cltClass;

    // Client phone number
    @Ignore
    @SerializedName("defaultShop")
    private ClientShop defaultShop;

    @Ignore
    @SerializedName("clientCategory")
    private ClientCat clientCat;

    @Ignore
    @SerializedName("clientClass")
    private ClientClass clientClass;

    @Ignore
    @SerializedName("clientScopes")
    private List<ClientScope> clientScopes;

    @ColumnInfo(name = "CategoryId")
    @SerializedName( "categoryId")
    private long categoryId;

    @ColumnInfo(name = "classId")
    @SerializedName("classId")
    private Integer classId;

    @ColumnInfo(name = "zoneId")
    @SerializedName("zoneId")
    private Integer zoneId;

    public Client() {
    }

    public Client(@NonNull Long id, String firstName, String lastName, String email, String phoneNumber, String sageCode,
                  ClientShop defaultShop, ClientCat clientCat, ClientClass clientClass, Integer classId,Integer zoneId,long categoryId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sageCode = sageCode;
        this.defaultShop = defaultShop;
        this.clientCat = clientCat;
        this.clientClass = clientClass;


        this.classId=classId;
        this.zoneId=zoneId;
        this.categoryId=categoryId;
    }

    public String getClientRisk() {
        return clientRisk;
    }

    public void setClientRisk(String clientRisk) {
        this.clientRisk = clientRisk;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBudgetLimit() {
        return budgetLimit;
    }

    public void setBudgetLimit(String budgetLimit) {
        this.budgetLimit = budgetLimit;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSageCode() {
        return sageCode;
    }

    public void setSageCode(String sageCode) {
        this.sageCode = sageCode;
    }

    public ClientShop getDefaultShop() {
        return defaultShop;
    }

    public void setDefaultShop(ClientShop defaultShop) {
        this.defaultShop = defaultShop;
    }

    public ClientCat getClientCat() {
        return clientCat;
    }

    public void setClientCat(ClientCat clientCat) {
        this.clientCat = clientCat;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getShopLon() {
        return shopLon;
    }

    public void setShopLon(String shopLon) {
        this.shopLon = shopLon;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCltClass() {
        return cltClass;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public void setCltClass(String cltClass) {
        this.cltClass = cltClass;
    }

    public ClientClass getClientClass() {
        return clientClass;
    }

    public void setClientClass(ClientClass clientClass) {
        this.clientClass = clientClass;
    }

    public List<ClientScope> getClientScopes() {
        return clientScopes;
    }

    public void setClientScopes(List<ClientScope> clientScopes) {
        this.clientScopes = clientScopes;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sageCode='" + sageCode + '\'' +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopLat='" + shopLat + '\'' +
                ", shopLon='" + shopLon + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", cltClass='" + cltClass + '\'' +
                ", defaultShop=" + defaultShop +
                ", clientCat=" + clientCat +
                ", clientClass=" + clientClass +
                ", clientScopes=" + clientScopes +
                ", classId=" + classId +
                ", zoneId=" + zoneId +
                '}';
    }
}
