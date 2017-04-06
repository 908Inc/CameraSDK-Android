package com.stickerpipe.camerasdk.provider.galleryitems;

// @formatter:off
import android.net.Uri;
import android.provider.BaseColumns;

import com.stickerpipe.camerasdk.provider.StampsProvider;
import com.stickerpipe.camerasdk.provider.base.AbstractSelection;
import com.stickerpipe.camerasdk.provider.galleryitems.GalleryItemsColumns;
import com.stickerpipe.camerasdk.provider.pendingtasks.PendingTasksColumns;
import com.stickerpipe.camerasdk.provider.stampsets.StampSetsColumns;
import com.stickerpipe.camerasdk.provider.stamps.StampsColumns;

/**
 * Gallery items
 */
@SuppressWarnings("unused")
public class GalleryItemsColumns implements BaseColumns {
    public static final String TABLE_NAME = "gallery_items";
    public static final Uri CONTENT_URI = Uri.parse(StampsProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Item id from server
     */
    public static final String ITEM_ID = "item_id";

    /**
     * Meta information
     */
    public static final String META = "meta";

    /**
     * Final image link
     */
    public static final String FINAL_LINK = "final_link";

    /**
     * Original image link
     */
    public static final String ORIGINAL_LINK = "original_link";


    public static final String DEFAULT_ORDER = null;

    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ITEM_ID,
            META,
            FINAL_LINK,
            ORIGINAL_LINK
    };

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ITEM_ID) || c.contains("." + ITEM_ID)) return true;
            if (c.equals(META) || c.contains("." + META)) return true;
            if (c.equals(FINAL_LINK) || c.contains("." + FINAL_LINK)) return true;
            if (c.equals(ORIGINAL_LINK) || c.contains("." + ORIGINAL_LINK)) return true;
        }
        return false;
    }

}
