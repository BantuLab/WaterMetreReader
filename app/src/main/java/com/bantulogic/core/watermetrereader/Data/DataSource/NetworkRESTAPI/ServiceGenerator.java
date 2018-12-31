package com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI;

import android.text.TextUtils;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String API_BASE_URL = "https://reqres.in";

    private static OkHttpClient.Builder sOkHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder sBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit sRetrofit = sBuilder.build();

    private static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass,null,null);
    }

    private static <S> S createService(
            Class<S> serviceClass, String username, String password){
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            String authToken = Credentials.basic(username, password);

            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);

    }
    private static <S> S createService(
            Class<S> serviceClass, final String authToken
    ){
        if (!TextUtils.isEmpty(authToken)){
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if (!sOkHttpClient.interceptors().contains(interceptor)){
                sOkHttpClient.addInterceptor(interceptor);
                sBuilder.client(sOkHttpClient.build());
                sRetrofit = sBuilder.build();
            }
        }
        return sRetrofit.create(serviceClass);
    }
}
