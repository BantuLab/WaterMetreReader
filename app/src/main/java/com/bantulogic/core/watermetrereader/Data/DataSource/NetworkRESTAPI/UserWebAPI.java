package com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ApiResponse;

import androidx.lifecycle.LiveData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserWebAPI {
    @GET("/api/users/{user_id}")
    LiveData<ApiResponse<User>> getUser(@Path("user_id") String user_id);

    @POST("/api/login")
    Call<ResponseBody> getAuthToken();
}
