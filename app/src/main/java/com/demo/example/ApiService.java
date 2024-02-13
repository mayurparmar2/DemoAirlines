package com.demo.example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("qfonapp-passenger")
    Call<ApiResponse> getAirlines(
            @Query("page") int page,
            @Query("size") int size
    );
}
