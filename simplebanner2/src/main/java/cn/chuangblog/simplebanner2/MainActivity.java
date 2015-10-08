package cn.chuangblog.simplebanner2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_slide_banner);

        LocalDisplay.init(this);

        final SliderBanner sliderBanner = (SliderBanner) findViewById(R.id.demo_slider_banner);
        LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(LocalDisplay.SCREEN_WIDTH_PIXELS, SliderBannerController.Height);
        sliderBanner.setLayoutParams(lyp);

        final SliderBannerController sliderBannerController = new SliderBannerController(this, sliderBanner);
        sliderBannerController.play(getData());

    }


    public ArrayList<Entity> getData() {
        ArrayList<Entity> list = new ArrayList<>();
        Entity entity = new Entity();
        entity.setImageUrl("http://p3.123.sogoucdn.com/imgu/2015/09/20150924151622_720.jpg");
        entity.setDesc("这是第一张");
        entity.setName("这是一张图片");
        Entity entity2 = new Entity();
        entity2.setImageUrl("http://p8.123.sogoucdn.com/imgu/2015/09/20150924151358_518.png");
        entity2.setDesc("这是第二张");
        entity2.setName("这是一张图片");
        Entity entity3 = new Entity();
        entity3.setImageUrl("http://p6.123.sogoucdn.com/imgu/2015/09/20150921184131_208.png");
        entity3.setDesc("这是第三张");
        entity3.setName("这是一张图片");
        list.add(entity);
        list.add(entity2);
        list.add(entity3);
        return list;
    }
}
