package com.stickerpipe.camerasdk.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.stickerpipe.camerasdk.R;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */
class DialogManager {
    static void showShareDialog(final Intent sharingIntent, final Context context) {
        final ShareListAdapter listAdapter = new ShareListAdapter(sharingIntent, context);
        final AlertDialog shareDialog = new AlertDialog.Builder(context)
                .setAdapter(listAdapter, (dialog, which) -> {
                    ResolveInfo info = listAdapter.getItem(which);
                    sharingIntent.setPackage(info.activityInfo.packageName);
                    context.startActivity(sharingIntent);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(true)
                .setTitle(R.string.share_to)
                .create();
        shareDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        shareDialog.setOnShowListener(dialog -> shareDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(context, R.color.colorPrimary)));
        shareDialog.show();
    }

    static void showExitEditModeDialog(Context context, DialogInterface.OnClickListener positiveCallback) {
        new AlertDialog.Builder(context)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(android.R.string.yes, positiveCallback)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss()).create().show();
    }
}
