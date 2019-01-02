package com.bantulogic.core.watermetrereader.Helpers;

import android.app.Application;

public class App extends Application {
    private Session mSession;
    private String mToken;
    private String mUsername;
    private String mPassword;
    private  AuthenticationListener mAuthenticationListener;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /*
    * Use a storage option to store the
    * credentials and user info
    * i.e. SQLite, SharedPreferences etc
     */
    public Session getSession(){
        if (mSession == null){
            mSession = new Session() {
                @Override
                public boolean isLoggedIn() {
                    /**
                     * Check if token exits or not
                     * return true if exists otherwise false
                     * assuming that token exists
                     */
                    return true;
                }

                @Override
                public void saveToken(String token) {
                    //Save the token
                    mToken = token;
                    //Save to sharedPrefs or DB
                }

                @Override
                public String getToken() {
                    //return the token that was saved earlier
                    return mToken;
                }

                @Override
                public void saveUsername() {
                    //Save username to sharedPrefs too

                }

                @Override
                public String getUsername() {
                    return mUsername;
                }

                @Override
                public void savePassword(String password) {
                    //encrypt and save to prefs
                }

                @Override
                public String getPassword() {
                    return mPassword;
                }

                @Override
                public void invalidate() {
                    /**
                     * get called when user become logged out
                     * delete token and other user info
                     * (i.e. email, password)
                     * from storage
                     *
                     * sending logged out event to its listener
                     * i.e. Activity, Fragment, Service
                     */
                    if (mAuthenticationListener != null){
                        mAuthenticationListener.onUserLoggedOut();
                    }

                }
            };
        }
        return mSession;
    }
    interface AuthenticationListener {
        void onUserLoggedOut();
    }
    public void setAuthenticationListener(AuthenticationListener listener) {
        this.mAuthenticationListener = listener;
    }
}
