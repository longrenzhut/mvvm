package com.zhongcai.common.widget.recyclerview.adapter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.base.BaseAdapter;
import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.common.R;


/**
 * Created by zhutao on 2018/3/10.
 * 加载更多
 */

public class LoadMoreAdapter extends BaseAdapter<Integer> {


    private int state = 1;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LoadMoreAdapter(){
//        add(1);
    }

    public void hide(){
        clear();
        notifyDataSetChanged();
    }


    public void show(){
        if(getItemCount() ==0) {
            add(1);
            notifyDataSetChanged();
        }
    }

    String textThree = "我也是有底线的~";

    public void setTextThree(String text){
        textThree = text;
    }

    @Override
    public int inflaterItemLayout(int viewType) {
        return R.layout.adapter_load_more;
    }

    @Override
    public void bindData(BaseViewHolder holder, int position, Integer model) {

        ProgressBar mPbLoading = holder.getView(R.id.pb_loading);
        TextView mTvLoading = holder.getView(R.id.tv_loading);
        switch (state){
            case 1:
                BaseUtils.setVisible(mPbLoading,1);
                mTvLoading.setText("");
                break;
            case 2:
                BaseUtils.setVisible(mPbLoading,-1);
                mTvLoading.setText("网络有点问题,点击重新加载");
                break;
            case 3:
                BaseUtils.setVisible(mPbLoading,-1);
                mTvLoading.setText(textThree);
                break;
        }

    }

    @Override
    public void onItemClickListener(View itemView, int pos, Integer model) {

    }
}
