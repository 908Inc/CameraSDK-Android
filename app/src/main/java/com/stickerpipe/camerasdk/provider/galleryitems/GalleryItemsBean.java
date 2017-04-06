package com.stickerpipe.camerasdk.provider.galleryitems;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Gallery items
 */
@SuppressWarnings({"WeakerAccess", "unused", "ConstantConditions"})
public class GalleryItemsBean implements GalleryItemsModel {
    private long mId;
    private Integer mItemId;
    private String mMeta;
    private String mFinalLink;
    private String mOriginalLink;

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
     * Item id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getItemId() {
        return mItemId;
    }

    /**
     * Item id from server
     * Can be {@code null}.
     */
    public void setItemId(@Nullable Integer itemId) {
        mItemId = itemId;
    }

    /**
     * Meta information
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getMeta() {
        return mMeta;
    }

    /**
     * Meta information
     * Can be {@code null}.
     */
    public void setMeta(@Nullable String meta) {
        mMeta = meta;
    }

    /**
     * Final image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getFinalLink() {
        return mFinalLink;
    }

    /**
     * Final image link
     * Can be {@code null}.
     */
    public void setFinalLink(@Nullable String finalLink) {
        mFinalLink = finalLink;
    }

    /**
     * Original image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getOriginalLink() {
        return mOriginalLink;
    }

    /**
     * Original image link
     * Can be {@code null}.
     */
    public void setOriginalLink(@Nullable String originalLink) {
        mOriginalLink = originalLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GalleryItemsBean bean = (GalleryItemsBean) o;
        return mId == bean.mId;
    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }

    /**
     * Instantiate a new GalleryItemsBean with specified values.
     */
    @NonNull
    public static GalleryItemsBean newInstance(long id, @Nullable Integer itemId, @Nullable String meta, @Nullable String finalLink, @Nullable String originalLink) {
        GalleryItemsBean res = new GalleryItemsBean();
        res.mId = id;
        res.mItemId = itemId;
        res.mMeta = meta;
        res.mFinalLink = finalLink;
        res.mOriginalLink = originalLink;
        return res;
    }

    /**
     * Instantiate a new GalleryItemsBean with all the values copied from the given model.
     */
    @NonNull
    public static GalleryItemsBean copy(@NonNull GalleryItemsModel from) {
        GalleryItemsBean res = new GalleryItemsBean();
        res.mId = from.getId();
        res.mItemId = from.getItemId();
        res.mMeta = from.getMeta();
        res.mFinalLink = from.getFinalLink();
        res.mOriginalLink = from.getOriginalLink();
        return res;
    }

    public static class Builder {
        private GalleryItemsBean mRes = new GalleryItemsBean();

        /**
         * Primary key.
         */
        public Builder id(long id) {
            mRes.mId = id;
            return this;
        }

        /**
         * Item id from server
         * Can be {@code null}.
         */
        public Builder itemId(@Nullable Integer itemId) {
            mRes.mItemId = itemId;
            return this;
        }

        /**
         * Meta information
         * Can be {@code null}.
         */
        public Builder meta(@Nullable String meta) {
            mRes.mMeta = meta;
            return this;
        }

        /**
         * Final image link
         * Can be {@code null}.
         */
        public Builder finalLink(@Nullable String finalLink) {
            mRes.mFinalLink = finalLink;
            return this;
        }

        /**
         * Original image link
         * Can be {@code null}.
         */
        public Builder originalLink(@Nullable String originalLink) {
            mRes.mOriginalLink = originalLink;
            return this;
        }

        /**
         * Get a new GalleryItemsBean built with the given values.
         */
        public GalleryItemsBean build() {
            return mRes;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
