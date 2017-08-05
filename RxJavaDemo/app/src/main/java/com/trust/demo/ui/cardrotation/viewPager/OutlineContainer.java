package com.trust.demo.ui.cardrotation.viewPager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;


public class OutlineContainer extends FrameLayout implements Animatable {

    private Paint mOutlinePaint;

    private boolean mIsRunning = false;
    private long mStartTime;
    private float mAlpha = 1.0f;
    private static final long ANIMATION_DURATION = 500;
    private static final long FRAME_DURATION = 1000 / 60;
    private final Interpolator mInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t + 1.0f;
        }
    };

    public OutlineContainer(Context context) {
        super(context);
        init();
    }

    public OutlineContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OutlineContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mOutlinePaint = new Paint();
        mOutlinePaint.setAntiAlias(true);
        mOutlinePaint.setStrokeWidth(dpToPx(2,getResources()));
        mOutlinePaint.setColor(Color.parseColor("#FF33E5B5"));
        mOutlinePaint.setStyle(Style.STROKE);

        int padding = dpToPx(10,getResources());
        setPadding(padding, padding, padding, padding);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int offset = dpToPx(5,getResources());
        if (mOutlinePaint.getColor() != MsgViewPager.sOutlineColor) {
            mOutlinePaint.setColor(MsgViewPager.sOutlineColor);
        }
        mOutlinePaint.setAlpha((int) (mAlpha * 255));
        Rect rect = new Rect(offset, offset, getMeasuredWidth() - offset, getMeasuredHeight() - offset);
        canvas.drawRect(rect, mOutlinePaint);
    }

    public void setOutlineAlpha(float alpha) {
        mAlpha = alpha;
    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    @Override
    public void start() {
        if (mIsRunning)
            return;
        mIsRunning = true;
        mStartTime = AnimationUtils.currentAnimationTimeMillis();
        post(mUpdater);
    }

    @Override
    public void stop() {
        if (!mIsRunning)
            return;
        mIsRunning = false;
    }

    private final Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            long duration = now - mStartTime;
            if (duration >= ANIMATION_DURATION) {
                mAlpha = 0.0f;
                invalidate();
                stop();
                return;
            } else {
                mAlpha = mInterpolator.getInterpolation(1 - duration / (float) ANIMATION_DURATION);
                invalidate();
            }
            postDelayed(mUpdater, FRAME_DURATION);
        }
    };

    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}
