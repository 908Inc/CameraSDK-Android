package com.stickerpipe.camerasdk.provider.storystamps;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Story stamps
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface StoryStampsModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Stamp id from server
     * Can be {@code null}.
     */
    @Nullable
    Integer getStampId();

    /**
     * Image link
     * Can be {@code null}.
     */
    @Nullable
    String getLink();

    /**
     * Control points
     * Can be {@code null}.
     */
    @Nullable
    String getPoints();

    /**
     * Stamp type
     * Can be {@code null}.
     */
    @Nullable
    String getType();

    /**
     * Stamp rotation
     * Can be {@code null}.
     */
    @Nullable
    Integer getRotation();

    /**
     * Stamp scale
     * Can be {@code null}.
     */
    @Nullable
    Float getScale();

    /**
     * Stamp position type
     * Can be {@code null}.
     */
    @Nullable
    String getPositionType();

    /**
     * Stamp order for current story
     * Can be {@code null}.
     */
    @Nullable
    Integer getStampOrder();

    /**
     * Story id
     * Can be {@code null}.
     */
    @Nullable
    Integer getStoryId();
}
