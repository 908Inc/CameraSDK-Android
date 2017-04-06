package com.stickerpipe.camerasdk.provider.stories;

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
 * Stories
 */
@SuppressWarnings("unused")
public class StoriesColumns implements BaseColumns {
    public static final String TABLE_NAME = "stories";
    public static final Uri CONTENT_URI = Uri.parse(StampsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Story id from server
     */
    public static final String STORY_ID = "story_id";

    /**
     * Story data hash
     */
    public static final String DATA_HASH = "data_hash";

    /**
     * Story icon link
     */
    public static final String ICON_LINK = "icon_link";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            STORY_ID,
            DATA_HASH,
            ICON_LINK
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(STORY_ID) || c.contains("." + STORY_ID)) return true;
            if (c.equals(DATA_HASH) || c.contains("." + DATA_HASH)) return true;
            if (c.equals(ICON_LINK) || c.contains("." + ICON_LINK)) return true;
        }
        return false;
    }

}
