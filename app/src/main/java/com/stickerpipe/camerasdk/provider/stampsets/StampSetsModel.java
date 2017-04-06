package com.stickerpipe.camerasdk.provider.stampsets;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Set of stamps
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface StampSetsModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Content id from server
     * Can be {@code null}.
     */
    @Nullable
    Integer getStampsSetId();
}
