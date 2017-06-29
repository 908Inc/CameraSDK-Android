package com.stickerpipe.camerasdk.provider.storystamps;

// @formatter:off
import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.stickerpipe.camerasdk.provider.base.AbstractSelection;

/**
 * Selection for the {@code story_stamps} table.
 */
@SuppressWarnings({"unused", "WeakerAccess", "Recycle"})
public class StoryStampsSelection extends AbstractSelection<StoryStampsSelection> {
    @Override
    protected Uri baseUri() {
        return StoryStampsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StoryStampsCursor} object, which is positioned before the first entry, or null.
     */
    public StoryStampsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StoryStampsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public StoryStampsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StoryStampsCursor} object, which is positioned before the first entry, or null.
     */
    public StoryStampsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StoryStampsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public StoryStampsCursor query(Context context) {
        return query(context, null);
    }


    public StoryStampsSelection id(long... value) {
        addEquals("story_stamps." + StoryStampsColumns._ID, toObjectArray(value));
        return this;
    }

    public StoryStampsSelection idNot(long... value) {
        addNotEquals("story_stamps." + StoryStampsColumns._ID, toObjectArray(value));
        return this;
    }

    public StoryStampsSelection orderById(boolean desc) {
        orderBy("story_stamps." + StoryStampsColumns._ID, desc);
        return this;
    }

    public StoryStampsSelection orderById() {
        return orderById(false);
    }

    public StoryStampsSelection stampId(Integer... value) {
        addEquals(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsSelection stampIdNot(Integer... value) {
        addNotEquals(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsSelection stampIdGt(int value) {
        addGreaterThan(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsSelection stampIdGtEq(int value) {
        addGreaterThanOrEquals(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsSelection stampIdLt(int value) {
        addLessThan(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsSelection stampIdLtEq(int value) {
        addLessThanOrEquals(StoryStampsColumns.STAMP_ID, value);
        return this;
    }

    public StoryStampsSelection orderByStampId(boolean desc) {
        orderBy(StoryStampsColumns.STAMP_ID, desc);
        return this;
    }

    public StoryStampsSelection orderByStampId() {
        orderBy(StoryStampsColumns.STAMP_ID, false);
        return this;
    }

    public StoryStampsSelection link(String... value) {
        addEquals(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsSelection linkNot(String... value) {
        addNotEquals(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsSelection linkLike(String... value) {
        addLike(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsSelection linkContains(String... value) {
        addContains(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsSelection linkStartsWith(String... value) {
        addStartsWith(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsSelection linkEndsWith(String... value) {
        addEndsWith(StoryStampsColumns.LINK, value);
        return this;
    }

    public StoryStampsSelection orderByLink(boolean desc) {
        orderBy(StoryStampsColumns.LINK, desc);
        return this;
    }

    public StoryStampsSelection orderByLink() {
        orderBy(StoryStampsColumns.LINK, false);
        return this;
    }

    public StoryStampsSelection points(String... value) {
        addEquals(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsSelection pointsNot(String... value) {
        addNotEquals(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsSelection pointsLike(String... value) {
        addLike(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsSelection pointsContains(String... value) {
        addContains(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsSelection pointsStartsWith(String... value) {
        addStartsWith(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsSelection pointsEndsWith(String... value) {
        addEndsWith(StoryStampsColumns.POINTS, value);
        return this;
    }

    public StoryStampsSelection orderByPoints(boolean desc) {
        orderBy(StoryStampsColumns.POINTS, desc);
        return this;
    }

    public StoryStampsSelection orderByPoints() {
        orderBy(StoryStampsColumns.POINTS, false);
        return this;
    }

    public StoryStampsSelection offset(String... value) {
        addEquals(StoryStampsColumns.OFFSET, value);
        return this;
    }

    public StoryStampsSelection offsetNot(String... value) {
        addNotEquals(StoryStampsColumns.OFFSET, value);
        return this;
    }

    public StoryStampsSelection offsetLike(String... value) {
        addLike(StoryStampsColumns.OFFSET, value);
        return this;
    }

    public StoryStampsSelection offsetContains(String... value) {
        addContains(StoryStampsColumns.OFFSET, value);
        return this;
    }

    public StoryStampsSelection offsetStartsWith(String... value) {
        addStartsWith(StoryStampsColumns.OFFSET, value);
        return this;
    }

    public StoryStampsSelection offsetEndsWith(String... value) {
        addEndsWith(StoryStampsColumns.OFFSET, value);
        return this;
    }

    public StoryStampsSelection orderByOffset(boolean desc) {
        orderBy(StoryStampsColumns.OFFSET, desc);
        return this;
    }

    public StoryStampsSelection orderByOffset() {
        orderBy(StoryStampsColumns.OFFSET, false);
        return this;
    }

    public StoryStampsSelection type(String... value) {
        addEquals(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsSelection typeNot(String... value) {
        addNotEquals(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsSelection typeLike(String... value) {
        addLike(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsSelection typeContains(String... value) {
        addContains(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsSelection typeStartsWith(String... value) {
        addStartsWith(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsSelection typeEndsWith(String... value) {
        addEndsWith(StoryStampsColumns.TYPE, value);
        return this;
    }

    public StoryStampsSelection orderByType(boolean desc) {
        orderBy(StoryStampsColumns.TYPE, desc);
        return this;
    }

    public StoryStampsSelection orderByType() {
        orderBy(StoryStampsColumns.TYPE, false);
        return this;
    }

    public StoryStampsSelection rotation(Integer... value) {
        addEquals(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsSelection rotationNot(Integer... value) {
        addNotEquals(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsSelection rotationGt(int value) {
        addGreaterThan(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsSelection rotationGtEq(int value) {
        addGreaterThanOrEquals(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsSelection rotationLt(int value) {
        addLessThan(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsSelection rotationLtEq(int value) {
        addLessThanOrEquals(StoryStampsColumns.ROTATION, value);
        return this;
    }

    public StoryStampsSelection orderByRotation(boolean desc) {
        orderBy(StoryStampsColumns.ROTATION, desc);
        return this;
    }

    public StoryStampsSelection orderByRotation() {
        orderBy(StoryStampsColumns.ROTATION, false);
        return this;
    }

    public StoryStampsSelection scale(Float... value) {
        addEquals(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsSelection scaleNot(Float... value) {
        addNotEquals(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsSelection scaleGt(float value) {
        addGreaterThan(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsSelection scaleGtEq(float value) {
        addGreaterThanOrEquals(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsSelection scaleLt(float value) {
        addLessThan(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsSelection scaleLtEq(float value) {
        addLessThanOrEquals(StoryStampsColumns.SCALE, value);
        return this;
    }

    public StoryStampsSelection orderByScale(boolean desc) {
        orderBy(StoryStampsColumns.SCALE, desc);
        return this;
    }

    public StoryStampsSelection orderByScale() {
        orderBy(StoryStampsColumns.SCALE, false);
        return this;
    }

    public StoryStampsSelection positionType(String... value) {
        addEquals(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsSelection positionTypeNot(String... value) {
        addNotEquals(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsSelection positionTypeLike(String... value) {
        addLike(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsSelection positionTypeContains(String... value) {
        addContains(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsSelection positionTypeStartsWith(String... value) {
        addStartsWith(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsSelection positionTypeEndsWith(String... value) {
        addEndsWith(StoryStampsColumns.POSITION_TYPE, value);
        return this;
    }

    public StoryStampsSelection orderByPositionType(boolean desc) {
        orderBy(StoryStampsColumns.POSITION_TYPE, desc);
        return this;
    }

    public StoryStampsSelection orderByPositionType() {
        orderBy(StoryStampsColumns.POSITION_TYPE, false);
        return this;
    }

    public StoryStampsSelection stampOrder(Integer... value) {
        addEquals(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsSelection stampOrderNot(Integer... value) {
        addNotEquals(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsSelection stampOrderGt(int value) {
        addGreaterThan(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsSelection stampOrderGtEq(int value) {
        addGreaterThanOrEquals(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsSelection stampOrderLt(int value) {
        addLessThan(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsSelection stampOrderLtEq(int value) {
        addLessThanOrEquals(StoryStampsColumns.STAMP_ORDER, value);
        return this;
    }

    public StoryStampsSelection orderByStampOrder(boolean desc) {
        orderBy(StoryStampsColumns.STAMP_ORDER, desc);
        return this;
    }

    public StoryStampsSelection orderByStampOrder() {
        orderBy(StoryStampsColumns.STAMP_ORDER, false);
        return this;
    }

    public StoryStampsSelection storyId(Integer... value) {
        addEquals(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsSelection storyIdNot(Integer... value) {
        addNotEquals(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsSelection storyIdGt(int value) {
        addGreaterThan(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsSelection storyIdGtEq(int value) {
        addGreaterThanOrEquals(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsSelection storyIdLt(int value) {
        addLessThan(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsSelection storyIdLtEq(int value) {
        addLessThanOrEquals(StoryStampsColumns.STORY_ID, value);
        return this;
    }

    public StoryStampsSelection orderByStoryId(boolean desc) {
        orderBy(StoryStampsColumns.STORY_ID, desc);
        return this;
    }

    public StoryStampsSelection orderByStoryId() {
        orderBy(StoryStampsColumns.STORY_ID, false);
        return this;
    }
}
