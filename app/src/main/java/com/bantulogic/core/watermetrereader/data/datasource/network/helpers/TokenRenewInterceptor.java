package com.bantulogic.core.watermetrereader.data.datasource.network.helpers;

import com.bantulogic.core.watermetrereader.helpers.Session;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class TokenRenewInterceptor implements Interceptor {
    private Session mSession;

    public TokenRenewInterceptor(Session session){
        this.mSession = session;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        // if 'x-auth-token' is available into the response header
        // save the new token into session.The header key can be
        // different upon implementation of backend.
        String newToken = response.header("x-auth-token");
        if (newToken != null){
            mSession.saveToken(newToken);
        }
        return response;
    }
}
