package com.stickerpipe.camerasdk.network;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */
interface NetworkService {

    @GET("stamps")
    Observable<StampsResponse> getStamps();

    @GET("new_stories")
    Observable<StoriesResponse> getStories();

    @POST("statistics")
    Observable<BaseResponse> sendAnalytics(@Body RequestBody body);

}