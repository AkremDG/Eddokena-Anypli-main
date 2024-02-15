package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "OrderChild")
public class OrderChild {

    // correction : Duplicated id declaration : JSON ERROR (RETROFIT)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idOrder")
    private long idOrder;

    @ColumnInfo(name = "originOrderId")
    private long originOrderId;

    @ColumnInfo(name = "idBo")
    @SerializedName("id")
    private Integer idBo;


    @ColumnInfo(name = "local")
    private Boolean local;
    @ColumnInfo(name = "coNo")
    private long coNo;

    @ColumnInfo(name = "db")
    private String db;

    // DocEntete reference code
    @ColumnInfo(name = "ref")
    @SerializedName("ref")
    private String ref;

    // DocEntete creation date
    @ColumnInfo(name = "orderDate")
    @SerializedName("orderDate")
    private String doDate;

    // Client reference code (CT_Num)
    // correction :client id is an integer not long
    @ColumnInfo(name = "clientId")
    @SerializedName("clientId")
    private Long clientId;

    @Ignore
    private Client client;

    // DocEntete comment
    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status;

    // DocEntete total price amount
    @ColumnInfo(name = "note")
    @SerializedName("note")
    private String note;

    @Ignore
    @SerializedName("total")
    private OrderTotal total;

    @ColumnInfo(name ="discountAmount")
    private float discountAmount;

    // DocEntete reference code
    @ColumnInfo(name ="totalAmount")
    private double totalAmount;

    // DocEntete list of articles
    // correction : items is list of OrderItem not OrderItem Object
    @Ignore
    @SerializedName("items")
    List<OrderItem> ligneList = new ArrayList<>();

    public OrderChild() {
    }

    public OrderChild(Integer idBo, long coNo, String db, String ref, String doDate, Long clientId,
                 String status, String note, boolean local, long originOrderId) {
        this.idBo = idBo;
        this.coNo = coNo;
        this.db = db;
        this.ref = ref;
        this.doDate = doDate;
        this.clientId = clientId;
        this.status = status;
        this.note = note;
        this.local = local;
        this.originOrderId=originOrderId;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getOriginOrderId() {
        return originOrderId;
    }

    public void setOriginOrderId(long originOrderId) {
        this.originOrderId = originOrderId;
    }

    public Integer getIdBo() {
        return idBo;
    }

    public void setIdBo(Integer idBo) {
        this.idBo = idBo;
    }

    public Boolean getLocal() {
        return local;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public long getCoNo() {
        return coNo;
    }

    public void setCoNo(long coNo) {
        this.coNo = coNo;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDoDate() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public OrderTotal getTotal() {
        return total;
    }

    public void setTotal(OrderTotal total) {
        this.total = total;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getLigneList() {
        return ligneList;
    }

    public void setLigneList(List<OrderItem> ligneList) {
        this.ligneList = ligneList;
    }

    @Override
    public String toString() {
        return "OrderChild{" +
                "idOrder=" + idOrder +
                ", originOrderId=" + originOrderId +
                ", idBo=" + idBo +
                ", local=" + local +
                ", coNo=" + coNo +
                ", db='" + db + '\'' +
                ", ref='" + ref + '\'' +
                ", doDate='" + doDate + '\'' +
                ", clientId=" + clientId +
                ", client=" + client +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", total=" + total +
                ", discountAmount=" + discountAmount +
                ", totalAmount=" + totalAmount +
                ", ligneList=" + ligneList +
                '}';
    }
}
