package com.stickerpipe.camerasdk.provider.storystamps;

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
 * Story stamps
 */
@SuppressWarnings("unused")
public class StoryStampsColumns implements BaseColumns {
    public static final String TABLE_NAME = "story_stamps";
    public static final Uri CONTENT_URI = Uri.parse(StampsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Stamp id from server
     */
    public static final String STAMP_ID = "stamp_id";

    /**
     * Image link
     */
    public static final String LINK = "link";

    /**
     * Control point for static stamps
     */
    public static final String POINTS = "points";

    /**
     * Offset for dynamic stamps
     */
    public static final String OFFSET = "offset";

    /**
     * Stamp type
     */
    public static final String TYPE = "type";

    /**
     * Stamp rotation
     */
    public static final String ROTATION = "rotation";

    /**
     * Stamp scale
     */
    public static final String SCALE = "scale";

    /**
     * Stamp position type
     */
    public static final String POSITION_TYPE = "position_type";

    /**
     * Stamp order for current story
     */
    public static final String STAMP_ORDER = "stamp_order";

    /**
     * Story id
     */
    public static final String STORY_ID = "story_id";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STAMP_ID,
            LINK,
            POINTS,
            OFFSET,
            TYPE,
            ROTATION,
            SCALE,
            POSITION_TYPE,
            STAMP_ORDER,
            STORY_ID
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(STAMP_ID) || c.contains("." + STAMP_ID)) return true;
            if (c.equals(LINK) || c.contains("." + LINK)) return true;
            if (c.equals(POINTS) || c.contains("." + POINTS)) return true;
            if (c.equals(OFFSET) || c.contains("." + OFFSET)) return true;
            if (c.equals(TYPE) || c.contains("." + TYPE)) return true;
            if (c.equals(ROTATION) || c.contains("." + ROTATION)) return true;
            if (c.equals(SCALE) || c.contains("." + SCALE)) return true;
            if (c.equals(POSITION_TYPE) || c.contains("." + POSITION_TYPE)) return true;
            if (c.equals(STAMP_ORDER) || c.contains("." + STAMP_ORDER)) return true;
            if (c.equals(STORY_ID) || c.contains("." + STORY_ID)) return true;
        }
        return false;
    }

}
