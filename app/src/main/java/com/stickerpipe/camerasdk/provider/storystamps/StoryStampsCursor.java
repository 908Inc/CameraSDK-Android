package com.stickerpipe.camerasdk.provider.storystamps;

// @formatter:off
import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code story_stamps} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class StoryStampsCursor extends AbstractCursor implements StoryStampsModel {
    public StoryStampsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(StoryStampsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Stamp id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampId() {
        Integer res = getIntegerOrNull(StoryStampsColumns.STAMP_ID);
        return res;
    }

    /**
     * Image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getLink() {
        String res = getStringOrNull(StoryStampsColumns.LINK);
        return res;
    }

    /**
     * Control point for static stamps
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getPoints() {
        String res = getStringOrNull(StoryStampsColumns.POINTS);
        return res;
    }

    /**
     * Offset for dynamic stamps
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getOffset() {
        String res = getStringOrNull(StoryStampsColumns.OFFSET);
        return res;
    }

    /**
     * Stamp type
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getType() {
        String res = getStringOrNull(StoryStampsColumns.TYPE);
        return res;
    }

    /**
     * Stamp rotation
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getRotation() {
        Integer res = getIntegerOrNull(StoryStampsColumns.ROTATION);
        return res;
    }

    /**
     * Stamp scale
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Float getScale() {
        Float res = getFloatOrNull(StoryStampsColumns.SCALE);
        return res;
    }

    /**
     * Stamp position type
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getPositionType() {
        String res = getStringOrNull(StoryStampsColumns.POSITION_TYPE);
        return res;
    }

    /**
     * Stamp order for current story
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampOrder() {
        Integer res = getIntegerOrNull(StoryStampsColumns.STAMP_ORDER);
        return res;
    }

    /**
     * Story id
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStoryId() {
        Integer res = getIntegerOrNull(StoryStampsColumns.STORY_ID);
        return res;
    }
}
