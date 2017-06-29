package com.stickerpipe.camerasdk;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;

import com.stickerpipe.camerasdk.provider.stamps.StampsBean;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;
import com.stickerpipe.camerasdk.provider.stamps.StampsContentValues;
import com.stickerpipe.camerasdk.provider.stamps.StampsSelection;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsColumns;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsContentValues;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsCursor;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsSelection;
import com.stickerpipe.camerasdk.provider.stories.StoriesBean;
import com.stickerpipe.camerasdk.provider.stories.StoriesColumns;
import com.stickerpipe.camerasdk.provider.stories.StoriesContentValues;
import com.stickerpipe.camerasdk.provider.stories.StoriesCursor;
import com.stickerpipe.camerasdk.provider.stories.StoriesSelection;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsBean;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsColumns;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsContentValues;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */
public class StorageManager extends PreferenceHelper {
    private static final String KEY_DEVICE_ID = "key_device_id";
    public static final String KEY_IMAGE_FINAL = "final_";

    private static StorageManager instance;

    /**
     * Create helper instance and instantiate default shared preferences
     *
     * @param context preference context
     */
    private StorageManager(Context context) {
        super(context);
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new StorageManager(context);
        }
    }

    public static StorageManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("Manager not initialized");
        }
        return instance;
    }


    public String getImagesFolder() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/stamps/";
    }

    /**
     * Get stored device id value
     */
    String getDeviceId() {
        return getStringValue(KEY_DEVICE_ID);
    }

    /**
     * Store device id value to shared preferences
     *
     * @param deviceId Device id
     */
    void storeDeviceId(String deviceId) {
        storeValue(KEY_DEVICE_ID, deviceId);
    }

    public void updateStampSets(SparseArray<List<StampsBean>> data) {
        List<Integer> storedSets = new ArrayList<>();
        StampSetsCursor stampsSetCursor = new StampSetsSelection().query(App.instance.getContentResolver());
        while (stampsSetCursor.moveToNext()) {
            storedSets.add(stampsSetCursor.getStampsSetId());
        }
        List<ContentValues> setsBulk = new ArrayList<>();
        List<ContentValues> stampsBulk = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int setId = data.keyAt(i);
            if (storedSets.contains(setId)) {
                storedSets.remove(storedSets.indexOf(setId));
            } else {
                setsBulk.add(createSetContentValues(setId));
                stampsBulk.addAll(createStampsContentValues(data.get(setId)));
            }
        }
        if (setsBulk.size() > 0) {
            App.instance.getContentResolver().bulkInsert(StampSetsColumns.CONTENT_URI, setsBulk.toArray(new ContentValues[setsBulk.size()]));
        }
        if (stampsBulk.size() > 0) {
            App.instance.getContentResolver().bulkInsert(StampsColumns.CONTENT_URI, stampsBulk.toArray(new ContentValues[stampsBulk.size()]));
        }
        if (storedSets.size() > 0) {
            new StampSetsSelection()
                    .stampsSetId(storedSets.toArray(new Integer[storedSets.size()]))
                    .delete(App.instance.getContentResolver());
            new StampsSelection()
                    .stampsSetId(storedSets.toArray(new Integer[storedSets.size()]))
                    .delete(App.instance.getContentResolver());
        }
    }

    private ContentValues createSetContentValues(int setId) {
        StampSetsContentValues cv = new StampSetsContentValues();
        cv.putStampsSetId(setId);
        return cv.values();
    }

    private List<ContentValues> createStampsContentValues(List<StampsBean> stampsBeans) {
        List<ContentValues> stampCvList = new ArrayList<>();
        for (StampsBean item : stampsBeans) {
            StampsContentValues cv = new StampsContentValues();
            cv.putStampId(item.getStampId());
            cv.putStampsSetId(item.getStampsSetId());
            cv.putLink(item.getLink());
            stampCvList.add(cv.values());
        }
        return stampCvList;
    }

    public void updateStories(Map<StoriesBean, List<StoryStampsBean>> data) {
        SparseArray<String> storedStories = new SparseArray<>();
        StoriesCursor storiesCursor = new StoriesSelection().query(App.instance.getContentResolver());
        while (storiesCursor.moveToNext()) {
            storedStories.put(storiesCursor.getStoryId(), storiesCursor.getDataHash());
        }
        List<ContentValues> storiesBulk = new ArrayList<>();
        List<ContentValues> stampsBulk = new ArrayList<>();
        for (Map.Entry<StoriesBean, List<StoryStampsBean>> story : data.entrySet()) {
            String storedStoryDataHash = storedStories.get(story.getKey().getStoryId());
            if (!TextUtils.isEmpty(storedStoryDataHash)
                    && storedStoryDataHash.equals(story.getKey().getDataHash())) {
                storedStories.remove(story.getKey().getStoryId());
            } else {
                if (!TextUtils.isEmpty(storedStoryDataHash)) {
                    new StoryStampsSelection()
                            .storyId(story.getKey().getStoryId())
                            .delete(App.instance.getContentResolver());
                }
                storiesBulk.add(createStoryContentValues(story.getKey()));
                stampsBulk.addAll(createStoryStampsContentValues(story.getValue()));
            }
        }
        if (storiesBulk.size() > 0) {
            App.instance.getContentResolver().bulkInsert(StoriesColumns.CONTENT_URI, storiesBulk.toArray(new ContentValues[storiesBulk.size()]));
        }
        if (stampsBulk.size() > 0) {
            App.instance.getContentResolver().bulkInsert(StoryStampsColumns.CONTENT_URI, stampsBulk.toArray(new ContentValues[stampsBulk.size()]));
        }
        if (storedStories.size() > 0) {
            Integer[] storiesForRemove = new Integer[storedStories.size()];
            for (int i = 0; i < storedStories.size(); i++) {
                storiesForRemove[i] = storedStories.keyAt(i);
            }
            new StoriesSelection()
                    .storyId(storiesForRemove)
                    .delete(App.instance.getContentResolver());
            new StoryStampsSelection()
                    .storyId(storiesForRemove)
                    .delete(App.instance.getContentResolver());
        }
    }

    private ContentValues createStoryContentValues(StoriesBean story) {
        StoriesContentValues cv = new StoriesContentValues()
                .putStoryId(story.getStoryId())
                .putDataHash(story.getDataHash())
                .putIconLink(story.getIconLink());
        return cv.values();
    }

    private List<ContentValues> createStoryStampsContentValues(List<StoryStampsBean> stamps) {
        List<ContentValues> values = new ArrayList<>();
        for (StoryStampsBean stamp : stamps) {
            StoryStampsContentValues cv = new StoryStampsContentValues()
                    .putStampId(stamp.getStampId())
                    .putStoryId(stamp.getStoryId())
                    .putLink(stamp.getLink())
                    .putPoints(stamp.getPoints())
                    .putPositionType(stamp.getPositionType())
                    .putRotation(stamp.getRotation())
                    .putScale(stamp.getScale())
                    .putOffset(stamp.getOffset())
                    .putStampOrder(stamp.getStampOrder())
                    .putType(stamp.getType());
            values.add(cv.values());
        }
        return values;
    }
}

