package com.stickerpipe.camerasdk.provider.galleryitems;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Gallery items
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface GalleryItemsModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Item id from server
     * Can be {@code null}.
     */
    @Nullable
    Integer getItemId();

    /**
     * Meta information
     * Can be {@code null}.
     */
    @Nullable
    String getMeta();

    /**
     * Final image link
     * Can be {@code null}.
     */
    @Nullable
    String getFinalLink();

    /**
     * Original image link
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalLink();
}
