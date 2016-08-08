package cn.cfanr.izhihudaily.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.CommonAdapter;
import cn.cfanr.izhihudaily.core.BaseBarActivity;
import cn.cfanr.izhihudaily.model.CommentModel;
import cn.cfanr.izhihudaily.model.CommentedModel;
import cn.cfanr.izhihudaily.presenter.CommentPresenter;
import cn.cfanr.izhihudaily.widget.NoScrollListView;
import cn.cfanr.izhihudaily.ui.viewholder.CommonViewHolder;
import cn.cfanr.izhihudaily.utils.AnimUtils;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.ui.view.CommentView;

public class CommentActivity extends BaseBarActivity implements CommentView{
    private CommentPresenter commentPresenter;

    private ScrollView mScrollView;
    private TextView tvLongComment;
    private TextView tvShortComment;
    private RelativeLayout rlShortItem;
    private ImageView ivFold;
    private NoScrollListView lvLongComment;
    private NoScrollListView lvShortComment;

    private List<CommentModel> longCommentList=new ArrayList<>();
    private List<CommentModel> shortCommentList=new ArrayList<>();
    private CommonAdapter longCommentAdapter, shortCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initPresenter() {
        commentPresenter=new CommentPresenter();
        commentPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        mScrollView=$(R.id.scroll_view_comment);
        tvLongComment=$(R.id.tv_comment_long_comment_tag);
        tvShortComment=$(R.id.tv_comment_short_comment_tag);
        rlShortItem=$(R.id.rl_comment_short_item);
        ivFold=$(R.id.iv_comment_fold);
        lvLongComment=$(R.id.list_view_long_comment);
        lvShortComment=$(R.id.list_view_short_comment);
    }

    @Override
    protected void initEvent() {
        Bundle bundle=getIntent().getExtras();
        String commentsNum=bundle.getString("commentsNum", "0");
        String articleId=bundle.getString("articleId");
        setTitle(commentsNum+"条点评");

        commentPresenter.loadLongCommentData(articleId);
        lvLongComment.setAdapter(longCommentAdapter=getCommentAdapter(longCommentList));

        commentPresenter.loadShortCommentData(articleId);
        lvShortComment.setAdapter(shortCommentAdapter=getCommentAdapter(shortCommentList));

        rlShortItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lvShortComment.getVisibility()==View.GONE) {
                    lvShortComment.setVisibility(View.VISIBLE);
                    AnimUtils.rotateAnim(0, 180, ivFold);
                    rlShortItem.post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView.scrollTo(0, rlShortItem.getTop());  //置顶，注意要在线程内执行才有效
                        }
                    });
                }else {
                    lvShortComment.setVisibility(View.GONE);
                    AnimUtils.rotateAnim(180, 0, ivFold);
                }
            }
        });
    }

    private CommonAdapter getCommentAdapter(List<CommentModel > commentModelList){
        return new CommonAdapter<CommentModel>(getActivity(), commentModelList, R.layout.item_comment_list) {
            @Override
            public void convert(CommonViewHolder holder, CommentModel commentModel, int position) {
                holder.setImageByUrl(R.id.circle_iv_comment_avatar, commentModel.getAvatar());
                holder.setText(R.id.tv_comment_author, commentModel.getAuthor());

                String content=commentModel.getContent();
                CommentedModel commentedModel=commentModel.getReply_to();
                String replyToContent="";
                if(commentedModel!=null){
                    int status=commentedModel.getStatus();
                    if(status==0){
                        replyToContent=commentedModel.getContent();
                        String replier=commentedModel.getAuthor();
                        replyToContent="<b>//"+replier+":</b><font color='#333333'>"+replyToContent+"</font>";
                    }else if(status==1){
                        replyToContent=commentedModel.getError_msg();
                    }
                }
                holder.setText(R.id.tv_comment_content, Html.fromHtml(content+"\n"+replyToContent));

                TextView tvLike=holder.getView(R.id.tv_comment_like);
                tvLike.setText(commentModel.getLikes());
                tvLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                String time= DateTimeUtils.timeStamp2Date(commentModel.getTime(), "MM-dd hh:mm");
                holder.setText(R.id.tv_comment_time, time);
            }
        };
    }

    @Override
    public void setLongCommentsData(List<CommentModel> longCommentList) {
        if(longCommentList!=null){
            int num=longCommentList.size();
            tvLongComment.setText(num+"条长评");
            longCommentAdapter.setData(longCommentList);  //适配器的数据源改变了，直接用notifyDataSetChanged无效
        }else{
            tvLongComment.setText("0条长评");
        }
    }

    @Override
    public void setShortCommentsData(List<CommentModel> shortCommentList) {
        if(shortCommentList!=null){
            int num=shortCommentList.size();
            tvShortComment.setText(num+"条短评");
            shortCommentAdapter.setData(shortCommentList);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentPresenter.detachView();
    }
}
