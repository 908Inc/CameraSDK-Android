package com.stickerpipe.camerasdk.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.stickerpipe.camerasdk.R;
import com.stickerpipe.camerasdk.ui.gesture.MyScaleGestureDetector;
import com.stickerpipe.camerasdk.ui.gesture.RotationGestureDetector;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class StampImageView extends android.support.v7.widget.AppCompatImageView implements RotationGestureDetector.OnRotationGestureListener, MyScaleGestureDetector.OnScaleGestureListener {

    private MyScaleGestureDetector mScaleGestureDetector;
    private RotationGestureDetector mRotationGestureDetector;
    private float mScaleFactor = 1.f;
    private float currentRotation;
    private float prevRotationAngle;
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;

    private OnStampViewActiveListener onViewActiveListener;
    private OnTouchListener externalTouchListener;
    private PointF downPoint = new PointF();
    private PointF startPoint = new PointF();
    private boolean isLocked;
    private int horizontalFlip = 1, verticalFlip = 1;
    private boolean useForceDown;
    private PorterDuffColorFilter selectedItemColorFilter = new PorterDuffColorFilter(0xffdddddd, PorterDuff.Mode.MULTIPLY);
    private int currentPointersCount;
    private Paint debugPaint;

    public StampImageView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mRotationGestureDetector = new RotationGestureDetector(this);
        mScaleGestureDetector = new MyScaleGestureDetector(context, this);
        debugPaint = new Paint();
        debugPaint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isLocked) return false;
        mRotationGestureDetector.onTouchEvent(ev);
        mScaleGestureDetector.onTouchEvent(ev);
        mScaleGestureDetector.setQuickScaleEnabled(false);

        if (externalTouchListener != null) {
            externalTouchListener.onTouch(this, ev);
        }
        currentPointersCount = ev.getPointerCount();
        int eventPointerId = MotionEventCompat.getPointerId(ev, MotionEventCompat.getActionIndex(ev));
        switch (ev.getActionMasked()) {
            case (MotionEvent.ACTION_MOVE):
                if (eventPointerId == mActivePointerId) {
                    PointF mv = new PointF(ev.getRawX() - downPoint.x, ev.getRawY() - downPoint.y);
                    setX((int) (startPoint.x + mv.x));
                    setY((int) (startPoint.y + mv.y));
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if (!useForceDown) return false;
                downPoint.x = ev.getRawX();
                downPoint.y = ev.getRawY();
                startPoint.x = getX();
                startPoint.y = getY();
                mActivePointerId = eventPointerId;
                if (onViewActiveListener != null) {
                    onViewActiveListener.onViewActive(this, true);
                }
                getDrawable().setColorFilter(selectedItemColorFilter);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                clearTouchEvents();
                getDrawable().setColorFilter(null);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (MotionEventCompat.getPointerId(ev, MotionEventCompat.getActionIndex(ev)) == mActivePointerId) {
                    clearTouchEvents();
                }
                break;
        }
        return true;

    }


    private void clearTouchEvents() {
        mActivePointerId = INVALID_POINTER_ID;
        prevRotationAngle = 0;
        if (onViewActiveListener != null) {
            onViewActiveListener.onViewActive(this, false);
        }
    }

    public boolean isCanBeDown(float x, float y) {
        Bitmap bitmap;
        if (getDrawable() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        } else if (getDrawable() instanceof GlideBitmapDrawable) {
            bitmap = ((GlideBitmapDrawable) getDrawable().getCurrent()).getBitmap();
        } else {
            return false;
        }
        // apply padding, rotation and calculate point at bitmap
        float realBitmapX = x - getPaddingLeft();
        float realBitmapY = y - getPaddingTop();
        float centerDeltaX = realBitmapX - bitmap.getWidth() / 2;
        float centerDeltaY = realBitmapY - bitmap.getHeight() / 2;
        float translatedRotation = currentRotation;
        if (translatedRotation < 0) translatedRotation += 360;
        double radianAngle = (translatedRotation) * Math.PI / 180;
        float rotatedDeltaX = (float) (centerDeltaY * Math.sin(radianAngle) + centerDeltaX * Math.cos(radianAngle));
        float rotatedDeltaY = (float) (centerDeltaY * Math.cos(radianAngle) - centerDeltaX * Math.sin(radianAngle));
        realBitmapX = rotatedDeltaX + bitmap.getWidth() / 2;
        realBitmapY = rotatedDeltaY + bitmap.getHeight() / 2;
        if (realBitmapX < 0 || realBitmapY < 0 || realBitmapX > bitmap.getWidth() || realBitmapY > bitmap.getHeight()) {
            return false;
        }
        // check for transparent pixel
        boolean result = false;
        try {
            result = ((bitmap.getPixel((int) realBitmapX, (int) realBitmapY) & 0xff000000) >> 24) != 0;
        } catch (IllegalArgumentException ex) {
            // in a case "y must be < bitmap.height()" etc.
        }
        return result;
    }

    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {
        setDrawingRotation(rotationDetector.getAngle(), true);
    }

    public void setDrawingRotation(float angle, boolean checkPointers) {
        float rotationDelta = prevRotationAngle - angle;
        if (checkPointers && (rotationDelta > 10 || rotationDelta < -10)) {
            // it means, that user change second pointer location and we don't want rotate image
            rotationDelta = 0;
        }
        currentRotation = currentRotation + rotationDelta;
        prevRotationAngle = angle;
        if (currentRotation >= 360) {
            currentRotation -= 360;
        } else if (currentRotation <= -360) {
            currentRotation += 360;
        }
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setScaleX(horizontalFlip * mScaleFactor);
        setScaleY(verticalFlip * mScaleFactor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.rotate(currentRotation, getWidth() / 2, getHeight() / 2);
//        canvas.drawLine(0,getHeight() / 2, getWidth(), getHeight() / 2, debugPaint);
//        canvas.drawLine(getWidth() / 2,0, getWidth() / 2, getHeight(), debugPaint);
        super.onDraw(canvas);
        canvas.restore();
        setTag(R.integer.stamp_rotation, currentRotation);
    }


    public void setUseForceDown(boolean useForceDown) {
        this.useForceDown = useForceDown;
    }

    @Override
    public boolean onScale(MyScaleGestureDetector detector) {
        if (currentPointersCount > 1) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.2f, Math.min(mScaleFactor, 20.0f));
            requestLayout();
        }
        return false;
    }

    public void setScale(float scale) {
        mScaleFactor = scale;
        requestLayout();
    }

    @Override
    public boolean onScaleBegin(MyScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(MyScaleGestureDetector detector) {

    }

    public float getScale() {
        return mScaleFactor;
    }


    public interface OnStampViewActiveListener {
        void onViewActive(StampImageView view, boolean isActive);
    }

    public void setOnViewActiveListener(OnStampViewActiveListener listener) {
        onViewActiveListener = listener;
    }

    public void setExternalOnTouchListener(OnTouchListener listener) {
        externalTouchListener = listener;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void flipImageHorizontal() {
        horizontalFlip *= -1;
        requestLayout();
    }

    public void flipImageVertical() {
        verticalFlip *= -1;
        requestLayout();
    }

    public int getHorizontalFlip() {
        return horizontalFlip;
    }

    public int getVerticalFlip() {
        return verticalFlip;
    }
}
