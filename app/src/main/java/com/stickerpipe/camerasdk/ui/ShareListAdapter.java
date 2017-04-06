package com.stickerpipe.camerasdk.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stickerpipe.camerasdk.Constants;
import com.stickerpipe.camerasdk.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */
public class ShareListAdapter extends BaseAdapter {

    private static final String[] WHITE_LIST_APPS = {
            Constants.APP_WAHTSAPP,
            Constants.APP_FACEBOOK_MESSANGER,
            Constants.APP_LINE,
            Constants.APP_VIBER,
            Constants.APP_TELEGRAM,
            Constants.APP_FACEBOOK,
            Constants.APP_TWITTER,
            Constants.APP_SNAPCHAT,
            Constants.APP_INSTAGRAMM,
    };

    private final Context mContext;
    private List<ResolveInfo> items = new ArrayList<>();

    public ShareListAdapter(Intent sharingIntent, Context context) {
        mContext = context;
        List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(sharingIntent, 0);
        Map<String, ResolveInfo> unsortedItems = new LinkedHashMap<>();
        for (ResolveInfo rInfo : activities) {
            unsortedItems.put(rInfo.activityInfo.packageName, rInfo);
        }
        for (String whiteListApp : WHITE_LIST_APPS) {
            if (unsortedItems.containsKey(whiteListApp)) {
                items.add(unsortedItems.get(whiteListApp));
                unsortedItems.remove(whiteListApp);
            }
        }
        items.addAll(unsortedItems.values());
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ResolveInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_share, null);
            viewHolder = new ViewHolder();
            viewHolder.iconView = (ImageView) convertView.findViewById(android.R.id.icon);
            viewHolder.labelView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ResolveInfo item = getItem(position);
        viewHolder.iconView.setImageDrawable(item.activityInfo.loadIcon(mContext.getPackageManager()));
        viewHolder.labelView.setText(item.activityInfo.loadLabel(mContext.getPackageManager()));
        return convertView;
    }

    private class ViewHolder {
        ImageView iconView;
        TextView labelView;
    }
}
