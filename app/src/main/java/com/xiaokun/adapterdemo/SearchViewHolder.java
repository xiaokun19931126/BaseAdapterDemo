package com.xiaokun.adapterdemo;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaokun.adapter_library.BaseRecyclerAdapter;
import com.xiaokun.adapter_library.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/01/10
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class SearchViewHolder extends BaseViewHolder<GankCategoryEntity.ResultsBean>
{

    @BindView(R.id.category_desc)
    TextView categoryDesc;
    @BindView(R.id.category_author)
    TextView categoryAuthor;
    @BindView(R.id.category_date)
    TextView categoryDate;
    @BindView(R.id.home_cv)
    CardView homeCv;

    public SearchViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter)
    {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public int getContentViewId()
    {
        return R.layout.item_search_view;
    }

    @Override
    public void initViews()
    {
        super.initViews();
        ButterKnife.bind(this, mItemView);
    }

    @Override
    public void updateItem(GankCategoryEntity.ResultsBean data, int position)
    {
        int limit = 48;
        String string = data.getDesc();
        String text = string.length() > limit ? string.substring(0, limit) + "..." : string;

        categoryDesc.setText(text);
        categoryAuthor.setText(data.getWho());
        categoryDate.setText(data.getPublishedAt().substring(0, 10));
    }

    @OnClick(R.id.home_cv)
    public void onViewClicked()
    {
        Toast.makeText(mContext, "测试Application启动Activity", Toast.LENGTH_LONG).show();
    }
}
