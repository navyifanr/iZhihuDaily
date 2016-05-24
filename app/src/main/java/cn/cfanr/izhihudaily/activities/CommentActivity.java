package cn.cfanr.izhihudaily.activities;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.CommonAdapter;
import cn.cfanr.izhihudaily.app.Api;
import cn.cfanr.izhihudaily.app.AppController;
import cn.cfanr.izhihudaily.base.BaseBarActivity;
import cn.cfanr.izhihudaily.model.CommentModel;
import cn.cfanr.izhihudaily.model.CommentedModel;
import cn.cfanr.izhihudaily.utils.AnimUtils;
import cn.cfanr.izhihudaily.utils.DateTimeUtils;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.view.NoScrollListView;
import cn.cfanr.izhihudaily.view.viewholder.CommonViewHolder;

public class CommentActivity extends BaseBarActivity {
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
    public void initView() {
        mScrollView=$(R.id.scroll_view_comment);
        tvLongComment=$(R.id.tv_comment_long_comment_tag);
        tvShortComment=$(R.id.tv_comment_short_comment_tag);
        rlShortItem=$(R.id.rl_comment_short_item);
        ivFold=$(R.id.iv_comment_fold);
        lvLongComment=$(R.id.list_view_long_comment);
        lvShortComment=$(R.id.list_view_short_comment);
    }

    @Override
    public void initEvent() {
        Bundle bundle=getIntent().getExtras();
        String commentsNum=bundle.getString("commentsNum", "0");
        String articleId=bundle.getString("articleId");
        setTitle(commentsNum+"条点评");

        loadLongCommentData(articleId);
        lvLongComment.setAdapter(longCommentAdapter=getCommentAdapter(longCommentList));

        loadShortCommentData(articleId);
        lvShortComment.setAdapter(shortCommentAdapter=getCommentAdapter(shortCommentList));

        rlShortItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.ic_rotate);
//                LinearInterpolator lin = new LinearInterpolator();
//                animation.setInterpolator(lin);
//                animation.setFillAfter(true);
//                ivFold.startAnimation(animation);

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

    private void loadLongCommentData(String articleId){
        String tagName=getClassMethodName();
        String url=String.format(Api.url_article_long_comments, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap=JsonTool.parseJson2Map(response.toString());
                        String content=JsonTool.mapObjVal2Str(resultMap, "comments");
                        longCommentList=JsonTool.jsonToObjList(content, CommentModel[].class);
                        if(longCommentList!=null){
                            int num=longCommentList.size();
                            tvLongComment.setText(num+"条长评");
                            longCommentAdapter.setData(longCommentList);  //适配器的数据源改变了，直接用notifyDataSetChanged无效
                        }else{
                            tvLongComment.setText("0条长评");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }

    private void loadShortCommentData(String articleId){
        String tagName=getClassMethodName();
        String url=String.format(Api.url_article_short_comments, articleId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Map<String, Object> resultMap=JsonTool.parseJson2Map(response.toString());
                        String content=JsonTool.mapObjVal2Str(resultMap, "comments");
                        shortCommentList=JsonTool.jsonToObjList(content, CommentModel[].class);
                        if(shortCommentList!=null){
                            int num=shortCommentList.size();
                            tvShortComment.setText(num+"条短评");
                            shortCommentAdapter.setData(shortCommentList);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tagName);
    }
}
