package com.tvshow.network.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.tvshow.network.response.ResponseCast;
import com.tvshow.network.response.ResponseTvShows;

/**
 * Created by ismael on 15/03/18.
 */

public interface ApiService {
     String API_KEY = "5145869f14f290cf981600f173ff35d9";

    @GET("popular")
    Call<ResponseTvShows> getTvShows(@Query("api_key") String api_key, @Query("page") String page);

    @GET("{tvId}/credits")
    Call<ResponseCast> getCast(@Path("tvId") String tvId, @Query("api_key") String api_key);
}
