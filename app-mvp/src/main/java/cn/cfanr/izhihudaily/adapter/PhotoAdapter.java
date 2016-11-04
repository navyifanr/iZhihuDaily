package cn.cfanr.izhihudaily.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * author: xifan
 * date: 2016/11/3
 * desc:
 */
public class PhotoAdapter extends PagerAdapter{
    private Context context;
    private ArrayList<String> imgUrlList = new ArrayList<>();

    public PhotoAdapter(Context context, ArrayList<String> imgUrlList){
        this.context = context;
        this.imgUrlList = imgUrlList;
    }

    @Override
    public int getCount() {
        return imgUrlList!=null ? imgUrlList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.item_photo_preview, null);
        ImageView pvShowImage = (ImageView) view.findViewById(R.id.pv_show_image);
        String picUrl = imgUrlList.get(position);
        final PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(pvShowImage);
        photoViewAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        photoViewAttacher.setMinimumScale(1F);
        ImageUtils.displayScaleImage(context,pvShowImage,picUrl,photoViewAttacher);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
