package com.digid.eddokenaCM.Models;


public class Zone {

    private Integer id;
    private String createdAt;
    private String updatedAt;

    private String nameFr;
    private String nameAr;

    private String status;
    private String deletedAt;


    public Zone(Integer id, String createdAt, String updatedAt, String nameFr, String nameAr, String status, String deletedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", nameFr='" + nameFr + '\'' +
                ", nameAr='" + nameAr + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}
