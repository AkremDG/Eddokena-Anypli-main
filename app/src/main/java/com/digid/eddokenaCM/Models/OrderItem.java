package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "OrderItem")
public class OrderItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "isFirstOrderRelated")
    private boolean isFirstOrderRelated;

    @ColumnInfo(name = "idCmdLocal")
    private Long idCmdLocal;

    @ColumnInfo(name = "idBo")
    private long idBo;
    @ColumnInfo(name = "ref")

    private String ref;

    // DocLigne local id
    @ColumnInfo(name = "articleId")
    @SerializedName("articleId")
    private Integer articleId;

    @ColumnInfo(name = "articleName")
    private String articleName;

    // Article reference code
    // correction : quantities is Listof orderItemQte not OrderItemQte Object
    @Ignore
    @SerializedName("quantities")
    private List<OrderItemQte> quantities;

    @ColumnInfo(name = "packingType")
    private String packingType;

    @ColumnInfo(name = "qty")
    private double qty;

    // Article reference code
    @Ignore
    @SerializedName("total")
    private OrderItemTotal total;

    @ColumnInfo(name = "totalAmount")
    private double totalAmount;

    @Ignore
    private Article article;

    @Ignore
    private Client client;

    public OrderItem() {
    }

    public OrderItem(Long idCmdLocal, Integer articleId, String packingType, double qty, double totalAmount) {
        this.idCmdLocal = idCmdLocal;
        this.articleId = articleId;
        this.packingType = packingType;
        this.qty = qty;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getIdCmdLocal() {
        return idCmdLocal;
    }

    public void setIdCmdLocal(Long idCmdLocal) {
        this.idCmdLocal = idCmdLocal;
    }

    public long getIdBo() {
        return idBo;
    }

    public void setIdBo(long idBo) {
        this.idBo = idBo;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public List<OrderItemQte> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<OrderItemQte> quantities) {
        this.quantities = quantities;
    }

    public String getPackingType() {
        return packingType;
    }

    public void setPackingType(String packingType) {
        this.packingType = packingType;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public OrderItemTotal getTotal() {
        return total;
    }

    public void setTotal(OrderItemTotal total) {
        this.total = total;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public boolean isFirstOrderRelated() {
        return isFirstOrderRelated;
    }

    public void setFirstOrderRelated(boolean firstOrderRelated) {
        isFirstOrderRelated = firstOrderRelated;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", isFirstOrderRelated=" + isFirstOrderRelated +
                ", idCmdLocal=" + idCmdLocal +
                ", idBo=" + idBo +
                ", ref='" + ref + '\'' +
                ", articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", quantities=" + quantities +
                ", packingType='" + packingType + '\'' +
                ", qty=" + qty +
                ", total=" + total +
                ", totalAmount=" + totalAmount +
                ", article=" + article +
                ", client=" + client +
                '}';
    }
}
