package com.stickerpipe.camerasdk.provider.stories;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code stories} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class StoriesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StoriesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StoriesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable StoriesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Story id from server
     */
    public StoriesContentValues putStoryId(@Nullable Integer value) {
        mContentValues.put(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesContentValues putStoryIdNull() {
        mContentValues.putNull(StoriesColumns.STORY_ID);
        return this;
    }

    /**
     * Story data hash
     */
    public StoriesContentValues putDataHash(@Nullable String value) {
        mContentValues.put(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesContentValues putDataHashNull() {
        mContentValues.putNull(StoriesColumns.DATA_HASH);
        return this;
    }

    /**
     * Story icon link
     */
    public StoriesContentValues putIconLink(@Nullable String value) {
        mContentValues.put(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesContentValues putIconLinkNull() {
        mContentValues.putNull(StoriesColumns.ICON_LINK);
        return this;
    }
}
