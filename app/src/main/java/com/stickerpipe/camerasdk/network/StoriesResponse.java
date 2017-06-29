package com.stickerpipe.camerasdk.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class StoriesResponse extends BaseResponse<List<StoriesResponse.Story>> {


    @Override
    public List<StoriesResponse.Story> getData() {
        if (data == null) {
            return new ArrayList<>();
        } else {
            return super.getData();
        }
    }

    static class Story {
        @Expose
        int id;

        @Expose
        @SerializedName("data_hash")
        String dataHash;

        @Expose
        Icon icon = new Icon();

        @Expose
        List<Stamp> content = new ArrayList<>();

    }

    static class Icon {
        @Expose
        @SerializedName("image")
        Map<String, String> links = new HashMap<>();
    }

    static class Stamp {
        @Expose
        @SerializedName("content_id")
        int id;

        @Expose
        @SerializedName("image")
        Map<String, String> links = new HashMap<>();

        @Expose
        List<Point> points;

        @Expose
        String type;

        @Expose
        @SerializedName("delta_offset")
        Point offset;

        @Expose
        @SerializedName("delta_rotation")
        int rotation;

        @Expose
        @SerializedName("delta_scale")
        float scale;

        @Expose
        String position;

        @Expose
        int order;

    }

    public static class Point {
        @Expose
        public int x;
        @Expose
        public int y;
    }
}
