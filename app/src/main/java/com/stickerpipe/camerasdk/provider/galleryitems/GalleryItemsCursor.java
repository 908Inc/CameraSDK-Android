package com.stickerpipe.camerasdk.provider.galleryitems;

// @formatter:off
import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code gallery_items} table.
 */
@SuppressWarnings({"WeakerAccess", "unused", "UnnecessaryLocalVariable"})
public class GalleryItemsCursor extends AbstractCursor implements GalleryItemsModel {
    public GalleryItemsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    @Override
    public long getId() {
        Long res = getLongOrNull(GalleryItemsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Item id from server
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public Integer getItemId() {
        Integer res = getIntegerOrNull(GalleryItemsColumns.ITEM_ID);
        return res;
    }

    /**
     * Meta information
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getMeta() {
        String res = getStringOrNull(GalleryItemsColumns.META);
        return res;
    }

    /**
     * Final image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getFinalLink() {
        String res = getStringOrNull(GalleryItemsColumns.FINAL_LINK);
        return res;
    }

    /**
     * Original image link
     * Can be {@code null}.
     */
    @Nullable
    @Override
    public String getOriginalLink() {
        String res = getStringOrNull(GalleryItemsColumns.ORIGINAL_LINK);
        return res;
    }
}
