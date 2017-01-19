package com.oleg.hubal.bankconverter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by User on 19.01.2017.
 */

@Table(database = FlowDatabase.class)
public class Organization extends BaseModel {

    @Column
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private String id;
    @Column
    @SerializedName("title")
    @Expose
    private String title;
    @Column
    @SerializedName("regionId")
    @Expose
    private String regionId;
    @Column
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @Column
    @SerializedName("phone")
    @Expose
    private String phone;
    @Column
    @SerializedName("address")
    @Expose
    private String address;
    @Column
    @SerializedName("link")
    @Expose
    private String link;

    public Organization() {
    }

    public Organization(String id, String title, String regionId, String cityId, String phone, String address, String link) {
        this.id = id;
        this.title = title;
        this.regionId = regionId;
        this.cityId = cityId;
        this.phone = phone;
        this.address = address;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
