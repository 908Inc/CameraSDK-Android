package com.stickerpipe.camerasdk.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */
public class NetworkHeaderInterceptor implements Interceptor {

    private Map<String, String> headers = new HashMap<>();

    public NetworkHeaderInterceptor(String apiKey, String userId, String appVersion, String deviceId, String packageName, String density) {
        headers.put("Platform", "Android");
        headers.put("UserID", userId);
        headers.put("AppVersion", appVersion);
        headers.put("ApiKey", apiKey);
        headers.put("Package", packageName);
        headers.put("DeviceID", deviceId);
        headers.put("Density", density);
        headers.put("Accept", "application/json");
    }

    /**
     * Intercept network request chain and add custom headers
     *
     * @param chain Network request chain
     * @return Intercepted request
     * @throws IOException chain proceed exception
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        return chain.proceed(builder.build());
    }
}
