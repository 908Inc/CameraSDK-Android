package com.stickerpipe.camerasdk.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import com.stickerpipe.camerasdk.BuildConfig;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;

/**
 * Implement your custom database creation or upgrade code here.
 * <p>
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class StampsSQLiteOpenHelperCallbacks {
    private static final String TAG = StampsSQLiteOpenHelperCallbacks.class.getSimpleName();

    public void onOpen(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onOpen");
        // Insert your db open code here.
    }

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");
        // Insert your db creation code here. This is called after your tables are created.
    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        if (oldVersion < 3) {
            db.execSQL(StampsSQLiteOpenHelper.SQL_CREATE_TABLE_STAMPS);
        }
        if (oldVersion < 7) {
            db.execSQL("DROP TABLE IF EXISTS '" + StampsColumns.TABLE_NAME + "'");
            db.execSQL(StampsSQLiteOpenHelper.SQL_CREATE_TABLE_STAMPS);
            db.execSQL(StampsSQLiteOpenHelper.SQL_CREATE_TABLE_STAMP_SETS);
            db.execSQL(StampsSQLiteOpenHelper.SQL_CREATE_TABLE_STORIES);
            db.execSQL(StampsSQLiteOpenHelper.SQL_CREATE_TABLE_STORY_STAMPS);
        }
    }
}
