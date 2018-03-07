package edu.washington.cs.ubicomplab.rdt_reader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;


/**
 * Created by cjparkuw on 3/6/2018.
 */

public class Viewport extends ViewGroup {
    private float area;

    public Viewport(Context context) {
        super(context);
    }

    public Viewport(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Viewport(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Viewport, 0, 0);
        area = ta.getFloat(R.styleable.Viewport_area, 0.25f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int viewportMargin = 32;
        int viewportCornerRadius = 8;
        Paint eraser = new Paint();
        eraser.setAntiAlias(true);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        float scale = (float)Math.sqrt(area);

        float width = ((float)getWidth())*scale;
        float height = ((float)getHeight())*scale;
        float x = (getWidth() - width)/2;
        float y = (getHeight() - height)/2;
        RectF rect = new RectF(x, y, x+width, y+height);
        RectF frame = new RectF(x-2, y-2, x+width+4, y+height+4);
        Path path = new Path();
        Paint stroke = new Paint();
        stroke.setAntiAlias(true);
        stroke.setStrokeWidth(4);
        stroke.setColor(Color.WHITE);
        stroke.setStyle(Paint.Style.STROKE);
        path.addRoundRect(frame, (float) viewportCornerRadius, (float) viewportCornerRadius, Path.Direction.CW);
        canvas.drawPath(path, stroke);
        canvas.drawRoundRect(rect, (float) viewportCornerRadius, (float) viewportCornerRadius, eraser);

    }
}
