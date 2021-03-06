package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities;

import com.bantulogic.core.watermetrereader.helpers.Converters;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class Customer {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    private String mCustomerId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "t_pin")
    private String mTaxPin;
    @ColumnInfo(name = "street_address")
    private String mMetreStreetAddress;
    @ColumnInfo(name = "city")
    private String mCity;
    @ColumnInfo(name = "province")
    private String mProvince;
    @ColumnInfo(name = "country")
    private String mCountry;

    public Customer(
            String customerId,
            String name,
            String taxPin,
            String metreStreetAddress,
            String city,
            String province,
            String country
    ){
        this.mCustomerId =customerId;
        this.mName = name;
        this.mTaxPin = taxPin;
        this.mMetreStreetAddress =metreStreetAddress;
        this.mCity = city;
        this.mProvince = province;
        this.mCountry = country;
    }
//region GETTERS AND SETTERS
    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getTaxPin() {
        return mTaxPin;
    }

    public void setTaxPin(String tpin) {
        mTaxPin = tpin;
    }

    public String getMetreStreetAddress() {
        return mMetreStreetAddress;
    }

    public void setMetreStreetAddress(String metreStreetAddress) {
        mMetreStreetAddress = metreStreetAddress;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String province) {
        mProvince = province;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }
    //endregion


}
