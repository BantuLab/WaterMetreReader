package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities;
import com.bantulogic.core.watermetrereader.helpers.Converters;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    private String mUserId;
    @ColumnInfo(name = "username")
    private String mUsername;
    @ColumnInfo(name="first_name")
    private String mFirstName;
    @ColumnInfo(name="last_name" )
    private String mLastName;
    @ColumnInfo(name="sex")
    private String mSex;
    @ColumnInfo(name="date_of_birth")
    private Date mDateOfBirth;
    @ColumnInfo(name="user_type")
    private String mUserType;
    @ColumnInfo(name="user_role")
    private String mUserRole;
    @ColumnInfo(name = "last_update")
    private Date mLastUpdate;

    public User(@NonNull String userId,
                        String username,
                        String lastName,
                        String firstName,
                        String sex,
                        Date dateOfBirth,
                        String userType,
                        String userRole,
                        Date lastUpdate
    ){
        this.mUserId = userId;
        this.mUsername = username;
        this.mLastName = lastName;
        this.mFirstName = firstName;
        this.mSex = sex;
        this.mDateOfBirth = dateOfBirth;
        this.mUserType = userType;
        this.mUserRole = userRole;
        this.mLastUpdate = lastUpdate;
    }
//region GETTERS AND SETTERS
    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getSex() {
        return mSex;
    }

    public void setSex(String sex) {
        mSex = sex;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
    }

    public String getUserType() {
        return mUserType;
    }

    public void setUserType(String userType) {
        mUserType = userType;
    }

    public String getUserRole() {
        return mUserRole;
    }

    public void setUserRole(String userRole) {
        mUserRole = userRole;
    }

    public void setLastUpdate(Date lastUpdate) {
        mLastUpdate = lastUpdate;
    }
    public Date getLastUpdate(){
        return mLastUpdate;
    }

    //endregion
}
