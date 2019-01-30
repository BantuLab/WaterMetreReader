package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities;

import android.util.Log;

import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;
import com.bantulogic.core.watermetrereader.Helpers.Converters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import retrofit2.Response;

@Entity
@TypeConverters(Converters.class)
public  class Authorization{
    //region COMMENTS/NOTES
    /**
     * Here, we are manipulating the request chain if 401/403 error occurred. When occurred,
     * we generate the auth key (an encoded combination of email and password)
     * and pass it to login API as a request header. As login API get called it returns
     * an Authorization object if the request is successful.
     * Authorization includes the new token which we have to save into session.
     * Thus the token get updated. Now we can retry for the mainRequest for which
     * the unauthorized error occurred. We add the new token to the mainRequest header
     * and let the request to make again. It should now get result successfully.
     *
     * Process flow: getAccountInfo() → Unauthorized Error → loginAccount()
     * → Success (token updated) → retry getAccountInfo() → Success!
     */
    //endregion
    @SerializedName("username")
    @Expose
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="username")
    private String username;

    @SerializedName("password")
    @Expose
    @NonNull
    @ColumnInfo(name="password")
    private String password;

    @SerializedName("sub")
    @Expose
    @NonNull
    @ColumnInfo(name = "user_id")
    private String sub;

    @SerializedName("token")
    @Expose
    @ColumnInfo(name = "token")
    private String token;

    @SerializedName("user_type")
    @Expose
    @ColumnInfo(name = "user_type")
    private String userType;

    @SerializedName("scope")
    @Expose
    @ColumnInfo(name = "scope")
    @TypeConverters(Converters.ArrayToStringConverter.class)
    private List<String> scope;

    @SerializedName("iat")
    @Expose
    @ColumnInfo(name = "iat")
    private Date iat;

    @SerializedName("exp")
    @Expose
    @ColumnInfo(name = "exp")
    private Date exp;

    @SerializedName("aud")
    @Expose
    @ColumnInfo(name = "aud")
    @TypeConverters(Converters.ArrayToStringConverter.class)
    private List<String> aud;

    @SerializedName("iss")
    @Expose
    @ColumnInfo(name = "iss")
    private String iss;

    @SerializedName("loggedOut")
    @Expose
    @ColumnInfo(name = "logged_out")
    private boolean loggedOut;

    @SerializedName("loggedIn")
    @Expose
    @ColumnInfo(name = "logged_in")
    private boolean loggedIn;


    public Authorization(
            String username,
            String password,
            String token,
            String sub,
            String userType,
            List<String> scope,
            Date iat,
            Date exp,
            List<String> aud,
            String iss,
            boolean loggedOut,
            boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.sub = sub;
        this.userType = userType;
        this.scope = scope;
        this.iat = iat;
        this.exp = exp;
        this.aud = aud;
        this.iss = iss;
        this.loggedOut = loggedOut;
        this.loggedIn = loggedIn;

    }


    public boolean isTokenExpired(){
        return this.exp.after(Calendar.getInstance().getTime());
    }
    public boolean isLoggedOut(){
        return this.loggedOut;
    }

    public void setLoggedOut(boolean loggedOut) {
        this.loggedOut = loggedOut;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn(){
        return this.loggedIn;

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        //Decrypt
        return password;
    }

    public void setPassword(String password) {
        //Encrypt
        this.password = password;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String user_type) {
        this.userType = user_type;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public List<String> getAud() {
        return aud;
    }

    public void setAud(List<String> aud) {
        this.aud = aud;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }
}
