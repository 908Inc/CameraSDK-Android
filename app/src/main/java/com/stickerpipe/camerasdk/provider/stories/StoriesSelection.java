package com.stickerpipe.camerasdk.provider.stories;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stickerpipe.camerasdk.provider.base.AbstractSelection;

/**
 * Selection for the {@code stories} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class StoriesSelection extends AbstractSelection<StoriesSelection> {
    @Override
    protected Uri baseUri() {
        return StoriesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StoriesCursor} object, which is positioned before the first entry, or null.
     */
    public StoriesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StoriesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public StoriesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StoriesCursor} object, which is positioned before the first entry, or null.
     */
    public StoriesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StoriesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public StoriesCursor query(Context context) {
        return query(context, null);
    }


    public StoriesSelection id(long... value) {
        addEquals("stories." + StoriesColumns._ID, toObjectArray(value));
        return this;
    }

    public StoriesSelection idNot(long... value) {
        addNotEquals("stories." + StoriesColumns._ID, toObjectArray(value));
        return this;
    }

    public StoriesSelection orderById(boolean desc) {
        orderBy("stories." + StoriesColumns._ID, desc);
        return this;
    }

    public StoriesSelection orderById() {
        return orderById(false);
    }

    public StoriesSelection storyId(Integer... value) {
        addEquals(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesSelection storyIdNot(Integer... value) {
        addNotEquals(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesSelection storyIdGt(int value) {
        addGreaterThan(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesSelection storyIdGtEq(int value) {
        addGreaterThanOrEquals(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesSelection storyIdLt(int value) {
        addLessThan(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesSelection storyIdLtEq(int value) {
        addLessThanOrEquals(StoriesColumns.STORY_ID, value);
        return this;
    }

    public StoriesSelection orderByStoryId(boolean desc) {
        orderBy(StoriesColumns.STORY_ID, desc);
        return this;
    }

    public StoriesSelection orderByStoryId() {
        orderBy(StoriesColumns.STORY_ID, false);
        return this;
    }

    public StoriesSelection dataHash(String... value) {
        addEquals(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesSelection dataHashNot(String... value) {
        addNotEquals(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesSelection dataHashLike(String... value) {
        addLike(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesSelection dataHashContains(String... value) {
        addContains(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesSelection dataHashStartsWith(String... value) {
        addStartsWith(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesSelection dataHashEndsWith(String... value) {
        addEndsWith(StoriesColumns.DATA_HASH, value);
        return this;
    }

    public StoriesSelection orderByDataHash(boolean desc) {
        orderBy(StoriesColumns.DATA_HASH, desc);
        return this;
    }

    public StoriesSelection orderByDataHash() {
        orderBy(StoriesColumns.DATA_HASH, false);
        return this;
    }

    public StoriesSelection iconLink(String... value) {
        addEquals(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesSelection iconLinkNot(String... value) {
        addNotEquals(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesSelection iconLinkLike(String... value) {
        addLike(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesSelection iconLinkContains(String... value) {
        addContains(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesSelection iconLinkStartsWith(String... value) {
        addStartsWith(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesSelection iconLinkEndsWith(String... value) {
        addEndsWith(StoriesColumns.ICON_LINK, value);
        return this;
    }

    public StoriesSelection orderByIconLink(boolean desc) {
        orderBy(StoriesColumns.ICON_LINK, desc);
        return this;
    }

    public StoriesSelection orderByIconLink() {
        orderBy(StoriesColumns.ICON_LINK, false);
        return this;
    }
}
