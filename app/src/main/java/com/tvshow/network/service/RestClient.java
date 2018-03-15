package com.tvshow.network.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ismael on 15/03/18.
 */

public class RestClient {
    private static Retrofit retrofit;

    public static Retrofit getRestClient(String endpoint) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(endpoint)
                    .build();
        }
        return retrofit;
    }
}
