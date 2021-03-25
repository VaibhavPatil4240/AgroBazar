
package com.vaibhav.agrobazarv1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customerID")
    @Expose
    private String customerID;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("LandMark")
    @Expose
    private String landMark;
    @SerializedName("pincode")
    @Expose
    private String pincode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}