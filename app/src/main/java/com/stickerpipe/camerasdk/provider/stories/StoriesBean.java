package com.stickerpipe.camerasdk.provider.stories;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Stories
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class StoriesBean implements StoriesModel {
    private long mId;
    private Integer mStoryId;
    private String mDataHash;
    private String mIconLink;

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        return mId;
    }

    /**
     * Primary key.
     */
    public void setId(long id) {
        mId = id;
    }

    /**
     * Story id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStoryId() {
        return mStoryId;
    }

    /**
     * Story id from server
     * Can be {@code null}.
     */
    public void setStoryId(@Nullable Integer storyId) {
        mStoryId = storyId;
    }

    /**
     * Story data hash
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getDataHash() {
        return mDataHash;
    }

    /**
     * Story data hash
     * Can be {@code null}.
     */
    public void setDataHash(@Nullable String dataHash) {
        mDataHash = dataHash;
    }

    /**
     * Story icon link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getIconLink() {
        return mIconLink;
    }

    /**
     * Story icon link
     * Can be {@code null}.
     */
    public void setIconLink(@Nullable String iconLink) {
        mIconLink = iconLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoriesBean bean = (StoriesBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new StoriesBean with specified values.
     */
    @NonNull
    public static StoriesBean newInstance(long id, @Nullable Integer storyId, @Nullable String dataHash, @Nullable String iconLink) {
        StoriesBean res = new StoriesBean();
        res.mId = id;
        res.mStoryId = storyId;
        res.mDataHash = dataHash;
        res.mIconLink = iconLink;
        return res;
    }

    /**
     * Instantiate a new StoriesBean with all the values copied from the given model.
     */
    @NonNull
    public static StoriesBean copy(@NonNull StoriesModel from) {
        StoriesBean res = new StoriesBean();
        res.mId = from.getId();
        res.mStoryId = from.getStoryId();
        res.mDataHash = from.getDataHash();
        res.mIconLink = from.getIconLink();
        return res;
    }

    public static class Builder {
        private StoriesBean mRes = new StoriesBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Story id from server
         * Can be {@code null}.
         */
        public Builder storyId(@Nullable Integer storyId) {
            mRes.mStoryId = storyId;
            return this;
        }

        /**
         * Story data hash
         * Can be {@code null}.
         */
        public Builder dataHash(@Nullable String dataHash) {
            mRes.mDataHash = dataHash;
            return this;
        }

        /**
         * Story icon link
         * Can be {@code null}.
         */
        public Builder iconLink(@Nullable String iconLink) {
            mRes.mIconLink = iconLink;
            return this;
        }

        /**
         * Get a new StoriesBean built with the given values.
         */
        public StoriesBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
