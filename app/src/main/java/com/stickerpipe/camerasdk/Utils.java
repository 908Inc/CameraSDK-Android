package com.stickerpipe.camerasdk;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.stickerpipe.camerasdk.ui.view.StampImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class Utils {

    private static Point size;
    public static AtomicInteger atomicInteger = new AtomicInteger(1000);


    public static boolean copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean copyFile(File src, File dst) {
        FileInputStream in;
        try {
            in = new FileInputStream(src);
        } catch (FileNotFoundException e) {
            return false;
        }
        return copyInputStreamToFile(in, dst);
    }

    public static void compressBitmapToFile(Bitmap bitmap, File file, int quality) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
        out.flush();
        out.close();
    }

    public static String implode(String separator, List<String> data) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (String s : data) {
            sb.append(prefix);
            sb.append(s);
            prefix = separator;
        }
        return sb.toString();
    }

    /**
     * Get density sting representation
     *
     * @param context Context
     * @return Density name
     */
    public static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 3.0) {
            return "xxhdpi";
        } else if (density >= 2.0) {
            return "xhdpi";
        } else if (density >= 1.5) {
            return "hdpi";
        } else {
            return "mdpi";
        }
    }

    /**
     * Generate uniq device ID
     *
     * @param context Utils context
     * @return Device ID
     */
    public static String getDeviceId(Context context) {
        String deviceId = StorageManager.getInstance().getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            String tmDevice = "", tmSerial = "", androidId = "";
            try {
                tmSerial = "" + Build.SERIAL;
                tmDevice = "" + Build.DEVICE;
                androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            } catch (Exception ignored) {
            }
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            deviceId = deviceUuid.toString();
            StorageManager.getInstance().storeDeviceId(deviceId);
        }
        return deviceId;
    }

    /**
     * Check, is any network connection available
     *
     * @param context Util context
     * @return Result of inspection
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * Create md5 hashed string from input string
     *
     * @param str Input string
     * @return Hashed string
     */
    public static String md5(final String str) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Convert density independent pixels to pixels
     *
     * @param val     Value to convert
     * @param context Context
     * @return Size in pixels
     */
    public static int dp(int val, Context context) {
        return (int) (context.getResources().getDisplayMetrics().density * val);
    }

    /**
     * Get screen physical width in pixels according to orientation
     *
     * @return Screen width
     */
    public static int getScreenWidthInPx(Context context) {
        return getScreenSideSize(true, context);
    }

    /**
     * Get screen physical height in pixels according to orientation
     *
     * @return Screen width
     */
    public static int getScreenHeightInPx(Context context) {
        return getScreenSideSize(false, context);
    }

    private static int getScreenSideSize(boolean isWidth, Context context) {
        if (size == null) {
            calculateScreenSize(context);
        }
        switch (getCurrentOrientation(context)) {
            case Configuration.ORIENTATION_LANDSCAPE:
                return isWidth ? size.y : size.x;
            case Configuration.ORIENTATION_PORTRAIT:
            default:
                return isWidth ? size.x : size.y;
        }
    }

    /**
     * Calculate screen size
     */
    private static void calculateScreenSize(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        size = new Point();
        switch (getCurrentOrientation(context)) {
            case Configuration.ORIENTATION_LANDSCAPE:
                size.y = display.getWidth();
                size.x = display.getHeight();
                break;
            case Configuration.ORIENTATION_PORTRAIT:
            default:
                size.x = display.getWidth();
                size.y = display.getHeight();
        }
    }

    /**
     * Return current screen orientation
     *
     * @return {@link Configuration#ORIENTATION_LANDSCAPE} or Configuration#ORIENTATION_LANDSCAPE
     */
    public static int getCurrentOrientation(Context context) {
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Activity.WINDOW_SERVICE);
        int rotation = wm.getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_270 || rotation == Surface.ROTATION_90) {
            return Configuration.ORIENTATION_LANDSCAPE;
        } else {
            return Configuration.ORIENTATION_PORTRAIT;
        }
    }



    public final static int KEYS_BLEND_DARKEN = 1;
    public final static int KEYS_BLEND_MULTIPLY = 2;
    public static final int KEYS_BLEND_ADD = 3;
    public static final int KEYS_BLEND_DESOLVE = 4;
    public static final int KEYS_BLEND_DESOLVE_LIGHTEN = 5;
    public static final int KEYS_BLEND_DESOLVE_OVERLAY = 6;
    public static final int KEYS_BLEND_DESOLVE_SCREEN = 7;



    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);
        source.recycle();
        return dest;
    }


    private static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        source.recycle();
        return bitmap;
    }

    /**
     * Determines if given points are inside view
     *
     * @param x    - x coordinate of point
     * @param y    - y coordinate of point
     * @param view - view object to compare
     * @return true if the points are within view bounds, false otherwise
     */
    public static boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        int horizontalFlip = 1;
        int verticalFlip = 1;
        if (view instanceof StampImageView) {
            StampImageView stampView = (StampImageView) view;
            horizontalFlip = stampView.getHorizontalFlip();
            verticalFlip = stampView.getVerticalFlip();
            if (horizontalFlip == -1) {
                viewX = (int) (viewX + view.getWidth() * view.getScaleX());
            }
            if (verticalFlip == -1) {
                viewY = (int) (viewY + view.getHeight() * view.getScaleY());
            }
        }
        //point is inside view bounds
        return (x > viewX && x < (viewX + view.getWidth() * view.getScaleX() * horizontalFlip)) &&
                (y > viewY && y < (viewY + view.getHeight() * view.getScaleY() * verticalFlip));
    }

    public static Bitmap imageOrientationValidator(Bitmap bitmap, String path) {
        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException ignored) {
        }

        return bitmap;
    }

    /**
     * Calculate the optimal size of camera preview
     */
    public static Camera.Size getOptimalCameraSize(List<Camera.Size> sizes, int w, int h) {

        final double ASPECT_TOLERANCE = 0.2;
        double targetRatio = (double) h / w;
        if (sizes == null)
            return null;
        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            // camera rotation is 90, so change ration calculation
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                continue;
            if (Math.abs(size.width - h) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.width - h);
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - h) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - h);
                }
            }
        }
        return optimalSize;
    }

    public static Bitmap blurRenderScript(Context context, Bitmap originalBitmap, int radius) {
        Bitmap bitmap = Bitmap.createBitmap(
                originalBitmap.getWidth(), originalBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, originalBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    public static Bitmap applyBlend(Bitmap bm1, Bitmap bm2, int value) {
        Bitmap newBitmap;
        int w;
        if (bm1.getWidth() >= bm2.getWidth()) {
            w = bm1.getWidth();
        } else {
            w = bm2.getWidth();
        }

        int h;
        if (bm1.getHeight() >= bm2.getHeight()) {
            h = bm1.getHeight();
        } else {
            h = bm2.getHeight();
        }

        Bitmap.Config config = bm1.getConfig();
        if (config == null) {
            config = Bitmap.Config.ARGB_8888;
        }


        newBitmap = Bitmap.createBitmap(w, h, config);
        Canvas newCanvas = new Canvas(newBitmap);

        newCanvas.drawBitmap(bm1, 0, 0, null);

        Paint paint = new Paint();
        switch (value) {
            case KEYS_BLEND_DARKEN:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
                break;
            case KEYS_BLEND_MULTIPLY:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
                break;
            case KEYS_BLEND_ADD:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                break;
            case KEYS_BLEND_DESOLVE:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
                break;
            case KEYS_BLEND_DESOLVE_LIGHTEN:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
                break;
            case KEYS_BLEND_DESOLVE_OVERLAY:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
                break;
            case KEYS_BLEND_DESOLVE_SCREEN:
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
                break;


            default:
                break;
        }

        paint.setShader(new BitmapShader(bm2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        newCanvas.drawRect(0, 0, bm2.getWidth(), bm2.getHeight(), paint);

        return newBitmap;
    }

    /**
     * store image file on media store provider
     *
     * @param filePath Path to file
     * @param context  Context
     */
    public static void addImageToGallery(final String filePath, final Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * Finds a specific landmark position, or approximates the position based on past observations
     * if it is not present.
     */

    public static PointF getLandmarkPosition(Face face, int landmarkId) {
        for (Landmark landmark : face.getLandmarks()) {
            if (landmark.getType() == landmarkId) {
                return landmark.getPosition();
            }
        }
        return null;
    }

}
