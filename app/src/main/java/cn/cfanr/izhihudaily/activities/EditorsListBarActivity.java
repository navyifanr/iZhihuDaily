package cn.cfanr.izhihudaily.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.BaseRecyclerAdapter;
import cn.cfanr.izhihudaily.base.BaseBarActivity;
import cn.cfanr.izhihudaily.model.EditorModel;
import cn.cfanr.izhihudaily.utils.JsonTool;
import cn.cfanr.izhihudaily.view.viewholder.RecyclerHolder;

public class EditorsListBarActivity extends BaseBarActivity {
    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_editors_list;
    }

    @Override
    public void initView() {
        setTitle("主编");
        mRecyclerView=$(R.id.recycler_view_editors_list);
    }

    @Override
    public void initEvent() {
        Bundle bundle=getIntent().getExtras();
        String editorsJson=bundle.getString("editorsJson");
        final List<EditorModel> editorModelList= JsonTool.jsonToObjList(editorsJson, EditorModel[].class);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter=new BaseRecyclerAdapter<EditorModel>(mRecyclerView, editorModelList, R.layout.item_editors_list) {
            @Override
            public void convert(RecyclerHolder holder, EditorModel editorModel, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.circle_iv_editors_list_avatar, editorModel.getAvatar());
                holder.setText(R.id.tv_editors_list_name, editorModel.getName());
                holder.setText(R.id.tv_editors_list_bio, editorModel.getBio());
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                String webUrl=editorModelList.get(position).getUrl();
                Intent intent=new Intent(getActivity(), WebViewBarActivity.class);
                intent.putExtra("webUrl", webUrl);
                startActivity(intent);
            }
        });
    }

}
