package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities;

import com.bantulogic.core.watermetrereader.helpers.Converters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class FcmToken {
    @SerializedName("_id")
    @Expose
    @NonNull
    @ColumnInfo(name = "_id")
    private String mId;

    @SerializedName("token")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "token")
    private String mToken;

    @SerializedName("user_id")
    @Expose
    @NonNull
    @ColumnInfo(name = "user_id")
    private String mUserId;

    @SerializedName("createdAt")
    @Expose
    @NonNull
    @ColumnInfo(name = "createdAt")
    private Date mCreatedAt;

    @SerializedName("updatedAt")
    @Expose
    @NonNull
    @ColumnInfo(name = "updatedAt")
    private Date mUpdatedAt;

    public FcmToken(String _id,
                    String token,
                    String user_id,
                    Date createdAt,
                    Date updatedAt)
    {
        this.mId = _id;
        this.mUserId = user_id;
        this.mToken = token;
        this.mCreatedAt = createdAt;
        this.mUpdatedAt = updatedAt;

    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    @NonNull
    public String getToken() {
        return mToken;
    }

    public void setToken(@NonNull String token) {
        mToken = token;
    }

    @NonNull
    public String getUserId() {
        return mUserId;
    }

    public void setUserId(@NonNull String userId) {
        mUserId = userId;
    }

    @NonNull
    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(@NonNull Date createdAt) {
        mCreatedAt = createdAt;
    }

    @NonNull
    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(@NonNull Date updatedAt) {
        mUpdatedAt = updatedAt;
    }
}
