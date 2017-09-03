package com.example.bakingtime.network;

/**
 * Created by rezagama on 9/3/17.
 */

public interface NetworkCallback<R, E> {
    void onSuccess(R response);

    void onError(E error);

    void onCompleted();
}
