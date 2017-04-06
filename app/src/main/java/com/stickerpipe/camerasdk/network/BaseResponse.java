package com.stickerpipe.camerasdk.network;

import com.google.gson.annotations.Expose;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class BaseResponse<T> {
    @Expose
    private String message;
    @Expose
    protected T data;

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "NetworkResponseModel{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
