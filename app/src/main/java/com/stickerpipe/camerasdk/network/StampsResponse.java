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

class StampsResponse extends BaseResponse<List<StampsResponse.StampsSet>> {
    static class StampsSet {
        @Expose
        @SerializedName("pack_id")
        int id;

        @Expose
        List<Stamp> stamps = new ArrayList<>();

        static class Stamp {
            @Expose
            @SerializedName("content_id")
            int id;
            @Expose
            @SerializedName("image")
            Map<String, String> links = new HashMap<>();
        }
    }

    @Override
    public List<StampsSet> getData() {
        if (data == null) {
            return new ArrayList<>();
        } else {
            return super.getData();
        }
    }
}
