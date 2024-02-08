package com.digid.eddokenaCM.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponseUser {
    @SerializedName("email")
    private String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("sageCode")
    private String sageCode;

    @SerializedName("status")
    private String status;

    @SerializedName("profileId")
    private Long profileId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSageCode() {
        return sageCode;
    }

    public void setSageCode(String sageCode) {
        this.sageCode = sageCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
