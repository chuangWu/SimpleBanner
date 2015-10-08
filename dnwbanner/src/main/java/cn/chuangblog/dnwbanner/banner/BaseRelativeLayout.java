package cn.chuangblog.dnwbanner.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-30 16:36
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public abstract class BaseRelativeLayout extends RelativeLayout {

    protected Context context;
    protected OnChildViewClickListener onChildViewClickListener;


    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    protected void onChildViewClick(View childView, int action, Object obj) {
        if (onChildViewClickListener != null)
            onChildViewClickListener.onChildViewClick(childView, action, obj);
    }

    protected void onChildViewClick(View childView, int action) {
        onChildViewClick(childView, action, null);
    }

    public BaseRelativeLayout(Context context) {
        super(context);
        this.context = context;
        init(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }


    protected abstract int getLayoutId();

    protected void init(Context context, AttributeSet attrs) {
        if (getLayoutId() != 0) {
            View view = LayoutInflater.from(context).inflate(getLayoutId(), this, true);
            if (view != null)
                ButterKnife.bind(this, view);
            if (attrs != null)
                initAttributeSet(attrs);
        }
        if (attrs != null)
            initAttributeSet(attrs);

        initView();
        initListener();
        initData();
    }


    protected void initAttributeSet(AttributeSet attrs) {

    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }
}
