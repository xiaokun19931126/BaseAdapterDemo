package com.xiaokun.adapterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xiaokun.adapter_library.BaseAdapterData;
import com.xiaokun.adapter_library.BaseRecyclerAdapter;
import com.xiaokun.adapter_library.LoadingData;
import com.xiaokun.http_library.RxHttpUtils;
import com.xiaokun.http_library.observer.CommonObserver;
import com.xiaokun.http_library.transformer.Transformer;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final int LINEAR = 0;
    public static final int GRID = 1;
    private RecyclerView mRecyclerview;
    private Toolbar mToolbar;
    private BaseRecyclerAdapter adapter;
    private int direction = LINEAR;
    private List<GankCategoryEntity.ResultsBean> datas;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initRecyclerView();
        convert(direction);
        loadData(page);
    }

    private void initToolbar()
    {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置 Toolbar menu
        mToolbar.inflateMenu(R.menu.setting_menu);
        // 设置menu item 点击事件
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                String title = item.getTitle().toString();
                switch (item.getItemId())
                {
                    case R.id.direction:
                        if (title.equals("Linear"))
                        {
                            item.setTitle("Grid");
                            direction = GRID;
                        } else
                        {
                            item.setTitle("Linear");
                            direction = LINEAR;
                        }
                        break;
                    default:

                        break;
                }
                convert(direction);
                return false;
            }
        });
    }

    private void initRecyclerView()
    {
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new BaseRecyclerAdapter(this);
        final int spacing = getResources().getDimensionPixelSize(R.dimen.dimen_2_dp);
        mRecyclerview.addItemDecoration(new OffsetDecoration(spacing));
    }

    private void convert(int direction)
    {
        if (direction == LINEAR)
        {
            LinearLayoutManager manager = new LinearLayoutManager(this);
            mRecyclerview.setLayoutManager(manager);
        } else
        {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerview.setLayoutManager(gridLayoutManager);

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
            {
                @Override
                public int getSpanSize(int position)
                {
                    BaseAdapterData data = adapter.getData().get(position);
                    return data instanceof LoadingData || data instanceof HeadViewData ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }

        adapter.setOnLoadMoreListener(new BaseRecyclerAdapter.OnLoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                page++;
                if (page > 3)
                {
                    adapter.loadEnd();
                } else
                {
                    loadData(page);
                }
            }
        }, mRecyclerview);
        mRecyclerview.setAdapter(adapter);
    }

    private void loadData(final int page)
    {
        RxHttpUtils.createApi(GankService.class).getCategoryData("Android", 20, page)
                .compose(Transformer.<GankCategoryEntity>switchSchedulers())
                .subscribe(new CommonObserver<GankCategoryEntity>()
                {
                    @Override
                    protected void onError(String errorMsg)
                    {
                        adapter.loadFailed();
                    }

                    @Override
                    protected void onSuccess(GankCategoryEntity gankCategoryEntity)
                    {
                        if (gankCategoryEntity == null || gankCategoryEntity.getResults() == null)
                        {
                            return;
                        }

                        datas = gankCategoryEntity.getResults();
                        if (page == 1)
                        {
                            adapter.registerHolder(SearchHeadHolder.class, new HeadViewData());
                        }
                        adapter.registerHolder(SearchViewHolder.class, datas);
                    }
                });
    }

}
