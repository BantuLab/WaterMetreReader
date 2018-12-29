package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities;
import com.bantulogic.core.watermetrereader.Utilities.Converters;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class MetreAccount {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "metre_id")
    private long mMetreId;
    @ColumnInfo(name = "customer_id")
    private String mCustomerId;
    @ColumnInfo(name = "street_address")
    private String mMetreStreetAddress;
    @ColumnInfo(name = "city")
    private String mCity;
    @ColumnInfo(name = "province")
    private String mProvince;
    @ColumnInfo(name = "country")
    private String mCountry;
    @ColumnInfo(name = "gps_coordinates")
    private String mGpsCoordinates;
    @ColumnInfo(name = "gps_latitude")
    private double mGpsLatitude;
    @ColumnInfo(name = "gps_longitude")
    private double mGpsLongitude;
    @ColumnInfo(name = "assigned_user_id")
    private String mAssignedUserId;

    public MetreAccount(
            @NonNull long metreId,
                String customerId,
                String metreStreetAddress,
                String city,
                String province,
                String country,
                String gpsCoordinates,
                double gpsLatitude,
                double gpsLongitude,
                String assignedUserId
    ){
        this.mMetreId = metreId;
        this.mCustomerId = customerId;
        this.mMetreStreetAddress = metreStreetAddress;
        this.mCity = city;
        this.mProvince = province;
        this.mCountry = country;
        this.mGpsCoordinates = gpsCoordinates;
        this.mGpsLatitude = gpsLatitude;
        this.mGpsLongitude = gpsLongitude;
        this.mAssignedUserId = assignedUserId;
    }

    public long getMetreId() {
        return mMetreId;
    }

    public void setMetreId(long metreId) {
        mMetreId = metreId;
    }

    public String getCustomerId() {
        return mCustomerId;
    }

    public void setCustomerId(String customerId) {
        mCustomerId = customerId;
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

    public String getGpsCoordinates() {
        return mGpsCoordinates;
    }

    public void setGpsCoordinates(String gpsCoordinates) {
        mGpsCoordinates = gpsCoordinates;
    }

    public double getGpsLatitude() {
        return mGpsLatitude;
    }

    public void setGpsLatitude(double gpsLatitude) {
        mGpsLatitude = gpsLatitude;
    }

    public double getGpsLongitude() {
        return mGpsLongitude;
    }

    public void setGpsLongitude(double gpsLongitude) {
        mGpsLongitude = gpsLongitude;
    }

    public String getAssignedUserId() {
        return mAssignedUserId;
    }

    public void setAssignedUserId(String assignedUserId) {
        mAssignedUserId = assignedUserId;
    }
}
