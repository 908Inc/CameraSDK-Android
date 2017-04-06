package com.stickerpipe.camerasdk.vision;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

import java.util.ArrayList;
import java.util.List;

/**
 * View which displays a bitmap containing a face1 along with overlay graphics that identify the
 * locations of detected facial landmarks.
 */
public class FaceView extends View {
    private SparseArray<Face> mFaces;
    private List<Lines> lines = new ArrayList<>();

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the bitmap background and the associated face1 detections.
     */
    public void setContent(SparseArray<Face> faces) {
        mFaces = faces;
        lines.clear();
        invalidate();
    }

    /**
     * Draws the bitmap background and the associated face1 landmarks.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mFaces != null)) {
            drawFaceAnnotations(canvas);
        }
    }

    /**
     * Draws a small circle for each detected landmark, centered at the detected landmark position.
     * <p>
     * <p>
     * Note that eye landmarks are defined to be the midpoint between the detected eye corner
     * positions, which tends to place the eye landmarks at the lower eyelid rather than at the
     * pupil position.
     */
    private void drawFaceAnnotations(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (int i = 0; i < mFaces.size(); ++i) {
            Face face = mFaces.valueAt(i);
            for (Landmark landmark : face.getLandmarks()) {
                int cx = (int) (landmark.getPosition().x);
                int cy = (int) (landmark.getPosition().y);
                canvas.drawCircle(cx, cy, 10, paint);
            }
            PointF p = face.getPosition();
            canvas.drawRect(p.x, p.y, p.x + face.getWidth(), p.y + face.getHeight(), paint);
        }
        for (Lines line : lines) {
            canvas.drawLine(line.leftPosition.x, line.leftPosition.y, line.rightPosition.x, line.rightPosition.y, paint);
            canvas.drawLine(line.stampLPoint.x, line.stampLPoint.y, line.stampRPoint.x, line.stampRPoint.y, paint);
        }
    }

    public void drawLine(PointF leftPosition, PointF rightPosition, PointF stampLPoint, PointF stampRPoint) {
        lines.add(new Lines(leftPosition, rightPosition, stampLPoint, stampRPoint));
        invalidate();
    }

    private class Lines {
        PointF leftPosition, rightPosition, stampLPoint, stampRPoint;

        public Lines(PointF leftPosition, PointF rightPosition, PointF stampLPoint, PointF stampRPoint) {
            this.leftPosition = leftPosition;
            this.rightPosition = rightPosition;
            this.stampLPoint = stampLPoint;
            this.stampRPoint = stampRPoint;
        }
    }
}