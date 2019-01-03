package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities;
import com.bantulogic.core.watermetrereader.Helpers.Converters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(
        indices =
                {
                        @Index(
                                value = "customer_id",
                                unique = true
                        ),
                        @Index(
                                value = "assigned_user_id",
                                unique = true
                        )
                },
        foreignKeys =
                {
                        @ForeignKey(
                                entity = Customer.class,
                                parentColumns = "id",
                                childColumns = "customer_id",
                                onDelete = CASCADE,
                                onUpdate = CASCADE
                        ),
                        @ForeignKey(
                                entity = User.class,
                                parentColumns = "user_id",
                                childColumns = "assigned_user_id",
                                onDelete = SET_NULL,
                                onUpdate = CASCADE
                        )
                }


)
@TypeConverters(Converters.class)
public class MetreAccount {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "metre_id")
    @SerializedName("metreId")
    @Expose
    private long mMetreId;
    @ColumnInfo(name = "customer_id")
    @SerializedName("customerId")
    @Expose
    private String mCustomerId;
    @SerializedName("metreStreetAddress")
    @Expose
    @ColumnInfo(name = "street_address")
    private String mMetreStreetAddress;
    @SerializedName("city")
    @Expose
    @ColumnInfo(name = "city")
    private String mCity;
    @SerializedName("province")
    @Expose
    @ColumnInfo(name = "province")
    private String mProvince;
    @SerializedName("country")
    @Expose
    @ColumnInfo(name = "country")
    private String mCountry;
    @SerializedName("gpsCoordinates")
    @Expose
    @ColumnInfo(name = "gps_coordinates")
    private String mGpsCoordinates;
    @SerializedName("gpsLatitude")
    @Expose
    @ColumnInfo(name = "gps_latitude")
    private double mGpsLatitude;
    @SerializedName("gpsLongitude")
    @Expose
    @ColumnInfo(name = "gps_longitude")
    private double mGpsLongitude;
    @SerializedName("assignedUserId")
    @Expose
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
//region GETTERS AND SETTERS
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
//endregion
}
