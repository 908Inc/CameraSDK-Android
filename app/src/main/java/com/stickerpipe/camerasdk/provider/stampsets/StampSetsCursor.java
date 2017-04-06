package com.stickerpipe.camerasdk.provider.stampsets;

// @formatter:off
import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code stamp_sets} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class StampSetsCursor extends AbstractCursor implements StampSetsModel {
    public StampSetsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(StampSetsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Content id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampsSetId() {
        Integer res = getIntegerOrNull(StampSetsColumns.STAMPS_SET_ID);
        return res;
    }
}
