package com.stickerpipe.camerasdk.provider.stories;

// @formatter:off
import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code stories} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class StoriesCursor extends AbstractCursor implements StoriesModel {
    public StoriesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(StoriesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Story id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getStoryId() {
        Integer res = getIntegerOrNull(StoriesColumns.STORY_ID);
        return res;
    }

    /**
     * Story data hash
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getDataHash() {
        String res = getStringOrNull(StoriesColumns.DATA_HASH);
        return res;
    }

    /**
     * Story icon link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getIconLink() {
        String res = getStringOrNull(StoriesColumns.ICON_LINK);
        return res;
    }
}
