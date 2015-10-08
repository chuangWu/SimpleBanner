package cn.chuangblog.dnwbanner.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.chuangblog.dnwbanner.Entity;
import cn.chuangblog.dnwbanner.R;

public class SliderBannerController {

    public final static int Height = 360;
    private CommonSlideBanner sliderBanner;
    private InnerAdapter bannerAdapter = new InnerAdapter();
    private DotView dotView;
    private Context context;

    public SliderBannerController(Context context, CommonSlideBanner sliderBanner) {
        this.context = context;
        this.sliderBanner = sliderBanner;
        dotView = (DotView) sliderBanner.findViewById(R.id.banner_indicator);
        sliderBanner.setAdapter(bannerAdapter);
    }


    public void play(ArrayList<Entity> list) {
        bannerAdapter.setEntityList(list);
        bannerAdapter.notifyDataSetChanged();
        sliderBanner.setDotNum(list.size());
        sliderBanner.beginPlay();
    }


    private class InnerAdapter extends CommonBannerAdapter {
        private List<Entity> entityList;

        public void setEntityList(List<Entity> entityList) {
            this.entityList = entityList;
        }

        public Entity getItem(int position) {
            if (entityList == null)
                return null;
            return entityList.get(getPositionForIndicator(position));
        }

        @Override
        public int getPositionForIndicator(int position) {
            if (null == entityList || entityList.size() == 0) {
                return 0;
            }
            return position % entityList.size();
        }

        @Override
        public View getView(LayoutInflater layoutInflater, int position) {
            View convertView = layoutInflater.inflate(R.layout.item_banner, null);

            Entity item = getItem(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.demo_banner_item_image);
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Log.e("TAG",item.getImageUrl());
            Picasso.with(context).load(item.getImageUrl()).placeholder(R.mipmap.bg_loding_horizontal).into(imageView);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.demo_banner_item_title);
            titleTextView.setText(item.getDesc());

            convertView.setTag(item);
            convertView.setOnClickListener(null);
            return convertView;
        }

        @Override
        public int getCount() {
            if (entityList == null) {
                return 0;
            }
            return Integer.MAX_VALUE;
        }
    }
}
