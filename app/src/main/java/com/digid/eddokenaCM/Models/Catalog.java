package com.digid.eddokenaCM.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "Catalog")
public class Catalog {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private Long id;

    @ColumnInfo(name = "idParent")
    private Long idParent;

    @ColumnInfo(name = "idArticle")
    private Long idArticle;

    @ColumnInfo(name = "nameFr")
    @SerializedName("nameFr")
    private String nameFr;

    @ColumnInfo(name = "level")
    @SerializedName("level")
    private Integer level;

    @Ignore
    @ColumnInfo(name = "children")
    @SerializedName("children")
    private List<Catalog> children;

    public Catalog(@NonNull Long id, Long idArticle, String nameFr, Integer level, List<Catalog> children) {
        this.id = id;
        this.idArticle = idArticle;
        this.nameFr = nameFr;
        this.level = level;
        this.children = children;
    }

    public Catalog() {
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public Long getIdParent() {
        return idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
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

    public List<Catalog> getChildren() {
        return children;
    }

    public void setChildren(List<Catalog> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", idParent=" + idParent +
                ", idArticle=" + idArticle +
                ", nameFr='" + nameFr + '\'' +
                ", level=" + level +
                ", children=" + children +
                '}';
    }
}
