package cn.cfanr.izhihudaily.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.cfanr.izhihudaily.R;
import cn.cfanr.izhihudaily.adapter.BaseRecyclerAdapter;
import cn.cfanr.izhihudaily.core.BaseBarActivity;
import cn.cfanr.izhihudaily.model.Editor;
import cn.cfanr.izhihudaily.ui.viewholder.RecyclerHolder;
import cn.cfanr.izhihudaily.utils.JsonTool;

public class EditorsActivity extends BaseBarActivity {
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
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setTitle("主编");
        mRecyclerView=$(R.id.recycler_view_editors_list);
    }

    @Override
    protected void initEvent() {
        Bundle bundle=getIntent().getExtras();
        String editorsJson=bundle.getString("editorsJson");
        final List<Editor> editorList= JsonTool.jsonToObjList(editorsJson, Editor[].class);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter=new BaseRecyclerAdapter<Editor>(mRecyclerView, editorList, R.layout.item_editors_list) {
            @Override
            public void convert(RecyclerHolder holder, Editor editor, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.circle_iv_editors_list_avatar, editor.getAvatar());
                holder.setText(R.id.tv_editors_list_name, editor.getName());
                holder.setText(R.id.tv_editors_list_bio, editor.getBio());
            }
        });

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                String webUrl=editorList.get(position).getUrl();
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("webUrl", webUrl);
                startActivity(intent);
            }
        });
    }

}
