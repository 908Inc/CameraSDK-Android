package com.stickerpipe.camerasdk.network;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stickerpipe.camerasdk.App;
import com.stickerpipe.camerasdk.BuildConfig;
import com.stickerpipe.camerasdk.Logger;
import com.stickerpipe.camerasdk.StorageManager;
import com.stickerpipe.camerasdk.Utils;
import com.stickerpipe.camerasdk.provider.stamps.StampsBean;
import com.stickerpipe.camerasdk.provider.stories.StoriesBean;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class NetworkManager {
    private static final String BASE_URL_API = "https://api.stickerpipe.com";

    private static final String API_PATH = "/api/v2/";
    private static final String API_URL = BASE_URL_API + API_PATH;
    private static final String TAG = NetworkManager.class.getSimpleName();
    private static NetworkManager instance;
    private final NetworkService mNetworkService;
    private Gson gson;

    public static NetworkManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("Manager not initialized");
        }
        return instance;
    }

    public static void init(Context context, String apiKey) {
        instance = new NetworkManager(context, apiKey);
    }

    private NetworkManager(Context context, String apiKey) {
        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
        NetworkHeaderInterceptor headersInterceptor = new NetworkHeaderInterceptor(
                apiKey,
                Utils.md5(Utils.getDeviceId(context)),
                BuildConfig.VERSION_NAME,
                Utils.getDeviceId(context),
                context.getPackageName(),
                Utils.getDensityName(context));
        mOkHttpClientBuilder.networkInterceptors().add(headersInterceptor);
        mOkHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);
        mOkHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS);
        mOkHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoginInterceptors = new HttpLoggingInterceptor();
        httpLoginInterceptors.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClientBuilder.networkInterceptors().add(httpLoginInterceptors);

        // cache
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        mOkHttpClientBuilder.cache(cache);

        OkHttpClient mOkHttpClient = mOkHttpClientBuilder.build();
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        mNetworkService = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(NetworkService.class);

    }

    public Gson getGson() {
        return gson;
    }

    public void requestStampsUpdate() {
        mNetworkService.getStamps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> StorageManager.getInstance().updateStampSets(convertStampsData(r.data)),
                        throwable -> Logger.e(TAG, throwable));
    }

    private SparseArray<List<StampsBean>> convertStampsData(List<StampsResponse.StampsSet> data) {
        SparseArray<List<StampsBean>> result = new SparseArray<>();
        if (data != null && data.size() > 0) {
            String density = Utils.getDensityName(App.instance);
            for (StampsResponse.StampsSet stampsSet : data) {
                if (stampsSet.stamps.size() > 0) {
                    List<StampsBean> stampBeans = new ArrayList<>();
                    for (StampsResponse.StampsSet.Stamp stamp : stampsSet.stamps) {
                        if (stamp.links.size() > 0) {
                            StampsBean stampsBean = new StampsBean();
                            stampsBean.setStampId(stamp.id);
                            stampsBean.setStampsSetId(stampsSet.id);
                            stampsBean.setLink(stamp.links.get(density));
                            stampBeans.add(stampsBean);
                        }
                    }
                    result.put(stampsSet.id, stampBeans);
                }
            }
        }
        return result;
    }

    public void requestStoriesUpdate() {
        mNetworkService.getStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> StorageManager.getInstance().updateStories(convertStoriesData(r.data)),
                        throwable -> Logger.e(TAG, throwable));
    }

    private Map<StoriesBean, List<StoryStampsBean>> convertStoriesData(List<StoriesResponse.Story> data) {
        HashMap<StoriesBean, List<StoryStampsBean>> result = new HashMap<>();
        if (data != null && data.size() > 0) {
            String density = Utils.getDensityName(App.instance);
            for (StoriesResponse.Story story : data) {
                StoriesBean storyBean = new StoriesBean();
                storyBean.setId(story.id);
                storyBean.setStoryId(story.id);
                storyBean.setDataHash(story.dataHash);
                storyBean.setIconLink(story.icon.links.get(density));
                List<StoryStampsBean> stamps = new ArrayList<>();
                for (StoriesResponse.Stamp stamp : story.content) {
                    StoryStampsBean stampBean = new StoryStampsBean();
                    stampBean.setStampId(stamp.id);
                    stampBean.setStoryId(story.id);
                    stampBean.setLink(stamp.links.get(density));
                    stampBean.setPoints(gson.toJson(stamp.points));
                    stampBean.setType(stamp.type);
                    stampBean.setRotation(stamp.rotation);
                    stampBean.setScale(stamp.scale);
                    stampBean.setPositionType(stamp.position);
                    stampBean.setStampOrder(stamp.order);
                    stamps.add(stampBean);
                }
                result.put(storyBean, stamps);
            }
        }
        return result;
    }

    public void sendStampUseEvent(int contentId) {
        sendAnalyticsEvent("stamp", "use", String.valueOf(contentId));
    }

    public void sendStoryUseEvent(int storyId) {
        sendAnalyticsEvent("story", "use", String.valueOf(storyId));
    }

    private void sendAnalyticsEvent(String category, String action, String label) {
        JSONArray data = new JSONArray();
        JSONObject obj = new JSONObject();
        try {
            obj.put("category", category);
            obj.put("action", action);
            obj.put("label", label);
            data.put(obj);
            sendAnalyticsData(data);
        } catch (JSONException e) {
            Logger.e(TAG, "Can't create analytics request", e);
        }
    }

    private void sendAnalyticsData(JSONArray data) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data.toString());
        mNetworkService.sendAnalytics(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}