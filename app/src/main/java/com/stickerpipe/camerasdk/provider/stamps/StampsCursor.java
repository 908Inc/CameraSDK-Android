package com.stickerpipe.camerasdk.provider.stamps;

// @formatter:off
import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code stamps} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class StampsCursor extends AbstractCursor implements StampsModel {
    public StampsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(StampsColumns._ID);
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
    public Integer getStampId() {
        Integer res = getIntegerOrNull(StampsColumns.STAMP_ID);
        return res;
    }

    /**
     * Stamp image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getLink() {
        String res = getStringOrNull(StampsColumns.LINK);
        return res;
    }

    /**
     * Stamps set id
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStampsSetId() {
        Integer res = getIntegerOrNull(StampsColumns.STAMPS_SET_ID);
        return res;
    }
}
