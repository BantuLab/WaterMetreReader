package com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI;

import android.text.TextUtils;

import com.bantulogic.core.watermetrereader.Helpers.TokenRenewInterceptor;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://f205ba47-781e-46f9-9773-b5e62f905431.mock.pstmn.io/water/";

    private static OkHttpClient.Builder sOkHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(LiveData)
            ;

    public static Retrofit sRetrofit = sBuilder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass,null,null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password){
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            String authToken = Credentials.basic(username, password);

            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);

    }
    public static <S> S createService(
            Class<S> serviceClass, final String authToken
    ){
        if (!TextUtils.isEmpty(authToken)){
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
            //Add Token Renew Logic
           // TokenRenewInterceptor tokenRenewInterceptor = new TokenRenewInterceptor();
            /**
             * Here, we’ve added another interceptor AuthorizationInterceptor .
             * That checks for response code 401 and 403 which conventionally denotes an
             * authentication issue. When this error is encountered we just call invalidate()
             * to delete token and user’s data. An event is passed to it’s
             * listener (i.e: view/service) to take the necessary actions (i.e: Switch to Login Activity).
             */

            if (!sOkHttpClient.interceptors().contains(interceptor)){
                sOkHttpClient.addInterceptor(interceptor);
                sBuilder.client(sOkHttpClient.build());
                sRetrofit = sBuilder.build();
            }

        }
        return sRetrofit.create(serviceClass);
    }
}
