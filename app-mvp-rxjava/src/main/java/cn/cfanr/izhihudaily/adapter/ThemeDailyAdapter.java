package cn.cfanr.izhihudaily.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.model.News;
import cn.cfanr.izhihudaily.model.ThemeDailyModel;
import cn.cfanr.izhihudaily.ui.viewholder.ThemeDailyHeader;
import cn.cfanr.izhihudaily.utils.ImageUtils;

/**
 * @author xifan
 * @time 2016/5/18
 * @desc 主题日报-适配器
 */
public class ThemeDailyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<ThemeDailyModel> themeDailyModelList=new ArrayList<>();

    public ThemeDailyAdapter(Context context, List<ThemeDailyModel> themeDailyModelList){
        this.context=context;
        this.themeDailyModelList=themeDailyModelList;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== ThemeDailyModel.THEME_DAILY_HEADER){
            return new ThemeDailyHeader(context, mLayoutInflater.inflate(R.layout.item_theme_daily_header, parent, false));
        }else  if(viewType==ThemeDailyModel.THEME_DAILY_NEWS){
            return new NewsHolder(mLayoutInflater.inflate(R.layout.item_home_news, parent, false));
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ThemeDailyModel themeDailyModel=themeDailyModelList.get(position);
        if(holder instanceof ThemeDailyHeader){
            ((ThemeDailyHeader) holder).refreshUI(themeDailyModel);
        }else  if(holder instanceof NewsHolder){
            News newsModel=themeDailyModel.getNewsModel();
            ((NewsHolder) holder).tvTitle.setText(newsModel.getTitle());
            List<String> imgList=newsModel.getImages();
            if(imgList!=null&&imgList.size()>0) {
                ImageUtils.loadImage(((NewsHolder) holder).mImg, newsModel.getImages().get(0));
            }else{
                ((NewsHolder) holder).mImg.setVisibility(View.GONE);
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int pos=holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return themeDailyModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return themeDailyModelList.get(position).getType();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        ImageView mImg;
        TextView tvTitle;
        public NewsHolder(View itemView) {
            super(itemView);
            mImg=$(itemView, R.id.iv_home_news_img);
            tvTitle=$(itemView, R.id.tv_home_news_title);
        }
    }

    private <T extends View>T $(View view, @IdRes int resId){
        return (T) view.findViewById(resId);
    }

}
