package com.bantulogic.core.watermetrereader.data.datasource.network;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.FcmToken;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ApiResponse;

import androidx.lifecycle.LiveData;
import retrofit2.http.POST;

public interface FcmTokenWebAPI {
    @POST("water/api/fcmtoken")
    LiveData<ApiResponse<FcmToken>> register_fcm_token();
}
