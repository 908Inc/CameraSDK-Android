package com.stickerpipe.camerasdk.provider.stamps;

// @formatter:off
import android.net.Uri;
import android.provider.BaseColumns;

import com.stickerpipe.camerasdk.provider.StampsProvider;
import com.stickerpipe.camerasdk.provider.base.AbstractSelection;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsColumns;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;
import com.stickerpipe.camerasdk.provider.stories.StoriesColumns;
import com.stickerpipe.camerasdk.provider.storystamps.StoryStampsColumns;

/**
 * Stamps
 */
@SuppressWarnings("unused")
public class StampsColumns implements BaseColumns {
    public static final String TABLE_NAME = "stamps";
    public static final Uri CONTENT_URI = Uri.parse(StampsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Content id from server
     */
    public static final String STAMP_ID = "stamp_id";

    /**
     * Stamp image link
     */
    public static final String LINK = "link";

    /**
     * Stamps set id
     */
    public static final String STAMPS_SET_ID = "stamps_set_id";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STAMP_ID,
            LINK,
            STAMPS_SET_ID
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(STAMP_ID) || c.contains("." + STAMP_ID)) return true;
            if (c.equals(LINK) || c.contains("." + LINK)) return true;
            if (c.equals(STAMPS_SET_ID) || c.contains("." + STAMPS_SET_ID)) return true;
        }
        return false;
    }

}
