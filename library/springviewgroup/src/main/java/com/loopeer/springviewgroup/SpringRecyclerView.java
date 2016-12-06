package com.loopeer.springviewgroup;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import java.lang.reflect.Field;

public class SpringRecyclerView extends RecyclerView {

    private static final float SCALE_FACTOR = 0.1f;
    private static final int SCALE_TIME = 450;
    private static final float MAX_VELOCITY = 20000;

    private int mMaxLength;
    private LinearLayoutManager mLayoutManager;
    private boolean mDoAnimation;
    private VelocityTracker mVelocityTracker;
    private float downY, moveY, mVelocity;

    private float mPivotX, mPivotY, mScaleX = 1.0f, mScaleY = 1.0f;

    public SpringRecyclerView(Context context) {
        this(context, null);
    }

    public SpringRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.scale(mScaleX, mScaleY, mPivotX, mPivotY);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (mLayoutManager == null) {
            mLayoutManager = (LinearLayoutManager) getLayoutManager();
        }
        if (mMaxLength == 0) {
            mMaxLength = getHeight() / 3 * 2;
        }
        mVelocityTracker.addMovement(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = e.getY();

                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mLayoutManager.getItemCount() - 1
                        && moveY < downY && downY - moveY < mMaxLength && !mDoAnimation) {

                    if (mLayoutManager.findLastCompletelyVisibleItemPosition() -
                            mLayoutManager.findFirstCompletelyVisibleItemPosition() ==
                            mLayoutManager.getItemCount() - 1) {
                        //RV一屏完全显示所有Item，向上滑动时做压缩效果
                        mScaleY = 1.0f - (downY - moveY) / mMaxLength * SCALE_FACTOR;
                        mPivotY = getTop();
                    } else {
                        mScaleY = (downY - moveY) / mMaxLength * SCALE_FACTOR + 1.0f;
                        mPivotY = getBottom();
                    }

                    if (Math.abs(mScaleY - 1.0f) < 0.01f)
                        mScaleY = 1.0f;
                    mPivotX = (getRight() - getLeft()) / 2;

                    invalidate();
                }
                if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
                        && moveY > downY && moveY - downY < mMaxLength && !mDoAnimation) {
                    mScaleY = (moveY - downY) / mMaxLength * SCALE_FACTOR + 1.0f;
                    if (mScaleY - 1.0f < 0.01f)
                        mScaleY = 1.0f;
                    mPivotX = (getRight() - getLeft()) / 2;
                    mPivotY = getTop();

                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isScaled() && !mDoAnimation) {
                    backToNoScale(isExpand());
                }
                downY = moveY = 0.0f;
                break;
        }

        return isScaled() || super.dispatchTouchEvent(e);
    }

    private void backToNoScale(boolean isExpand) {
        doScaleAnimation(false, !isExpand || moveY > downY, getCanvasScale(), 1.0f + (isExpand ? -0.02f : 0.02f), 1.0f);
    }

    private void flingScale(boolean isHeader, float scale) {
        doScaleAnimation(true, isHeader, 1.0f, scale, 0.99f, 1.0f);
    }

    private boolean isExpand() {
        return getCanvasScale() > 1.0f;
    }

    private boolean isScaled() {
        return getCanvasScale() != 1.0f;
    }

    private float getCanvasScale() {
        return mScaleY;
    }

    private void doScaleAnimation(boolean isFling, boolean isHeader, float... values) {
        ValueAnimator animator = ValueAnimator.ofFloat(values);
        animator.setDuration(isFling ? 2 * SCALE_TIME : SCALE_TIME);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mScaleY = (float) valueAnimator.getAnimatedValue();

                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mDoAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDoAnimation = false;
                mScaleY = 1.0f;

                changeRVScrollState();
                //用于结束动画后将RV中的mScrollState置为SCROLL_STATE_IDLE，不然将会出现动画结束后第一次点击条目失效的Bug
            }
        });
        mPivotX = (getRight() - getLeft()) / 2;
        mPivotY = isHeader ? getTop() : getBottom();
        animator.start();
    }

    private void changeRVScrollState() {
        Class<?> clazz = getClass().getSuperclass();
        try {
            Field mScrollState = clazz.getDeclaredField("mScrollState");
            mScrollState.setAccessible(true);
            mScrollState.set(this, RecyclerView.SCROLL_STATE_IDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0 ||
                    mLayoutManager.findLastCompletelyVisibleItemPosition() == mLayoutManager.getItemCount() - 1)) {
                mVelocityTracker.computeCurrentVelocity(1000);
                mVelocity = Math.abs(mVelocityTracker.getYVelocity());
                if (mVelocity > MAX_VELOCITY) {
                    mVelocity = MAX_VELOCITY;
                }
                float scale = mVelocity / 1000 / 20 * SCALE_FACTOR / 3 + 1.0f;

                if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    flingScale(true, scale);
                }
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == mLayoutManager.getItemCount() - 1) {
                    flingScale(false, scale);
                }
            }
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnScrollListener(mOnScrollListener);
        mVelocityTracker.recycle();
    }
}
