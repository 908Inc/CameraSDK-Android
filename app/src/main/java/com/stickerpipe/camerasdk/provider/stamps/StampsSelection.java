package com.stickerpipe.camerasdk.provider.stamps;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stickerpipe.camerasdk.provider.base.AbstractSelection;

/**
 * Selection for the {@code stamps} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class StampsSelection extends AbstractSelection<StampsSelection> {
    @Override
    protected Uri baseUri() {
        return StampsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StampsCursor} object, which is positioned before the first entry, or null.
     */
    public StampsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StampsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public StampsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StampsCursor} object, which is positioned before the first entry, or null.
     */
    public StampsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StampsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public StampsCursor query(Context context) {
        return query(context, null);
    }


    public StampsSelection id(long... value) {
        addEquals("stamps." + StampsColumns._ID, toObjectArray(value));
        return this;
    }

    public StampsSelection idNot(long... value) {
        addNotEquals("stamps." + StampsColumns._ID, toObjectArray(value));
        return this;
    }

    public StampsSelection orderById(boolean desc) {
        orderBy("stamps." + StampsColumns._ID, desc);
        return this;
    }

    public StampsSelection orderById() {
        return orderById(false);
    }

    public StampsSelection stampId(Integer... value) {
        addEquals(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsSelection stampIdNot(Integer... value) {
        addNotEquals(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsSelection stampIdGt(int value) {
        addGreaterThan(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsSelection stampIdGtEq(int value) {
        addGreaterThanOrEquals(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsSelection stampIdLt(int value) {
        addLessThan(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsSelection stampIdLtEq(int value) {
        addLessThanOrEquals(StampsColumns.STAMP_ID, value);
        return this;
    }

    public StampsSelection orderByStampId(boolean desc) {
        orderBy(StampsColumns.STAMP_ID, desc);
        return this;
    }

    public StampsSelection orderByStampId() {
        orderBy(StampsColumns.STAMP_ID, false);
        return this;
    }

    public StampsSelection link(String... value) {
        addEquals(StampsColumns.LINK, value);
        return this;
    }

    public StampsSelection linkNot(String... value) {
        addNotEquals(StampsColumns.LINK, value);
        return this;
    }

    public StampsSelection linkLike(String... value) {
        addLike(StampsColumns.LINK, value);
        return this;
    }

    public StampsSelection linkContains(String... value) {
        addContains(StampsColumns.LINK, value);
        return this;
    }

    public StampsSelection linkStartsWith(String... value) {
        addStartsWith(StampsColumns.LINK, value);
        return this;
    }

    public StampsSelection linkEndsWith(String... value) {
        addEndsWith(StampsColumns.LINK, value);
        return this;
    }

    public StampsSelection orderByLink(boolean desc) {
        orderBy(StampsColumns.LINK, desc);
        return this;
    }

    public StampsSelection orderByLink() {
        orderBy(StampsColumns.LINK, false);
        return this;
    }

    public StampsSelection stampsSetId(Integer... value) {
        addEquals(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsSelection stampsSetIdNot(Integer... value) {
        addNotEquals(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsSelection stampsSetIdGt(int value) {
        addGreaterThan(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsSelection stampsSetIdGtEq(int value) {
        addGreaterThanOrEquals(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsSelection stampsSetIdLt(int value) {
        addLessThan(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsSelection stampsSetIdLtEq(int value) {
        addLessThanOrEquals(StampsColumns.STAMPS_SET_ID, value);
        return this;
    }

    public StampsSelection orderByStampsSetId(boolean desc) {
        orderBy(StampsColumns.STAMPS_SET_ID, desc);
        return this;
    }

    public StampsSelection orderByStampsSetId() {
        orderBy(StampsColumns.STAMPS_SET_ID, false);
        return this;
    }
}
