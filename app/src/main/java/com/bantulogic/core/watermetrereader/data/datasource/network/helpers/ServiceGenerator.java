package com.bantulogic.core.watermetrereader.data.datasource.network.helpers;

import android.text.TextUtils;
import android.util.Log;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = "http://metre-reader.bantulabtech.com/";

    private static HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder sOkHttpClient = new OkHttpClient.Builder()
            .addInterceptor(mHttpLoggingInterceptor);




    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory());

    public static Retrofit sRetrofit = sBuilder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass,null,null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password){
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            String authToken = Credentials.basic(username, password);
            Log.i("Chaiwa","Authorization: "+authToken);
            Log.i("ServiceGenerator","Authorization: "+authToken);

            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);

    }
    public static <S> S createService(
            Class<S> serviceClass, final String authToken
    ){
        if (!TextUtils.isEmpty(authToken)){
            AuthenticationHeaderInterceptor interceptor = new AuthenticationHeaderInterceptor(authToken);
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
