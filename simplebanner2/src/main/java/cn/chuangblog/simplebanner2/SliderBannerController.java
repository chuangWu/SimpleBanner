package cn.chuangblog.simplebanner2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderBannerController {

    public final static int Height = (int) (LocalDisplay.SCREEN_WIDTH_PIXELS * 1f / 16 * 9.6f);
    private SliderBanner sliderBanner;
    private InnerAdapter bannerAdapter = new InnerAdapter();
    private DotView dotView;
    private Context context;

    public SliderBannerController(Context context, SliderBanner sliderBanner) {
        this.context = context;
        this.sliderBanner = sliderBanner;
        dotView = (DotView) sliderBanner.findViewById(R.id.demo_slider_banner_indicator);
        sliderBanner.setAdapter(bannerAdapter);
    }


    public void play(ArrayList<Entity> list) {
        bannerAdapter.setEntityList(list);
        bannerAdapter.notifyDataSetChanged();
        sliderBanner.setDotNum(list.size());
        sliderBanner.beginPlay();
    }


    private class InnerAdapter extends BannerAdapter {
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
