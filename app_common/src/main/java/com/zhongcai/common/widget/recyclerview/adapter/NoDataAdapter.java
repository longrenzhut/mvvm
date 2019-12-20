package com.zhongcai.common.widget.recyclerview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.base.BaseAdapter;
import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.rxbus.RxBus;
import com.zhongcai.base.utils.StringUtils;
import com.zhongcai.common.R;
import com.zhongcai.common.helper.rxbus.Codes;
import com.zhongcai.common.widget.recyclerview.SuperRecyclerView;


/**
 * Created by zc3 on 2018/4/4.
 */

public class NoDataAdapter extends BaseAdapter<Integer> {

    private int layoutid;
    private RecyclerView rv;

    public NoDataAdapter(int layoutid, RecyclerView rv){
        if(layoutid == 0)
            this.layoutid = R.layout.adapter_no_data;
        else
            this.layoutid = layoutid;
        this.rv = rv;

    }

    public NoDataAdapter(RecyclerView rv){

        this(R.layout.adapter_no_data,rv);

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

    private String promptText = "";

    public void setPromptText(String text){
        this.promptText = text;
    }

    private int drawable = 0;

    public void setIcon(int drawable){
        this.drawable = drawable;
    }


    private int NoDataHeight = 0;

    public void setHeight(int height){
        this.NoDataHeight = height;
    }

    @Override
    public int inflaterItemLayout(int viewType) {
        return layoutid;
    }

    @Override
    public void bindData(BaseViewHolder holder, int position, Integer model) {
        if(NoDataHeight == 0) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, rv.getHeight());
            holder.itemView.setLayoutParams(lp);
        }
        else {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(-1, NoDataHeight);
            holder.itemView.setLayoutParams(lp);
        }
        if(R.layout.adapter_no_data != layoutid){
//            TextView mTvLogin = holder.getView(R.id.mTvLogin);
//            if(null != mTvLogin) {
//                mTvLogin.setSelected(true);
//                mTvLogin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RxBus.instance().post(Codes.CODE_2, 1);
//                    }
//                });
//            }

            return;
        }

        TextView mTvPrompt =  holder.getView(R.id.tv_text);
        ImageView mIvIocn =  holder.getView(R.id.iv_icon);
        if(0 != drawable)
            mIvIocn.setImageResource(drawable);
        mTvPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != listener)
                    listener.loadMore(1);
            }
        });

        if(StringUtils.isValue(promptText)){
            mTvPrompt.setText(promptText);
        }


    }
    SuperRecyclerView.OnLoadMoreListener listener;
    public void setOnLoadMoreListener(SuperRecyclerView.OnLoadMoreListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClickListener(View itemView, int pos, Integer model) {

    }
}
