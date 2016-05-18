package cn.cfanr.izhihudaily.view.viewholder;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.BaseRecyclerAdapter;
import cn.cfanr.izhihudaily.model.EditorModel;
import cn.cfanr.izhihudaily.model.ThemeDailyModel;
import cn.cfanr.izhihudaily.utils.ImageUtils;
import cn.cfanr.izhihudaily.utils.ScreenUtil;

/**
 * @author xifan
 * @time 2016/5/18
 * @desc 主题日报列表的头部
 */
public class ThemeDailyHeader extends RecyclerView.ViewHolder{
    Context context;
    ScreenUtil screenUtil;
    RelativeLayout rlHeader;
    ImageView ivBg;
    TextView tvDescription;
    RecyclerView mRecyclerView;
    BaseRecyclerAdapter mAdapter;
    public ThemeDailyHeader(Context context, View itemView) {
        super(itemView);
        this.context=context;
        rlHeader=$(itemView, R.id.rl_theme_daily_header);
        ivBg=$(itemView, R.id.iv_theme_daily_bg);
        tvDescription=$(itemView, R.id.tv_theme_daily_title);
        mRecyclerView=$(itemView, R.id.recycler_view_editors);
        screenUtil=new ScreenUtil(context);
        int width=screenUtil.getScreenWidth();

        ViewGroup.LayoutParams params=rlHeader.getLayoutParams();
        params.height=9*width/16;
        rlHeader.setLayoutParams(params);
    }

    public void refreshUI(ThemeDailyModel themeDailyModel){
        ImageUtils.loadImage(ivBg, themeDailyModel.getBackground());
        tvDescription.setText(themeDailyModel.getDescription());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<EditorModel> editorModelList=themeDailyModel.getEditorModelList();
        mRecyclerView.setAdapter(mAdapter=new BaseRecyclerAdapter<EditorModel>(mRecyclerView, editorModelList, R.layout.item_theme_editor_avatar) {
            @Override
            public void convert(RecyclerHolder holder, EditorModel editorModel, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.circle_iv_editor_avatar, editorModel.getAvatar());
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {

            }
        });
    }

    private <T extends View>T $(View view, @IdRes int resId){
        return (T) view.findViewById(resId);
    }
}