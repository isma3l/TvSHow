package com.tvshow.network.service;

import com.tvshow.network.Endpoints;

/**
 * Created by ismael on 15/03/18.
 */

public class ApiUtils {
    public static ApiService getApiService() {
        return RestClient.getRestClient(Endpoints.BASE).create(ApiService.class);
    }
}
