package com.zhongcai.common.widget.dialog;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.base.BaseAdapter;
import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.base.widget.BaseDialogFra;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.common.R;

import java.util.Arrays;
import java.util.List;


/**
 * Created by zc3 on 2018/7/12.
 */

public class BottomDialog extends BaseDialogFra {
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_common_bottom;
    }


    private BottomAdapter mAdapter;
    RecyclerView mRvContent;
    @Override
    protected void initView() {
         mRvContent = findVId(R.id.rv_content);

        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new BottomAdapter();
        mRvContent.setAdapter(mAdapter);
        setDatas(datas);
        findVId(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    List<String> datas;

    public void setDatas(List<String> list){
        this.datas = list;
        if(null != mAdapter) {
            if(null != list &&list.size() > 7){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, BaseUtils.getDimen(R.dimen.y60) * 7);
                mRvContent.setLayoutParams(lp);
            }
            else{
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1,-2);
                mRvContent.setLayoutParams(lp);
            }

            mAdapter.clear();
            mAdapter.notifyItems(list);
        }
    }


    public void setDatas(String[] datas){
        this.datas = Arrays.asList(datas);
        if(null != mAdapter) {
            mAdapter.clear();
            mAdapter.notifyItems(this.datas);
        }
    }
    BaseAdapter.OnItemClickListener<String> listener;

    public void setonItemClickListener(BaseAdapter.OnItemClickListener<String> listener){
        this.listener = listener;
    }


    class BottomAdapter extends BaseAdapter<String>{

        @Override
        public int inflaterItemLayout(int viewType) {
            return R.layout.adapter_bottom;
        }

        @Override
        public void bindData(BaseViewHolder holder, int position, String model) {
            TextView mTvContent = holder.getView(R.id.tv_content);
            View viewline = holder.getView(R.id.view_line);
            mTvContent.setText(model);

            if(position == getItemCount() - 1){
                BaseUtils.setVisible(viewline,-1);
            }
            else{
                BaseUtils.setVisible(viewline,1);
            }
        }

        @Override
        public void onItemClickListener(View itemView, int pos, String model) {
            BottomDialog.this.dismiss();
            if(null != listener)
                listener.onItemClick(itemView,pos,model);

        }
    }
}
