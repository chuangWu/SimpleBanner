package cn.chuangblog.dnwbanner.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.chuangblog.dnwbanner.R;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-30 16:34
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CommonSlideBanner extends BaseRelativeLayout {

    protected int mIdForViewPager;
    protected int mIdForIndicator;
    protected int mTimeInterval = 2000;
    private ViewPager mViewPager;
    private CommonBannerAdapter mBannerAdapter;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private PagerIndicator mPagerIndicator;
    private AutoPlayer mAutoPlayer;
    private OnTouchListener mViewPagerOnTouchListener;
    private AutoPlayer.Playable mGalleryPlayable = new AutoPlayer.Playable() {

        @Override
        public void playTo(int to) {
            mViewPager.setCurrentItem(to, true);
        }

        @Override
        public void playNext() {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        }

        @Override
        public void playPrevious() {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        }

        @Override
        public int getTotal() {
            return mBannerAdapter.getCount();
        }

        @Override
        public int getCurrent() {
            return mViewPager.getCurrentItem();
        }
    };


    public CommonSlideBanner(Context context) {
        super(context);
    }

    public CommonSlideBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initAttributeSet(AttributeSet attrs) {
        super.initAttributeSet(attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SliderBanner, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.SliderBanner_slider_banner_pager)) {
                mIdForViewPager = arr.getResourceId(R.styleable.SliderBanner_slider_banner_pager, 0);
            }
            if (arr.hasValue(R.styleable.SliderBanner_slider_banner_indicator)) {
                mIdForIndicator = arr.getResourceId(R.styleable.SliderBanner_slider_banner_indicator, 0);
            }
            mTimeInterval = arr.getInt(R.styleable.SliderBanner_slider_banner_time_interval, mTimeInterval);
            arr.recycle();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mAutoPlayer != null) {
                    mAutoPlayer.pause();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mAutoPlayer != null) {
                    mAutoPlayer.resume();
                }
                break;
            default:
                break;
        }
        if (mViewPagerOnTouchListener != null) {
            mViewPagerOnTouchListener.onTouch(this, ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setViewPagerOnTouchListener(OnTouchListener onTouchListener) {
        mViewPagerOnTouchListener = onTouchListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mViewPager = (ViewPager) findViewById(mIdForViewPager);
        mPagerIndicator = (DotView) findViewById(mIdForIndicator);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(i, v, i2);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (mPagerIndicator != null) {
                    mPagerIndicator.setSelected(mBannerAdapter.getPositionForIndicator(position));
                }
                mAutoPlayer.skipNext();

                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        });

        mAutoPlayer = new AutoPlayer(mGalleryPlayable).setPlayRecycleMode(AutoPlayer.PlayRecycleMode.play_back);
        mAutoPlayer.setTimeInterval(mTimeInterval);
    }

    public void setTimeInterval(int interval) {
        mAutoPlayer.setTimeInterval(interval);
    }

    public void setAdapter(CommonBannerAdapter adapter) {
        mBannerAdapter = adapter;
        mViewPager.setAdapter(adapter);
    }

    public void beginPlay() {
        mAutoPlayer.play();
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    public void setDotNum(int num) {
        if (mPagerIndicator != null) {
            mPagerIndicator.setNum(num);
        }
    }
}
