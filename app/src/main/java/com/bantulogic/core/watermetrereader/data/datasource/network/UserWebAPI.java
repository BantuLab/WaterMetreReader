package com.bantulogic.core.watermetrereader.data.datasource.network;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.User;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserWebAPI {
    @GET("/water/api/users/{user_id}")
    LiveData<ApiResponse<User>> getUser(@Path("user_id") String user_id);

}
