package com.bantulogic.core.watermetrereader.data.datasource.network;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomerWebAPI {

    @GET("/water/api/customers")
    Call<List<Customer>> getCustomers(
            @Query("assigned_user_id") String assigned_user_id);
}
