package com.stickerpipe.camerasdk.provider.storystamps;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Story stamps
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class StoryStampsBean implements StoryStampsModel {
    private long mId;
    private Integer mStampId;
    private String mLink;
    private String mPoints;
    private String mOffset;
    private String mType;
    private Integer mRotation;
    private Float mScale;
    private String mPositionType;
    private Integer mStampOrder;
    private Integer mStoryId;

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
     * Stamp id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampId() {
        return mStampId;
    }

    /**
     * Stamp id from server
     * Can be {@code null}.
     */
    public void setStampId(@Nullable Integer stampId) {
        mStampId = stampId;
    }

    /**
     * Image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getLink() {
        return mLink;
    }

    /**
     * Image link
     * Can be {@code null}.
     */
    public void setLink(@Nullable String link) {
        mLink = link;
    }

    /**
     * Control point for static stamps
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getPoints() {
        return mPoints;
    }

    /**
     * Control point for static stamps
     * Can be {@code null}.
     */
    public void setPoints(@Nullable String points) {
        mPoints = points;
    }

    /**
     * Offset for dynamic stamps
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getOffset() {
        return mOffset;
    }

    /**
     * Offset for dynamic stamps
     * Can be {@code null}.
     */
    public void setOffset(@Nullable String offset) {
        mOffset = offset;
    }

    /**
     * Stamp type
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getType() {
        return mType;
    }

    /**
     * Stamp type
     * Can be {@code null}.
     */
    public void setType(@Nullable String type) {
        mType = type;
    }

    /**
     * Stamp rotation
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getRotation() {
        return mRotation;
    }

    /**
     * Stamp rotation
     * Can be {@code null}.
     */
    public void setRotation(@Nullable Integer rotation) {
        mRotation = rotation;
    }

    /**
     * Stamp scale
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Float getScale() {
        return mScale;
    }

    /**
     * Stamp scale
     * Can be {@code null}.
     */
    public void setScale(@Nullable Float scale) {
        mScale = scale;
    }

    /**
     * Stamp position type
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getPositionType() {
        return mPositionType;
    }

    /**
     * Stamp position type
     * Can be {@code null}.
     */
    public void setPositionType(@Nullable String positionType) {
        mPositionType = positionType;
    }

    /**
     * Stamp order for current story
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampOrder() {
        return mStampOrder;
    }

    /**
     * Stamp order for current story
     * Can be {@code null}.
     */
    public void setStampOrder(@Nullable Integer stampOrder) {
        mStampOrder = stampOrder;
    }

    /**
     * Story id
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStoryId() {
        return mStoryId;
    }

    /**
     * Story id
     * Can be {@code null}.
     */
    public void setStoryId(@Nullable Integer storyId) {
        mStoryId = storyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryStampsBean bean = (StoryStampsBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new StoryStampsBean with specified values.
     */
    @NonNull
    public static StoryStampsBean newInstance(long id, @Nullable Integer stampId, @Nullable String link, @Nullable String points, @Nullable String offset, @Nullable String type, @Nullable Integer rotation, @Nullable Float scale, @Nullable String positionType, @Nullable Integer stampOrder, @Nullable Integer storyId) {
        StoryStampsBean res = new StoryStampsBean();
        res.mId = id;
        res.mStampId = stampId;
        res.mLink = link;
        res.mPoints = points;
        res.mOffset = offset;
        res.mType = type;
        res.mRotation = rotation;
        res.mScale = scale;
        res.mPositionType = positionType;
        res.mStampOrder = stampOrder;
        res.mStoryId = storyId;
        return res;
    }

    /**
     * Instantiate a new StoryStampsBean with all the values copied from the given model.
     */
    @NonNull
    public static StoryStampsBean copy(@NonNull StoryStampsModel from) {
        StoryStampsBean res = new StoryStampsBean();
        res.mId = from.getId();
        res.mStampId = from.getStampId();
        res.mLink = from.getLink();
        res.mPoints = from.getPoints();
        res.mOffset = from.getOffset();
        res.mType = from.getType();
        res.mRotation = from.getRotation();
        res.mScale = from.getScale();
        res.mPositionType = from.getPositionType();
        res.mStampOrder = from.getStampOrder();
        res.mStoryId = from.getStoryId();
        return res;
    }

    public static class Builder {
        private StoryStampsBean mRes = new StoryStampsBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Stamp id from server
         * Can be {@code null}.
         */
        public Builder stampId(@Nullable Integer stampId) {
            mRes.mStampId = stampId;
            return this;
        }

        /**
         * Image link
         * Can be {@code null}.
         */
        public Builder link(@Nullable String link) {
            mRes.mLink = link;
            return this;
        }

        /**
         * Control point for static stamps
         * Can be {@code null}.
         */
        public Builder points(@Nullable String points) {
            mRes.mPoints = points;
            return this;
        }

        /**
         * Offset for dynamic stamps
         * Can be {@code null}.
         */
        public Builder offset(@Nullable String offset) {
            mRes.mOffset = offset;
            return this;
        }

        /**
         * Stamp type
         * Can be {@code null}.
         */
        public Builder type(@Nullable String type) {
            mRes.mType = type;
            return this;
        }

        /**
         * Stamp rotation
         * Can be {@code null}.
         */
        public Builder rotation(@Nullable Integer rotation) {
            mRes.mRotation = rotation;
            return this;
        }

        /**
         * Stamp scale
         * Can be {@code null}.
         */
        public Builder scale(@Nullable Float scale) {
            mRes.mScale = scale;
            return this;
        }

        /**
         * Stamp position type
         * Can be {@code null}.
         */
        public Builder positionType(@Nullable String positionType) {
            mRes.mPositionType = positionType;
            return this;
        }

        /**
         * Stamp order for current story
         * Can be {@code null}.
         */
        public Builder stampOrder(@Nullable Integer stampOrder) {
            mRes.mStampOrder = stampOrder;
            return this;
        }

        /**
         * Story id
         * Can be {@code null}.
         */
        public Builder storyId(@Nullable Integer storyId) {
            mRes.mStoryId = storyId;
            return this;
        }

        /**
         * Get a new StoryStampsBean built with the given values.
         */
        public StoryStampsBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
