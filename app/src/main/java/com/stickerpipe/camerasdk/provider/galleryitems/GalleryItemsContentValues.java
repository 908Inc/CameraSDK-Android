package com.stickerpipe.camerasdk.provider.galleryitems;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stickerpipe.camerasdk.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code gallery_items} table.
 */
@SuppressWarnings({"ConstantConditions", "unused"})
public class GalleryItemsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return GalleryItemsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable GalleryItemsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The context to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable GalleryItemsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Item id from server
     */
    public GalleryItemsContentValues putItemId(@Nullable Integer value) {
        mContentValues.put(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsContentValues putItemIdNull() {
        mContentValues.putNull(GalleryItemsColumns.ITEM_ID);
        return this;
    }

    /**
     * Meta information
     */
    public GalleryItemsContentValues putMeta(@Nullable String value) {
        mContentValues.put(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsContentValues putMetaNull() {
        mContentValues.putNull(GalleryItemsColumns.META);
        return this;
    }

    /**
     * Final image link
     */
    public GalleryItemsContentValues putFinalLink(@Nullable String value) {
        mContentValues.put(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsContentValues putFinalLinkNull() {
        mContentValues.putNull(GalleryItemsColumns.FINAL_LINK);
        return this;
    }

    /**
     * Original image link
     */
    public GalleryItemsContentValues putOriginalLink(@Nullable String value) {
        mContentValues.put(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsContentValues putOriginalLinkNull() {
        mContentValues.putNull(GalleryItemsColumns.ORIGINAL_LINK);
        return this;
    }
}
