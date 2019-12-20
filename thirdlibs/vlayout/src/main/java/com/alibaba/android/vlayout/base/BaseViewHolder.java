package com.alibaba.android.vlayout.base;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2018/3/9.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;


    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<View>();
    }


    public <T extends View> T getView(int id){
        View view = mViews.get(id);
        if(null == view){
            view = itemView.findViewById(id);
            mViews.put(id,view);
        }
        return (T)view;

    }
}
