package com.zhongcai.common.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.base.BaseAdapter;
import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.Config;
import com.zhongcai.common.widget.recyclerview.adapter.LoadMoreAdapter;
import com.zhongcai.common.widget.recyclerview.adapter.NoDataAdapter;

import java.util.List;


/**
 * Created by zhutao on 2018/3/9.
 *
 *  加载更多
 */

public class SuperRecyclerView extends RecyclerView {


    public SuperRecyclerView(Context context) {
        this(context,null);
    }

    public SuperRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SuperRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,  attrs);
    }

    // 页码
    private int page = 1;
    private VirtualLayoutManager layoutManager;
    private RecycledViewPool recycledViewPool;
    private DelegateAdapter adapters;
    private LoadMoreAdapter mloadMoreAdapter;
    private boolean isLoading = false;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void init(Context context, AttributeSet attrs){
        layoutManager = new VirtualLayoutManager(getContext());
        setLayoutManager(layoutManager);
    }

    private void initAdapters() {
        if (null == adapters){
            this.recycledViewPool = new RecycledViewPool();
            adapters = new DelegateAdapter(layoutManager);
        }
    }

    public boolean isNotEmptyAdapter(){
        if (null == adapters){
            return false;
        }
        return adapters.getItemCount() > 0;
    }

    public VirtualLayoutManager getManager(){
        return layoutManager;
    }

    /**
     * 共用
     */
    public void setPoolRecyclerView(RecyclerView rv){
        rv.setRecycledViewPool(recycledViewPool);
    }

    /**
     * 设置一个界面内 当前type最多能展示多少个 不回收
     */
    public void setMaxRecycledViews(int viewType,int max){
        initAdapters();
        recycledViewPool.setMaxRecycledViews(viewType, max);

    }


    public void setLoadMoreText(String text){
        if(null == mloadMoreAdapter)
            mloadMoreAdapter = new LoadMoreAdapter();
        mloadMoreAdapter.setTextThree(text);
    }

    /**
     * 添加
     * @param adapter
     */
    public void addAdapter(DelegateAdapter.Adapter<BaseViewHolder> adapter){
        initAdapters();
        adapters.addAdapter(adapter);
    }

    public void clear(){
        if(null != adapters)
            adapters.clear();
    }

    /**
     * 设置适配器
     * 没有加载更多使用
     */
    public void setAdapter(){
        setAdapter(adapters);
    }


    private NoDataAdapter noDataAdapter;

    public NoDataAdapter getNoDataAdapter() {
        if(null == noDataAdapter){
            noDataAdapter = new NoDataAdapter(layoutId,this);
            if(null != listener)
                noDataAdapter.setOnLoadMoreListener(listener);
            addAdapter(noDataAdapter);
        }
        return noDataAdapter;
    }

    public void hideNoData(){
        if(null != noDataAdapter) {
            noDataAdapter.hide();
            if(null != mloadMoreAdapter){
                mloadMoreAdapter.show();
            }
        }
    }

    private int layoutId = 0;

    public void setLayoutId(int layoutId){
        this.layoutId = layoutId;
    }


    public void showNoData(){
        if(null == noDataAdapter){
            noDataAdapter = new NoDataAdapter(layoutId,this);
            if(null != listener)
                noDataAdapter.setOnLoadMoreListener(listener);
            addAdapter(noDataAdapter);
        }
        if(null != mloadMoreAdapter){
            mloadMoreAdapter.hide();
        }
        noDataAdapter.show();
    }


    OnLoadMoreListener listener;
//    public void setLoadAgainListener(OnLoadMoreListener listener){
//        this.listener = listener;
//    }


    public void hideMore(){
        if(null != mloadMoreAdapter){
            mloadMoreAdapter.hide();
        }
    }

    /**
     * 设置无线加载更多的适配器
     */
    public  <T> void setAdapter(BaseAdapter<T> adapter, List<T> list){
        adapter.clear();
        int size = list == null ? 0: list.size();
        if(size == 0) {
            if(null != mloadMoreAdapter){
                mloadMoreAdapter.hide();
            }
            showNoData();
        }
        else {
            if(null != mloadMoreAdapter){
                mloadMoreAdapter.show();
            }
            hideNoData();
        }

        setLoadMore(adapter,list);
    }


    public  <T> void setLoadMore(BaseAdapter<T> adapter, List<T> list){
        if(null != mloadMoreAdapter) {
            int size = list == null ? 0: list.size();
            if (size >= Config.PAGE_SIZE) {
                mloadMoreAdapter.setState(1);
            } else {
                mloadMoreAdapter.setState(3);
            }
            mloadMoreAdapter.notifyDataSetChanged();
        }
        isLoading = false;
        adapter.notifyItems(list);
    }

    public interface OnLoadMoreListener{
        void loadMore(int page);
    }


    public void setOnLoadMoreListener(final OnLoadMoreListener listener) {
        if(null == mloadMoreAdapter)
            mloadMoreAdapter = new LoadMoreAdapter();


        adapters.addAdapter(mloadMoreAdapter);
        setAdapter();
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mloadMoreAdapter.getItemCount() == 0)
                    return;
                LinearLayoutManager lm =  (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastVisiblePosition = lm.findLastVisibleItemPosition();
                if(lastVisiblePosition == adapters.getItemCount() - 1){
                    if(mloadMoreAdapter.getState() == 1){
                        if(!isLoading){
                            isLoading = true;
                            if(null != listener)
                                listener.loadMore(++page);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mloadMoreAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(View itemView, int position, Integer model) {
                if(mloadMoreAdapter.getState() == 2){
                    mloadMoreAdapter.setState(1);
                    isLoading = true;
                    mloadMoreAdapter.notifyDataSetChanged();
                    if(null != listener)
                        listener.loadMore(page);
                }
            }
        });
    }


    public void loadFailed() {
        if(page == 1)
            return;
        isLoading = false;
        mloadMoreAdapter.setState(2);
        mloadMoreAdapter.notifyDataSetChanged();
    }



}
