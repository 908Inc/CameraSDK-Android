package com.stickerpipe.camerasdk.provider.stamps;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Stamps
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface StampsModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Content id from server
     * Can be {@code null}.
     */
    @Nullable
    Integer getStampId();

    /**
     * Stamp image link
     * Can be {@code null}.
     */
    @Nullable
    String getLink();

    /**
     * Stamps set id
     * Can be {@code null}.
     */
    @Nullable
    Integer getStampsSetId();
}
