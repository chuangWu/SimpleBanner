package cn.chuangblog.simplebanner1.test;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cn.chuangblog.simplebanner1.InfiniteViewPager;
import cn.chuangblog.simplebanner1.R;
import cn.chuangblog.simplebanner1.indicator.CirclePageIndicator;
import cn.chuangblog.simplebanner1.indicator.LinePageIndicator;

public class MainActivity extends FragmentActivity {
    InfiniteViewPager mViewPager;
    LinePageIndicator mLineIndicator;
    InfiniteViewPager mViewPager2;
    CirclePageIndicator mCircleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (InfiniteViewPager) findViewById(R.id.viewpager);
        mLineIndicator = (LinePageIndicator) findViewById(R.id.indicator);
        mViewPager2 = (InfiniteViewPager) findViewById(R.id.viewpager2);
        mCircleIndicator = (CirclePageIndicator) findViewById(R.id.indicator2);

        MockPagerAdapter pagerAdapter = new MockPagerAdapter(this);
        pagerAdapter.setDataList(MockDataGenerator.getViewPagerData());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setAutoScrollTime(5000);
        mViewPager.startAutoScroll();
        mLineIndicator.setViewPager(mViewPager);

        MockPagerAdapter pagerAdapter2 = new MockPagerAdapter(this);
        pagerAdapter2.setDataList(MockDataGenerator.getViewPagerData());
        mViewPager2.setAdapter(pagerAdapter);
        mViewPager2.setAutoScrollTime(10000);
        mViewPager2.startAutoScroll();
        mCircleIndicator.setViewPager(mViewPager2);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mViewPager != null)
            mViewPager.startAutoScroll();
        if (mViewPager2 != null)
            mViewPager2.startAutoScroll();
    }

    @Override
    public void onStop() {
        if (mViewPager != null)
            mViewPager.stopAutoScroll();
        if (mViewPager2 != null)
            mViewPager2.stopAutoScroll();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/waylife/InfiniteViewPager"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
