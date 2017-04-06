package com.stickerpipe.camerasdk.provider.stories;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Stories
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface StoriesModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Story id from server
     * Can be {@code null}.
     */
    @Nullable
    Integer getStoryId();

    /**
     * Story data hash
     * Can be {@code null}.
     */
    @Nullable
    String getDataHash();

    /**
     * Story icon link
     * Can be {@code null}.
     */
    @Nullable
    String getIconLink();
}
