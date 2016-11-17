package com.loopeer.flowlayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class FlowLayout extends HorizontalScrollView {

    public static final int MODE_SCROLLABLE = 0;
    public static final int MODE_FIXED = 1;

    public static final int LINE_HIDE_POSITION_NONE = 0;
    public static final int LINE_HIDE_POSITION_BOTH = 1;
    public static final int LINE_HIDE_POSITION_START = 2;
    public static final int LINE_HIDE_POSITION_END = 3;

    private final LinearLayout mFlowItemStrip;

    ColorStateList mLineColors;
    ColorStateList mWidgetColors;
    ColorStateList mContentTextColors;
    boolean mShowLine;
    int mMode;
    int mLineHidePosition;
    int mWidgetPadding;
    int mWidgetSize;
    int mLineHeight;
    float mContentTextSize;

    private int mTabMaxWidth;

    int mFlowItemCount;

    private FlowAdapter mFlowAdapter;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        mFlowItemStrip = new LinearLayout(context);
        super.addView(mFlowItemStrip, 0, new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);
        mLineColors = array.getColorStateList(R.styleable.FlowLayout_flowLineColor);
        mWidgetColors = array.getColorStateList(R.styleable.FlowLayout_flowWidgetColor);
        mContentTextColors = array.getColorStateList(R.styleable.FlowLayout_flowContentTextColor);
        mShowLine = array.getBoolean(R.styleable.FlowLayout_flowShowLine, false);
        mMode = array.getInt(R.styleable.FlowLayout_flowMode, MODE_FIXED);
        mLineHidePosition = array.getInt(R.styleable.FlowLayout_flowLineHidePosition, LINE_HIDE_POSITION_BOTH);
        mWidgetPadding = array.getDimensionPixelSize(R.styleable.FlowLayout_flowWidgetPadding, 0);
        mWidgetSize = array.getDimensionPixelSize(R.styleable.FlowLayout_flowWidgetSize, 0);
        mLineHeight = array.getDimensionPixelSize(R.styleable.FlowLayout_flowLineHeight, 0);
        mContentTextSize = array.getDimension(R.styleable.FlowLayout_flowContentTextSize, dpToPx(14));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 1) {
            final View childView = getChildAt(0);
            boolean remeasure = false;
            switch (mMode) {
                case MODE_FIXED:
                    remeasure = childView.getMeasuredWidth() != getMeasuredWidth();
                    break;
                case MODE_SCROLLABLE:
                    remeasure = childView.getMeasuredWidth() < getMeasuredWidth();
                    break;
            }
            if (remeasure) {
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop()
                        + getPaddingBottom(), childView.getLayoutParams().height);
                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                        getMeasuredWidth(), MeasureSpec.EXACTLY);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    public void setAdapter(FlowAdapter flowAdapter) {
        mFlowAdapter = flowAdapter;
        updateViews();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ((mTabMaxWidth == 0 || mTabMaxWidth != w) && mFlowAdapter != null) {
            mTabMaxWidth = w;
            updateViews();
        }
    }

    public void updateViews() {
        if (mTabMaxWidth == 0)
            return;

        mFlowItemStrip.removeAllViews();
        mFlowItemCount = mFlowAdapter.getItemCount();
        for (int i = 0; i < mFlowItemCount; i++) {
            LinearLayout containerLayout = new LinearLayout(getContext());
            containerLayout.setOrientation(LinearLayout.VERTICAL);
            FlowItemView flowItemView = new FlowItemView(getContext(), i < mFlowAdapter.getSelectedCount(), i);
            flowItemView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            containerLayout.addView(flowItemView);
            if (mFlowAdapter.getItemView(i) != null) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_HORIZONTAL;
                containerLayout.addView(mFlowAdapter.getItemView(i), params);
                mFlowAdapter.getItemView(i).setSelected(i < mFlowAdapter.getSelectedCount());
            }
            mFlowItemStrip.addView(containerLayout, flowItemView.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    class FlowItemView extends View {

        Paint mWidgetPaint;
        Paint mLinePaint;
        Paint mContentTextPaint;

        int mActualWidgetSize;
        int mPosition;

        public FlowItemView(Context context, boolean selected, int position) {
            super(context);
            setWillNotDraw(false);

            mPosition = position;
            mWidgetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setStrokeWidth(mLineHeight);
            mContentTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mContentTextPaint.setTextSize(mContentTextSize);
            mContentTextPaint.setTextAlign(Paint.Align.CENTER);
            setSelected(selected);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            switch (mMode) {
                case MODE_FIXED:
                    int fixedWidth;
                    if (mFlowItemCount == 0)
                        fixedWidth = mTabMaxWidth;
                    else
                        fixedWidth = mTabMaxWidth / mFlowItemCount;
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(fixedWidth, MeasureSpec.EXACTLY);

                    mActualWidgetSize = fixedWidth - mWidgetPadding * 2;
                    break;
                case MODE_SCROLLABLE:
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(mWidgetSize + mWidgetPadding * 2,
                            MeasureSpec.EXACTLY);

                    mActualWidgetSize = mWidgetSize;
                    break;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mActualWidgetSize, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(mWidgetPadding + mActualWidgetSize / 2, mActualWidgetSize / 2,
                    mActualWidgetSize / 2, mWidgetPaint);
            drawText(canvas);
            if (mShowLine)
                drawLine(canvas);
        }

        private void drawText(Canvas canvas) {
            String content = mFlowAdapter.getContents()[mPosition];
            if (TextUtils.isEmpty(content))
                return;
            Rect rect = new Rect();
            mContentTextPaint.getTextBounds(content, 0, content.length(), rect);
            canvas.drawText(content, mWidgetPadding + mActualWidgetSize / 2
                    , mActualWidgetSize / 2 + rect.height() / 2, mContentTextPaint);
        }

        private void drawLine(Canvas canvas) {
            //draw start
            if (isStart()) {
                switch (mLineHidePosition) {
                    case LINE_HIDE_POSITION_BOTH:
                        //ignore draw
                        break;
                    case LINE_HIDE_POSITION_NONE:
                        drawStartLine(canvas);
                        break;
                    case LINE_HIDE_POSITION_START:
                        //ignore draw
                        break;
                    case LINE_HIDE_POSITION_END:
                        drawStartLine(canvas);
                        break;
                }
            } else {
                drawStartLine(canvas);
            }
            //draw end
            if (isEnd()) {
                switch (mLineHidePosition) {
                    case LINE_HIDE_POSITION_BOTH:
                        //ignore draw
                        break;
                    case LINE_HIDE_POSITION_NONE:
                        drawEndLine(canvas);
                        break;
                    case LINE_HIDE_POSITION_START:
                        drawEndLine(canvas);
                        break;
                    case LINE_HIDE_POSITION_END:
                        //ignore draw
                        break;
                }
            } else {
                if (mPosition == mFlowAdapter.getSelectedCount() - 1)
                    mLinePaint.setColor(mLineColors.getDefaultColor());
                drawEndLine(canvas);
            }
        }

        private void drawStartLine(Canvas canvas) {
            canvas.drawLine(0, mActualWidgetSize / 2, mWidgetPadding, mActualWidgetSize / 2, mLinePaint);
        }

        private void drawEndLine(Canvas canvas) {
            canvas.drawLine(mWidgetPadding + mActualWidgetSize, mActualWidgetSize / 2,
                    mActualWidgetSize + mWidgetPadding * 2, mActualWidgetSize / 2, mLinePaint);
        }

        @Override
        protected void drawableStateChanged() {
            super.drawableStateChanged();
            updateWidgetColor();
        }

        private void updateWidgetColor() {
            boolean inval = false;
            int color = mWidgetColors.getColorForState(getDrawableState(), 0);
            if (color != mWidgetPaint.getColor()) {
                mWidgetPaint.setColor(color);
                inval = true;
            }
            color = mLineColors.getColorForState(getDrawableState(), 0);
            if (color != mLinePaint.getColor()) {
                mLinePaint.setColor(color);
                inval = true;
            }
            color = mContentTextColors.getColorForState(getDrawableState(), 0);
            if (color != mContentTextPaint.getColor()) {
                mContentTextPaint.setColor(color);
                inval = true;
            }
            if (inval) {
                invalidate();
            }
        }

        private boolean isStart() {
            return mPosition == 0;
        }

        private boolean isEnd() {
            return mPosition == mFlowItemCount - 1;
        }

    }

}
