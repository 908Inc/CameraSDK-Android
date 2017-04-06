package com.stickerpipe.camerasdk.provider.pendingtasks;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Pending tasks
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class PendingTasksBean implements PendingTasksModel {
    private long mId;
    private String mCategory;
    private String mAction;
    private String mValue;
    private Boolean mIspending;

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
     * Task category
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getCategory() {
        return mCategory;
    }

    /**
     * Task category
     * Can be {@code null}.
     */
    public void setCategory(@Nullable String category) {
        mCategory = category;
    }

    /**
     * Task action
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getAction() {
        return mAction;
    }

    /**
     * Task action
     * Can be {@code null}.
     */
    public void setAction(@Nullable String action) {
        mAction = action;
    }

    /**
     * Task value
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getValue() {
        return mValue;
    }

    /**
     * Task value
     * Can be {@code null}.
     */
    public void setValue(@Nullable String value) {
        mValue = value;
    }

    /**
     * Is task waiting for execution
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Boolean getIspending() {
        return mIspending;
    }

    /**
     * Is task waiting for execution
     * Can be {@code null}.
     */
    public void setIspending(@Nullable Boolean ispending) {
        mIspending = ispending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PendingTasksBean bean = (PendingTasksBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new PendingTasksBean with specified values.
     */
    @NonNull
    public static PendingTasksBean newInstance(long id, @Nullable String category, @Nullable String action, @Nullable String value, @Nullable Boolean ispending) {
        PendingTasksBean res = new PendingTasksBean();
        res.mId = id;
        res.mCategory = category;
        res.mAction = action;
        res.mValue = value;
        res.mIspending = ispending;
        return res;
    }

    /**
     * Instantiate a new PendingTasksBean with all the values copied from the given model.
     */
    @NonNull
    public static PendingTasksBean copy(@NonNull PendingTasksModel from) {
        PendingTasksBean res = new PendingTasksBean();
        res.mId = from.getId();
        res.mCategory = from.getCategory();
        res.mAction = from.getAction();
        res.mValue = from.getValue();
        res.mIspending = from.getIspending();
        return res;
    }

    public static class Builder {
        private PendingTasksBean mRes = new PendingTasksBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Task category
         * Can be {@code null}.
         */
        public Builder category(@Nullable String category) {
            mRes.mCategory = category;
            return this;
        }

        /**
         * Task action
         * Can be {@code null}.
         */
        public Builder action(@Nullable String action) {
            mRes.mAction = action;
            return this;
        }

        /**
         * Task value
         * Can be {@code null}.
         */
        public Builder value(@Nullable String value) {
            mRes.mValue = value;
            return this;
        }

        /**
         * Is task waiting for execution
         * Can be {@code null}.
         */
        public Builder ispending(@Nullable Boolean ispending) {
            mRes.mIspending = ispending;
            return this;
        }

        /**
         * Get a new PendingTasksBean built with the given values.
         */
        public PendingTasksBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
