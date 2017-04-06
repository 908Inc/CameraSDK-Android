package com.stickerpipe.camerasdk.provider;

// @formatter:off
import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.stickerpipe.camerasdk.BuildConfig;
import com.stickerpipe.camerasdk.provider.base.BaseContentProvider;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsColumns;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;
import com.stickerpipe.camerasdk.provider.stories.StoriesColumns;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsColumns;

public class StampsProvider extends BaseContentProvider {
    private static final String TAG = StampsProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.stickerpipe.camerasdk.dataprovider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_STAMP_SETS = 0;
    private static final int URI_TYPE_STAMP_SETS_ID = 1;

    private static final int URI_TYPE_STAMPS = 2;
    private static final int URI_TYPE_STAMPS_ID = 3;

    private static final int URI_TYPE_STORIES = 4;
    private static final int URI_TYPE_STORIES_ID = 5;

    private static final int URI_TYPE_STORY_STAMPS = 6;
    private static final int URI_TYPE_STORY_STAMPS_ID = 7;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, StampSetsColumns.TABLE_NAME, URI_TYPE_STAMP_SETS);
        URI_MATCHER.addURI(AUTHORITY, StampSetsColumns.TABLE_NAME + "/#", URI_TYPE_STAMP_SETS_ID);
        URI_MATCHER.addURI(AUTHORITY, StampsColumns.TABLE_NAME, URI_TYPE_STAMPS);
        URI_MATCHER.addURI(AUTHORITY, StampsColumns.TABLE_NAME + "/#", URI_TYPE_STAMPS_ID);
        URI_MATCHER.addURI(AUTHORITY, StoriesColumns.TABLE_NAME, URI_TYPE_STORIES);
        URI_MATCHER.addURI(AUTHORITY, StoriesColumns.TABLE_NAME + "/#", URI_TYPE_STORIES_ID);
        URI_MATCHER.addURI(AUTHORITY, StoryStampsColumns.TABLE_NAME, URI_TYPE_STORY_STAMPS);
        URI_MATCHER.addURI(AUTHORITY, StoryStampsColumns.TABLE_NAME + "/#", URI_TYPE_STORY_STAMPS_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return StampsSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_STAMP_SETS:
                return TYPE_CURSOR_DIR + StampSetsColumns.TABLE_NAME;
            case URI_TYPE_STAMP_SETS_ID:
                return TYPE_CURSOR_ITEM + StampSetsColumns.TABLE_NAME;

            case URI_TYPE_STAMPS:
                return TYPE_CURSOR_DIR + StampsColumns.TABLE_NAME;
            case URI_TYPE_STAMPS_ID:
                return TYPE_CURSOR_ITEM + StampsColumns.TABLE_NAME;

            case URI_TYPE_STORIES:
                return TYPE_CURSOR_DIR + StoriesColumns.TABLE_NAME;
            case URI_TYPE_STORIES_ID:
                return TYPE_CURSOR_ITEM + StoriesColumns.TABLE_NAME;

            case URI_TYPE_STORY_STAMPS:
                return TYPE_CURSOR_DIR + StoryStampsColumns.TABLE_NAME;
            case URI_TYPE_STORY_STAMPS_ID:
                return TYPE_CURSOR_ITEM + StoryStampsColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_STAMP_SETS:
            case URI_TYPE_STAMP_SETS_ID:
                res.table = StampSetsColumns.TABLE_NAME;
                res.idColumn = StampSetsColumns._ID;
                res.tablesWithJoins = StampSetsColumns.TABLE_NAME;
                res.orderBy = StampSetsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STAMPS:
            case URI_TYPE_STAMPS_ID:
                res.table = StampsColumns.TABLE_NAME;
                res.idColumn = StampsColumns._ID;
                res.tablesWithJoins = StampsColumns.TABLE_NAME;
                res.orderBy = StampsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STORIES:
            case URI_TYPE_STORIES_ID:
                res.table = StoriesColumns.TABLE_NAME;
                res.idColumn = StoriesColumns._ID;
                res.tablesWithJoins = StoriesColumns.TABLE_NAME;
                res.orderBy = StoriesColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STORY_STAMPS:
            case URI_TYPE_STORY_STAMPS_ID:
                res.table = StoryStampsColumns.TABLE_NAME;
                res.idColumn = StoryStampsColumns._ID;
                res.tablesWithJoins = StoryStampsColumns.TABLE_NAME;
                res.orderBy = StoryStampsColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_STAMP_SETS_ID:
            case URI_TYPE_STAMPS_ID:
            case URI_TYPE_STORIES_ID:
            case URI_TYPE_STORY_STAMPS_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
