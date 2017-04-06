package com.stickerpipe.camerasdk.provider.pendingtasks;

// @formatter:off
import com.stickerpipe.camerasdk.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Pending tasks
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public interface PendingTasksModel extends BaseModel {

    /**
     * Primary key.
     */
    long getId();

    /**
     * Task category
     * Can be {@code null}.
     */
    @Nullable
    String getCategory();

    /**
     * Task action
     * Can be {@code null}.
     */
    @Nullable
    String getAction();

    /**
     * Task value
     * Can be {@code null}.
     */
    @Nullable
    String getValue();

    /**
     * Is task waiting for execution
     * Can be {@code null}.
     */
    @Nullable
    Boolean getIspending();
}
