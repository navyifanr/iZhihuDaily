package cn.cfanr.izhihudaily.ui.viewholder;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.ImageAdapter;
import cn.cfanr.izhihudaily.model.HomeModel;
import cn.cfanr.izhihudaily.model.News;
import cn.cfanr.izhihudaily.ui.activities.ArticleActivity;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

/**
 * @author xifan
 * @time 2016/5/11
 * @desc
 */
public class BannerHolder  extends RecyclerView.ViewHolder {
    //请求更新显示的View
    protected static final int MSG_UPDATE_IMAGE=1;
    //请求暂停轮播
    protected static final int MSG_KEEP_SILENT=2;
    //请求恢复轮播
    protected static final int MSG_BREAK_SILENT=3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    protected static final int MSG_PAGE_CHANGED=4;
    //轮播间隔时间
    protected static final long MSG_DELAY=3000;

    Context context;
    ScreenUtil screenUtil;
    RelativeLayout rlItem;
    ImageView ivIntro;
    ViewPager mViewPager;
    LinearLayout llDots;
    TextView tvTitle;
    List<News> bannerList;
    ArrayList<String> articleIdList=new ArrayList<>();
    ImageAdapter imgAdapter;
    int currentItem=0;
    private int preDotPosition = 0; //上一个被选中的小圆点的索引，默认值为0
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if(handler.hasMessages(MSG_UPDATE_IMAGE)){
                handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch(msg.what){
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    mViewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem=msg.arg1;
                    break;
                default:
                    break;
            }
        }
    };
    public BannerHolder(Context context, View itemView) {
        super(itemView);
        this.context=context;
        rlItem=$(itemView, R.id.rl_home_banner);
        ivIntro=$(itemView, R.id.iv_home_banner_img);
        mViewPager=$(itemView, R.id.view_pager_home_banner_img);
        llDots=$(itemView, R.id.ll_home_banner_dots);
        tvTitle=$(itemView, R.id.tv_home_banner_title);
        screenUtil=new ScreenUtil(context);
        int width=screenUtil.getScreenWidth();

        ViewGroup.LayoutParams params=rlItem.getLayoutParams();
        params.height=10*width/16;
        rlItem.setLayoutParams(params);
    }

    public void setViewPager(HomeModel homeModel){
        bannerList =homeModel.getBannerList();
        final List<String> imgStrList=new ArrayList<>();
        final int length= bannerList.size();
        if(length==1&&llDots.getChildCount()==0){
            mViewPager.setVisibility(View.GONE);
            ivIntro.setVisibility(View.VISIBLE);
            ImageUtils.loadImage(ivIntro, bannerList.get(0).getImages().get(0));
            setPagerItemData(0);
            View dot = getDotView();
            llDots.addView(dot);
            llDots.getChildAt(0).setEnabled(true);
            return;
        }
        //注意判断当再次滑到厨说时，只有一条，不执行配置适配器的代码
        if(length==1){
            return;
        }
        if(imgAdapter==null){   //避免重复初始化ViewPager和addView
            for(int index=0; index<length; index++){
                News newsModel = bannerList.get(index);
                imgStrList.add(newsModel.getImage());
                View dot = getDotView();
                llDots.addView(dot); // 向线性布局中添加"点"
                articleIdList.add(newsModel.getId());
            }
            llDots.getChildAt(0).setEnabled(true);
            imgAdapter=new ImageAdapter(context, imgStrList);
            mViewPager.setAdapter(imgAdapter);
            //默认设置
            setPagerItemData(0);
            //只有大于1，才设置轮播
            if(length>1){
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                    }

                    @Override
                    public void onPageSelected(final int position){
                        handler.sendMessage(Message.obtain(handler, MSG_PAGE_CHANGED, position, 0));
                        int newPosition=position%length;
                        setPagerItemData(newPosition);
                        // 把上一个点设置为被选中
                        llDots.getChildAt(preDotPosition).setEnabled(false);
                        llDots.getChildAt(newPosition).setEnabled(true);
                        preDotPosition=newPosition;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state){
                        switch(state){
                            case ViewPager.SCROLL_STATE_DRAGGING:
                                handler.sendEmptyMessage(MSG_KEEP_SILENT);
                                break;
                            case ViewPager.SCROLL_STATE_IDLE:
                                handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                                break;
                            default:
                                break;
                        }
                    }
                });
                mViewPager.setCurrentItem(0);
                //开始轮播效果
                handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
            }

            imgAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra("articleId", articleIdList.get(position));
                    intent.putStringArrayListExtra("articleIdList", articleIdList);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    private View getDotView() {
        View dot=new View(context);
        dot.setBackgroundResource(R.drawable.dot_bg_selector);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(screenUtil.dip2px(5), screenUtil.dip2px(5));
        params.leftMargin=screenUtil.dip2px(10);
        dot.setEnabled(false);
        dot.setLayoutParams(params);
        return dot;
    }

    private void setPagerItemData(final int position) {
        News newsModel = bannerList.get(position);
        String title=newsModel.getTitle();
        tvTitle.setText(title);
    }

    private <T extends View>T $(View view, @IdRes int resId){
        return (T) view.findViewById(resId);
    }
}