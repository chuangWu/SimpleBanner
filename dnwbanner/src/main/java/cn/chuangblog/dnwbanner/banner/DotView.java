package cn.chuangblog.dnwbanner.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import cn.chuangblog.dnwbanner.R;

public class DotView extends BaseLinearLayout implements PagerIndicator {

    public interface OnDotClickHandler {
        void onDotClick(int index);
    }

    private int mLittleDotSize = 12;
    private int mDotSpan = 4;
    private float mDotRadius = 8f;

    private int mCurrent = 0;
    private int mTotal = 0;

    private int mSelectedColor = 0xFF377BEE;
    private int mUnSelectedColor = 0xFFC5CEDB;
    private OnDotClickHandler mOnDotClickHandler;

    public void setOnDotClickHandler(OnDotClickHandler handler) {
        mOnDotClickHandler = handler;
    }

    private OnClickListener mDotClickHandler = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof LittleDot && null != mOnDotClickHandler) {
                mOnDotClickHandler.onDotClick(((LittleDot) v).getIndex());
            }
        }
    };

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(attrs);
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        setGravity(Gravity.CENTER_HORIZONTAL);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.DotView_dot_radius)) {
                mDotRadius = arr.getDimension(R.styleable.DotView_dot_radius, mDotRadius);
            }

            if (arr.hasValue(R.styleable.DotView_dot_span)) {
                mDotSpan = (int) arr.getDimension(R.styleable.DotView_dot_span, mDotSpan);
            }

            mSelectedColor = arr.getColor(R.styleable.DotView_dot_selected_color, mSelectedColor);
            mUnSelectedColor = arr.getColor(R.styleable.DotView_dot_unselected_color, mUnSelectedColor);
            arr.recycle();
        }
        mLittleDotSize = (int) (mDotSpan / 2 + mDotRadius * 2);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void setNum(int num) {
        if (num < 0)
            return;

        mTotal = num;

        removeAllViews();
        setOrientation(HORIZONTAL);
        for (int i = 0; i < num; i++) {
            LittleDot dot = new LittleDot(getContext(), i);
            if (i == 0) {
                dot.setColor(mSelectedColor);
            } else {
                dot.setColor(mUnSelectedColor);
            }
            dot.setLayoutParams(new LayoutParams((int) mLittleDotSize, (int) mDotRadius * 2, 1));
            dot.setClickable(true);
            dot.setOnClickListener(mDotClickHandler);
            addView(dot);
        }
    }

    @Override
    public int getTotal() {
        return mTotal;
    }

    @Override
    public void setSelected(int index) {
        if (index >= getChildCount() || index < 0 || mCurrent == index) {
            return;
        }
        if (mCurrent < getChildCount() && mCurrent >= 0) {
            ((LittleDot) getChildAt(mCurrent)).setColor(mUnSelectedColor);
        }
        ((LittleDot) getChildAt(index)).setColor(mSelectedColor);
        mCurrent = index;
    }

    @Override
    public int getCurrentIndex() {
        return mCurrent;
    }

    private class LittleDot extends View {

        private int mColor;
        private Paint mPaint;
        private int mIndex;

        public LittleDot(Context context, int index) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mIndex = index;
        }

        public int getIndex() {
            return mIndex;
        }

        public void setColor(int color) {
            if (color == mColor)
                return;
            mColor = color;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            mPaint.setColor(mColor);
            Log.e("TAG", mLittleDotSize + ":" + mDotRadius);
            canvas.drawCircle(mLittleDotSize / 2, mDotRadius, mDotRadius, mPaint);
        }
    }

    public void setSelectedColor(int color) {
        if (mSelectedColor != color) {
            mSelectedColor = color;
            invalidate();
        }
    }

    public void setColor(int selectedColor, int unSelectedColor) {
        if (mSelectedColor != selectedColor || mUnSelectedColor != unSelectedColor) {
            mSelectedColor = selectedColor;
            mUnSelectedColor = unSelectedColor;
            invalidate();
        }
    }

    public void setUnSelectedColor(int color) {
        if (mUnSelectedColor != color) {
            mSelectedColor = color;
            invalidate();
        }
    }
}
