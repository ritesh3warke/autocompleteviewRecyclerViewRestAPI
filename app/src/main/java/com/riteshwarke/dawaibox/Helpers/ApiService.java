package com.riteshwarke.dawaibox.Helpers;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ritesh Warke on 19/05/17.
 */

public interface ApiService {
    @GET("DawaiBoxSmartPrescription/searchdrugs/")
    Call<JsonElement> createTask(@Query("Id") String id, @Query("SearchText") String searchText, @Query("start") String start, @Query("limit") String limit, @Query("role") String role);

}
