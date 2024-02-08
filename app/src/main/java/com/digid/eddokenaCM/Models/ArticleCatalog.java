package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ArticleCatalog")
public class ArticleCatalog {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "idLocal")
    private Long idLocal;

    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long idBo;

    @ColumnInfo(name = "idArticle")
    private Long idArticle;

    @ColumnInfo(name = "nameFr")
    @SerializedName("nameFr")
    private String nameFr;

    @ColumnInfo(name = "level")
    @SerializedName("level")
    private Integer level;

    @ColumnInfo(name = "status")
    @SerializedName("status")
    private String status;

    public ArticleCatalog(Long idBo, Long idArticle, String nameFr, Integer level, String status) {
        this.idBo = idBo;
        this.idArticle = idArticle;
        this.nameFr = nameFr;
        this.level = level;
        this.status = status;
    }

    public ArticleCatalog() {
    }

    @NonNull
    public Long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(@NonNull Long idLocal) {
        this.idLocal = idLocal;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getIdBo() {
        return idBo;
    }

    public void setIdBo(Long idBo) {
        this.idBo = idBo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
