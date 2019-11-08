package com.his.retrofitnetworkapi.interfaces;

import com.his.retrofitnetworkapi.error.APIError;

public interface IRetrofitResponseCallback<T> {// extends Callback<T> {

    /**
     * This is a method which will be called once a response is received from a network API
     *
     * @param REQUEST_CODE is the request code
     * @param object
     */
    void onResponseReceived(int REQUEST_CODE, T object);

    /**
     * This is a method which will be called once a error response is received from a network API
     *
     * @param apiError
     */
    void onErrorReceived(APIError apiError);
}
