package com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Authorization;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Token;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthorizationWebAPI {

    @POST("/api/auth/login")
    LiveData<ApiResponse<Token>> login(
            @Query("username") String username,
            @Query("password") String password
    );

    @POST("/api/auth/renew_token")
    LiveData<ApiResponse<Token>> renew_token();
}
