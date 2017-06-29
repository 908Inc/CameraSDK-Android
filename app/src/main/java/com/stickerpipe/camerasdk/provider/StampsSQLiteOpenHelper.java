package com.stickerpipe.camerasdk.provider;

// @formatter:off
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.stickerpipe.camerasdk.BuildConfig;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsColumns;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;
import com.stickerpipe.camerasdk.provider.stories.StoriesColumns;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsColumns;

public class StampsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = StampsSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "stamps.db";
    private static final int DATABASE_VERSION = 8;
    private static StampsSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final StampsSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    public static final String SQL_CREATE_TABLE_STAMP_SETS = "CREATE TABLE IF NOT EXISTS "
            + StampSetsColumns.TABLE_NAME + " ( "
            + StampSetsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StampSetsColumns.STAMPS_SET_ID + " INTEGER "
            + ", CONSTRAINT unique_action UNIQUE (stamps_set_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_STAMPS = "CREATE TABLE IF NOT EXISTS "
            + StampsColumns.TABLE_NAME + " ( "
            + StampsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StampsColumns.STAMP_ID + " INTEGER, "
            + StampsColumns.LINK + " TEXT, "
            + StampsColumns.STAMPS_SET_ID + " INTEGER "
            + ", CONSTRAINT unique_action UNIQUE (stamp_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_STORIES = "CREATE TABLE IF NOT EXISTS "
            + StoriesColumns.TABLE_NAME + " ( "
            + StoriesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StoriesColumns.STORY_ID + " INTEGER, "
            + StoriesColumns.DATA_HASH + " TEXT, "
            + StoriesColumns.ICON_LINK + " TEXT "
            + ", CONSTRAINT unique_action UNIQUE (story_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_TABLE_STORY_STAMPS = "CREATE TABLE IF NOT EXISTS "
            + StoryStampsColumns.TABLE_NAME + " ( "
            + StoryStampsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StoryStampsColumns.STAMP_ID + " INTEGER, "
            + StoryStampsColumns.LINK + " TEXT, "
            + StoryStampsColumns.POINTS + " TEXT, "
            + StoryStampsColumns.OFFSET + " TEXT, "
            + StoryStampsColumns.TYPE + " TEXT, "
            + StoryStampsColumns.ROTATION + " INTEGER, "
            + StoryStampsColumns.SCALE + " REAL, "
            + StoryStampsColumns.POSITION_TYPE + " TEXT, "
            + StoryStampsColumns.STAMP_ORDER + " INTEGER, "
            + StoryStampsColumns.STORY_ID + " INTEGER "
            + ", CONSTRAINT unique_action UNIQUE (stamp_id) ON CONFLICT REPLACE"
            + " );";


    public static StampsSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static StampsSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static StampsSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new StampsSQLiteOpenHelper(context);
    }

    private StampsSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new StampsSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static StampsSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new StampsSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private StampsSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new StampsSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_STAMP_SETS);
        db.execSQL(SQL_CREATE_TABLE_STAMPS);
        db.execSQL(SQL_CREATE_TABLE_STORIES);
        db.execSQL(SQL_CREATE_TABLE_STORY_STAMPS);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
