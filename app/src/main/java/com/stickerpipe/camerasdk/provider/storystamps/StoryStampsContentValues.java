package com.stickerpipe.camerasdk.provider.storystamps;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code story_stamps} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class StoryStampsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StoryStampsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StoryStampsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable StoryStampsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Stamp id from server
     */
    public StoryStampsContentValues putStampId(@Nullable Integer value) {
        mContentValues.put(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsContentValues putStampIdNull() {
        mContentValues.putNull(StoryStampsColumns.STAMP_ID);
        return this;
    }

    /**
     * Image link
     */
    public StoryStampsContentValues putLink(@Nullable String value) {
        mContentValues.put(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsContentValues putLinkNull() {
        mContentValues.putNull(StoryStampsColumns.LINK);
        return this;
    }

    /**
     * Control points
     */
    public StoryStampsContentValues putPoints(@Nullable String value) {
        mContentValues.put(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsContentValues putPointsNull() {
        mContentValues.putNull(StoryStampsColumns.POINTS);
        return this;
    }

    /**
     * Stamp type
     */
    public StoryStampsContentValues putType(@Nullable String value) {
        mContentValues.put(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsContentValues putTypeNull() {
        mContentValues.putNull(StoryStampsColumns.TYPE);
        return this;
    }

    /**
     * Stamp rotation
     */
    public StoryStampsContentValues putRotation(@Nullable Integer value) {
        mContentValues.put(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsContentValues putRotationNull() {
        mContentValues.putNull(StoryStampsColumns.ROTATION);
        return this;
    }

    /**
     * Stamp scale
     */
    public StoryStampsContentValues putScale(@Nullable Float value) {
        mContentValues.put(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsContentValues putScaleNull() {
        mContentValues.putNull(StoryStampsColumns.SCALE);
        return this;
    }

    /**
     * Stamp position type
     */
    public StoryStampsContentValues putPositionType(@Nullable String value) {
        mContentValues.put(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsContentValues putPositionTypeNull() {
        mContentValues.putNull(StoryStampsColumns.POSITION_TYPE);
        return this;
    }

    /**
     * Stamp order for current story
     */
    public StoryStampsContentValues putStampOrder(@Nullable Integer value) {
        mContentValues.put(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsContentValues putStampOrderNull() {
        mContentValues.putNull(StoryStampsColumns.STAMP_ORDER);
        return this;
    }

    /**
     * Story id
     */
    public StoryStampsContentValues putStoryId(@Nullable Integer value) {
        mContentValues.put(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsContentValues putStoryIdNull() {
        mContentValues.putNull(StoryStampsColumns.STORY_ID);
        return this;
    }
}
