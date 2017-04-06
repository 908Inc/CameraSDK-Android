package com.stickerpipe.camerasdk.provider.stamps;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Stamps
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class StampsBean implements StampsModel {
    private long mId;
    private Integer mStampId;
    private String mLink;
    private Integer mStampsSetId;

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
     * Content id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampId() {
        return mStampId;
    }

    /**
     * Content id from server
     * Can be {@code null}.
     */
    public void setStampId(@Nullable Integer stampId) {
        mStampId = stampId;
    }

    /**
     * Stamp image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getLink() {
        return mLink;
    }

    /**
     * Stamp image link
     * Can be {@code null}.
     */
    public void setLink(@Nullable String link) {
        mLink = link;
    }

    /**
     * Stamps set id
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampsSetId() {
        return mStampsSetId;
    }

    /**
     * Stamps set id
     * Can be {@code null}.
     */
    public void setStampsSetId(@Nullable Integer stampsSetId) {
        mStampsSetId = stampsSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StampsBean bean = (StampsBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new StampsBean with specified values.
     */
    @NonNull
    public static StampsBean newInstance(long id, @Nullable Integer stampId, @Nullable String link, @Nullable Integer stampsSetId) {
        StampsBean res = new StampsBean();
        res.mId = id;
        res.mStampId = stampId;
        res.mLink = link;
        res.mStampsSetId = stampsSetId;
        return res;
    }

    /**
     * Instantiate a new StampsBean with all the values copied from the given model.
     */
    @NonNull
    public static StampsBean copy(@NonNull StampsModel from) {
        StampsBean res = new StampsBean();
        res.mId = from.getId();
        res.mStampId = from.getStampId();
        res.mLink = from.getLink();
        res.mStampsSetId = from.getStampsSetId();
        return res;
    }

    public static class Builder {
        private StampsBean mRes = new StampsBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Content id from server
         * Can be {@code null}.
         */
        public Builder stampId(@Nullable Integer stampId) {
            mRes.mStampId = stampId;
            return this;
        }

        /**
         * Stamp image link
         * Can be {@code null}.
         */
        public Builder link(@Nullable String link) {
            mRes.mLink = link;
            return this;
        }

        /**
         * Stamps set id
         * Can be {@code null}.
         */
        public Builder stampsSetId(@Nullable Integer stampsSetId) {
            mRes.mStampsSetId = stampsSetId;
            return this;
        }

        /**
         * Get a new StampsBean built with the given values.
         */
        public StampsBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
