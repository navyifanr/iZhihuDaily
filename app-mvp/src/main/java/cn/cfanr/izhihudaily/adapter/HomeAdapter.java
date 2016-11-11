package cn.cfanr.izhihudaily.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.model.HomeModel;
import cn.cfanr.izhihudaily.model.HomeType;
import cn.cfanr.izhihudaily.model.NewsModel;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import cn.cfanr.izhihudaily.ui.viewholder.BannerHolder;
import cn.cfanr.izhihudaily.utils.PreferenceUtil;

/**
 * @author xifan
 * @time 2016/5/5
 * @desc
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<HomeModel> homeModelList=new ArrayList<>();

    public HomeAdapter(Context context, List<HomeModel> homeModelList){
        this.context=context;
        this.homeModelList=homeModelList;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String articleId);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== HomeType.BANNER_ITEM){
            return new BannerHolder(context, mLayoutInflater.inflate(R.layout.item_home_banner, parent, false));
        }else if(viewType==HomeType.TITLE_ITEM){
            return new TitleHolder(mLayoutInflater.inflate(R.layout.item_home_title, parent, false));
        }else if(viewType==HomeType.NEWS_ITEM){
            return new NewsHolder(mLayoutInflater.inflate(R.layout.item_home_news, parent, false));
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HomeModel homeModel=homeModelList.get(position);
        if(holder instanceof BannerHolder){
            ((BannerHolder) holder).setViewPager(homeModel);
        }else if(holder instanceof TitleHolder){
            ((TitleHolder) holder).tvTitle.setText(homeModel.getDate());
        }else if(holder instanceof NewsHolder){
            final NewsModel newsModel=homeModel.getNewsModel();
            ((NewsHolder) holder).tvTitle.setText(newsModel.getTitle());
            List<String> imgList=newsModel.getImages();
            if(imgList!=null&&imgList.size()>0) {
                ImageUtils.loadImage(((NewsHolder) holder).mImg, newsModel.getImages().get(0));
            }
            final String articleId=newsModel.getId();
            if(PreferenceUtil.isRead(context, articleId)){
                ((NewsHolder) holder).tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray));
            }else{
                ((NewsHolder) holder).tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        ((NewsHolder) holder).tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray));
                        int pos=holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, pos, articleId);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return homeModelList.get(position).getType();
    }

    class TitleHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        public TitleHolder(View itemView) {
            super(itemView);
            tvTitle=$(itemView, R.id.tv_home_title);
        }
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
