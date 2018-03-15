package com.tvshow.network.response;

import com.tvshow.network.service.RestClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by ismael on 15/03/18.
 */

public class ErrorUtils {

    public static ResponseError parseError(Response<?> response) {
        Converter<ResponseBody, ResponseError> converter =
                RestClient.getRestClient("")
                        .responseBodyConverter(ResponseError.class, new Annotation[0]);

        ResponseError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            error = new ResponseError();
        }

        return error;
    }
}
