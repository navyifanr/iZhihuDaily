package cn.cfanr.izhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.cfanr.izhihudaily.model.CommentModel;

/**
 * @author xifan
 * @time 2016/5/22
 * @desc
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<CommentModel> longCommentList;
    private List<CommentModel> shortCommentList;

    public CommentAdapter(Context context, List<CommentModel> longCommentList, List<CommentModel> shortCommentList){

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
