package com.stickerpipe.camerasdk.provider.galleryitems;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stickerpipe.camerasdk.provider.base.AbstractSelection;

/**
 * Selection for the {@code gallery_items} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class GalleryItemsSelection extends AbstractSelection<GalleryItemsSelection> {
    @Override
    protected Uri baseUri() {
        return GalleryItemsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code GalleryItemsCursor} object, which is positioned before the first entry, or null.
     */
    public GalleryItemsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new GalleryItemsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public GalleryItemsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code GalleryItemsCursor} object, which is positioned before the first entry, or null.
     */
    public GalleryItemsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new GalleryItemsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public GalleryItemsCursor query(Context context) {
        return query(context, null);
    }


    public GalleryItemsSelection id(long... value) {
        addEquals("gallery_items." + GalleryItemsColumns._ID, toObjectArray(value));
        return this;
    }

    public GalleryItemsSelection idNot(long... value) {
        addNotEquals("gallery_items." + GalleryItemsColumns._ID, toObjectArray(value));
        return this;
    }

    public GalleryItemsSelection orderById(boolean desc) {
        orderBy("gallery_items." + GalleryItemsColumns._ID, desc);
        return this;
    }

    public GalleryItemsSelection orderById() {
        return orderById(false);
    }

    public GalleryItemsSelection itemId(Integer... value) {
        addEquals(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsSelection itemIdNot(Integer... value) {
        addNotEquals(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsSelection itemIdGt(int value) {
        addGreaterThan(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsSelection itemIdGtEq(int value) {
        addGreaterThanOrEquals(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsSelection itemIdLt(int value) {
        addLessThan(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsSelection itemIdLtEq(int value) {
        addLessThanOrEquals(GalleryItemsColumns.ITEM_ID, value);
        return this;
    }

    public GalleryItemsSelection orderByItemId(boolean desc) {
        orderBy(GalleryItemsColumns.ITEM_ID, desc);
        return this;
    }

    public GalleryItemsSelection orderByItemId() {
        orderBy(GalleryItemsColumns.ITEM_ID, false);
        return this;
    }

    public GalleryItemsSelection meta(String... value) {
        addEquals(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsSelection metaNot(String... value) {
        addNotEquals(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsSelection metaLike(String... value) {
        addLike(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsSelection metaContains(String... value) {
        addContains(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsSelection metaStartsWith(String... value) {
        addStartsWith(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsSelection metaEndsWith(String... value) {
        addEndsWith(GalleryItemsColumns.META, value);
        return this;
    }

    public GalleryItemsSelection orderByMeta(boolean desc) {
        orderBy(GalleryItemsColumns.META, desc);
        return this;
    }

    public GalleryItemsSelection orderByMeta() {
        orderBy(GalleryItemsColumns.META, false);
        return this;
    }

    public GalleryItemsSelection finalLink(String... value) {
        addEquals(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection finalLinkNot(String... value) {
        addNotEquals(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection finalLinkLike(String... value) {
        addLike(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection finalLinkContains(String... value) {
        addContains(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection finalLinkStartsWith(String... value) {
        addStartsWith(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection finalLinkEndsWith(String... value) {
        addEndsWith(GalleryItemsColumns.FINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection orderByFinalLink(boolean desc) {
        orderBy(GalleryItemsColumns.FINAL_LINK, desc);
        return this;
    }

    public GalleryItemsSelection orderByFinalLink() {
        orderBy(GalleryItemsColumns.FINAL_LINK, false);
        return this;
    }

    public GalleryItemsSelection originalLink(String... value) {
        addEquals(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection originalLinkNot(String... value) {
        addNotEquals(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection originalLinkLike(String... value) {
        addLike(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection originalLinkContains(String... value) {
        addContains(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection originalLinkStartsWith(String... value) {
        addStartsWith(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection originalLinkEndsWith(String... value) {
        addEndsWith(GalleryItemsColumns.ORIGINAL_LINK, value);
        return this;
    }

    public GalleryItemsSelection orderByOriginalLink(boolean desc) {
        orderBy(GalleryItemsColumns.ORIGINAL_LINK, desc);
        return this;
    }

    public GalleryItemsSelection orderByOriginalLink() {
        orderBy(GalleryItemsColumns.ORIGINAL_LINK, false);
        return this;
    }
}
