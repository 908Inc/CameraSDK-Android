package com.stickerpipe.camerasdk.provider.stampsets;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stickerpipe.camerasdk.provider.base.AbstractSelection;

/**
 * Selection for the {@code stamp_sets} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class StampSetsSelection extends AbstractSelection<StampSetsSelection> {
    @Override
    protected Uri baseUri() {
        return StampSetsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StampSetsCursor} object, which is positioned before the first entry, or null.
     */
    public StampSetsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StampSetsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public StampSetsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StampSetsCursor} object, which is positioned before the first entry, or null.
     */
    public StampSetsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StampSetsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public StampSetsCursor query(Context context) {
        return query(context, null);
    }


    public StampSetsSelection id(long... value) {
        addEquals("stamp_sets." + StampSetsColumns._ID, toObjectArray(value));
        return this;
    }

    public StampSetsSelection idNot(long... value) {
        addNotEquals("stamp_sets." + StampSetsColumns._ID, toObjectArray(value));
        return this;
    }

    public StampSetsSelection orderById(boolean desc) {
        orderBy("stamp_sets." + StampSetsColumns._ID, desc);
        return this;
    }

    public StampSetsSelection orderById() {
        return orderById(false);
    }

    public StampSetsSelection stampsSetId(Integer... value) {
        addEquals(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsSelection stampsSetIdNot(Integer... value) {
        addNotEquals(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsSelection stampsSetIdGt(int value) {
        addGreaterThan(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsSelection stampsSetIdGtEq(int value) {
        addGreaterThanOrEquals(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsSelection stampsSetIdLt(int value) {
        addLessThan(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsSelection stampsSetIdLtEq(int value) {
        addLessThanOrEquals(StampSetsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampSetsSelection orderByStampsSetId(boolean desc) {
        orderBy(StampSetsColumns.STAMPS_SET_ID, desc);
        return this;
    }

    public StampSetsSelection orderByStampsSetId() {
        orderBy(StampSetsColumns.STAMPS_SET_ID, false);
        return this;
    }
}
