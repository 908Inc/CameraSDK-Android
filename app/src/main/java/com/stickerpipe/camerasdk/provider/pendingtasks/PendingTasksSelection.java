package com.stickerpipe.camerasdk.provider.pendingtasks;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stickerpipe.camerasdk.provider.base.AbstractSelection;

/**
 * Selection for the {@code pending_tasks} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class PendingTasksSelection extends AbstractSelection<PendingTasksSelection> {
    @Override
    protected Uri baseUri() {
        return PendingTasksColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PendingTasksCursor} object, which is positioned before the first entry, or null.
     */
    public PendingTasksCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PendingTasksCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PendingTasksCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PendingTasksCursor} object, which is positioned before the first entry, or null.
     */
    public PendingTasksCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PendingTasksCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PendingTasksCursor query(Context context) {
        return query(context, null);
    }


    public PendingTasksSelection id(long... value) {
        addEquals("pending_tasks." + PendingTasksColumns._ID, toObjectArray(value));
        return this;
    }

    public PendingTasksSelection idNot(long... value) {
        addNotEquals("pending_tasks." + PendingTasksColumns._ID, toObjectArray(value));
        return this;
    }

    public PendingTasksSelection orderById(boolean desc) {
        orderBy("pending_tasks." + PendingTasksColumns._ID, desc);
        return this;
    }

    public PendingTasksSelection orderById() {
        return orderById(false);
    }

    public PendingTasksSelection category(String... value) {
        addEquals(PendingTasksColumns.CATEGORY, value);
        return this;
    }

    public PendingTasksSelection categoryNot(String... value) {
        addNotEquals(PendingTasksColumns.CATEGORY, value);
        return this;
    }

    public PendingTasksSelection categoryLike(String... value) {
        addLike(PendingTasksColumns.CATEGORY, value);
        return this;
    }

    public PendingTasksSelection categoryContains(String... value) {
        addContains(PendingTasksColumns.CATEGORY, value);
        return this;
    }

    public PendingTasksSelection categoryStartsWith(String... value) {
        addStartsWith(PendingTasksColumns.CATEGORY, value);
        return this;
    }

    public PendingTasksSelection categoryEndsWith(String... value) {
        addEndsWith(PendingTasksColumns.CATEGORY, value);
        return this;
    }

    public PendingTasksSelection orderByCategory(boolean desc) {
        orderBy(PendingTasksColumns.CATEGORY, desc);
        return this;
    }

    public PendingTasksSelection orderByCategory() {
        orderBy(PendingTasksColumns.CATEGORY, false);
        return this;
    }

    public PendingTasksSelection action(String... value) {
        addEquals(PendingTasksColumns.ACTION, value);
        return this;
    }

    public PendingTasksSelection actionNot(String... value) {
        addNotEquals(PendingTasksColumns.ACTION, value);
        return this;
    }

    public PendingTasksSelection actionLike(String... value) {
        addLike(PendingTasksColumns.ACTION, value);
        return this;
    }

    public PendingTasksSelection actionContains(String... value) {
        addContains(PendingTasksColumns.ACTION, value);
        return this;
    }

    public PendingTasksSelection actionStartsWith(String... value) {
        addStartsWith(PendingTasksColumns.ACTION, value);
        return this;
    }

    public PendingTasksSelection actionEndsWith(String... value) {
        addEndsWith(PendingTasksColumns.ACTION, value);
        return this;
    }

    public PendingTasksSelection orderByAction(boolean desc) {
        orderBy(PendingTasksColumns.ACTION, desc);
        return this;
    }

    public PendingTasksSelection orderByAction() {
        orderBy(PendingTasksColumns.ACTION, false);
        return this;
    }

    public PendingTasksSelection value(String... value) {
        addEquals(PendingTasksColumns.VALUE, value);
        return this;
    }

    public PendingTasksSelection valueNot(String... value) {
        addNotEquals(PendingTasksColumns.VALUE, value);
        return this;
    }

    public PendingTasksSelection valueLike(String... value) {
        addLike(PendingTasksColumns.VALUE, value);
        return this;
    }

    public PendingTasksSelection valueContains(String... value) {
        addContains(PendingTasksColumns.VALUE, value);
        return this;
    }

    public PendingTasksSelection valueStartsWith(String... value) {
        addStartsWith(PendingTasksColumns.VALUE, value);
        return this;
    }

    public PendingTasksSelection valueEndsWith(String... value) {
        addEndsWith(PendingTasksColumns.VALUE, value);
        return this;
    }

    public PendingTasksSelection orderByValue(boolean desc) {
        orderBy(PendingTasksColumns.VALUE, desc);
        return this;
    }

    public PendingTasksSelection orderByValue() {
        orderBy(PendingTasksColumns.VALUE, false);
        return this;
    }

    public PendingTasksSelection ispending(Boolean value) {
        addEquals(PendingTasksColumns.ISPENDING, toObjectArray(value));
        return this;
    }

    public PendingTasksSelection orderByIspending(boolean desc) {
        orderBy(PendingTasksColumns.ISPENDING, desc);
        return this;
    }

    public PendingTasksSelection orderByIspending() {
        orderBy(PendingTasksColumns.ISPENDING, false);
        return this;
    }
}
