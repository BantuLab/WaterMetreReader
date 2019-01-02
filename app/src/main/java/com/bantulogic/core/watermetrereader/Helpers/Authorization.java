package com.bantulogic.core.watermetrereader.Helpers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Authorization {
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

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("token")
    @Expose
    private String token;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return userId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}