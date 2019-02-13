package com.bantulogic.core.watermetrereader.data.datasource.network;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Token;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthorizationWebAPI {

    @FormUrlEncoded
    @POST("/water/api/auth/login")
    LiveData<ApiResponse<Token>> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("/water/api/auth/renew_token")
    LiveData<ApiResponse<Token>> renew_token();
}
