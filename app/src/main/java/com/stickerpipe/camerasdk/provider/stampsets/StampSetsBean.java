package com.stickerpipe.camerasdk.provider.stampsets;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Set of stamps
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class StampSetsBean implements StampSetsModel {
    private long mId;
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
    public Integer getStampsSetId() {
        return mStampsSetId;
    }

    /**
     * Content id from server
     * Can be {@code null}.
     */
    public void setStampsSetId(@Nullable Integer stampsSetId) {
        mStampsSetId = stampsSetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StampSetsBean bean = (StampSetsBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new StampSetsBean with specified values.
     */
    @NonNull
    public static StampSetsBean newInstance(long id, @Nullable Integer stampsSetId) {
        StampSetsBean res = new StampSetsBean();
        res.mId = id;
        res.mStampsSetId = stampsSetId;
        return res;
    }

    /**
     * Instantiate a new StampSetsBean with all the values copied from the given model.
     */
    @NonNull
    public static StampSetsBean copy(@NonNull StampSetsModel from) {
        StampSetsBean res = new StampSetsBean();
        res.mId = from.getId();
        res.mStampsSetId = from.getStampsSetId();
        return res;
    }

    public static class Builder {
        private StampSetsBean mRes = new StampSetsBean();

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
        public Builder stampsSetId(@Nullable Integer stampsSetId) {
            mRes.mStampsSetId = stampsSetId;
            return this;
        }

        /**
         * Get a new StampSetsBean built with the given values.
         */
        public StampSetsBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
