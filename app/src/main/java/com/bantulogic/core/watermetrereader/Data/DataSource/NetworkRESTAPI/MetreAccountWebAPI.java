package com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MetreAccountWebAPI {

    @GET("/water/api/metre_accounts")
    Call<List<MetreAccount>> getMetreAccountsByUser(
            @Query("assigned_user_id") String assignedUserId
    );
    @GET("/api/metre_accounts/{metre_id}")
    Call<MetreAccount> getMetreAccount(
            @Path("metre_id") long  metreId
    );
}
