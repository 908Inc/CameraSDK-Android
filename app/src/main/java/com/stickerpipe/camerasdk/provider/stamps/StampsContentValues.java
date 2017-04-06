package com.stickerpipe.camerasdk.provider.stamps;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code stamps} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class StampsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StampsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StampsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable StampsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Content id from server
     */
    public StampsContentValues putStampId(@Nullable Integer value) {
        mContentValues.put(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsContentValues putStampIdNull() {
        mContentValues.putNull(StampsColumns.STAMP_ID);
        return this;
    }

    /**
     * Stamp image link
     */
    public StampsContentValues putLink(@Nullable String value) {
        mContentValues.put(StampsColumns.LINK, value);
        return this;
    }

    public StampsContentValues putLinkNull() {
        mContentValues.putNull(StampsColumns.LINK);
        return this;
    }

    /**
     * Stamps set id
     */
    public StampsContentValues putStampsSetId(@Nullable Integer value) {
        mContentValues.put(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsContentValues putStampsSetIdNull() {
        mContentValues.putNull(StampsColumns.STAMPS_SET_ID);
        return this;
    }
}
