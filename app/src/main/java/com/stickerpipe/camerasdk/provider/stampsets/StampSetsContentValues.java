package com.stickerpipe.camerasdk.provider.stampsets;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code stamp_sets} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class StampSetsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StampSetsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StampSetsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable StampSetsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Content id from server
     */
    public StampSetsContentValues putStampsSetId(@Nullable Integer value) {
        mContentValues.put(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsContentValues putStampsSetIdNull() {
        mContentValues.putNull(StampSetsColumns.STAMPS_SET_ID);
        return this;
    }
}
