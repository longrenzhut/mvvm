package com.alibaba.android.vlayout.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/9.
 */

abstract public class BaseAdapter<T> extends DelegateAdapter.Adapter<BaseViewHolder>{

    private boolean isClick = true;

    private List<T> datas;

    public BaseAdapter() {
        this(true);
    }

    public BaseAdapter(boolean isClick) {
        this.isClick = isClick;
        datas = new ArrayList<T>();

    }

    public List<T> getDatas() {
        return datas;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(inflaterItemLayout(viewType)
                ,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if(isClick){
            holder.itemView.setOnClickListener(getOnClickListener(position));
            holder.itemView.setOnLongClickListener(getLongOnClickListener(position));
        }

        int index = position;
        if(getItemCount() != datas.size()){
            if (datas.size() == 0)
                index = position;
            else
                index = position % datas.size();
        }
        if(null != holder)
            bindData(holder,position,datas.get(index));
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View itemView, int position, T model);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View itemView, int position, T model);
    }

    protected OnItemClickListener mClickListener;

    public void setOnItemClickListener(OnItemClickListener mClickListener){
        this.mClickListener = mClickListener;
    }

    OnItemLongClickListener mClickLongListener;

    public void setOnItemLongClickListener(OnItemLongClickListener mClickLongListener){
        this.mClickLongListener = mClickLongListener;
    }

    private View.OnClickListener getOnClickListener(final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T model;
                if(getItemCount() != datas.size()){
                    model = datas.get(position%datas.size());
                }
                else{
                    model = datas.get(position);
                }
                if(null != mClickListener)
                    mClickListener.onItemClick(view,position,model);
                else
                    onItemClickListener(view,position,model);
            }
        };
    }

    private View.OnLongClickListener getLongOnClickListener(final int position){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                T model;
                if(getItemCount() != datas.size()){
                    model = datas.get(position%datas.size());
                }
                else{
                    model = datas.get(position);
                }
                if(null != mClickLongListener)
                    mClickLongListener.onItemLongClick(view,position,model);
                else
                    onItemLongClickListener(view,position,model);


                return false;
            }
        };
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }


    abstract public int inflaterItemLayout(int viewType);

    abstract public void bindData(BaseViewHolder holder, int position, T model);


    abstract public void onItemClickListener(View itemView, int pos, T model);

    public void onItemLongClickListener(View itemView, int pos, T model) {

    }

    public void add(T model){
        datas.add(model);
    }

    public void addAll(List<T> list){
        if(null != list){
            datas.addAll(list);
        }
    }

    public void clear(){
        datas.clear();
    }

    public void notifyItem(T model){
        add(model);
        notifyDataSetChanged();
    }

    public void notifyRemove(T model){
        datas.remove(model);
        notifyDataSetChanged();
    }

    public void remove(T model){
        datas.remove(model);
    }

    public void remove(int i){
        if(i >= 0 && i < datas.size())
            datas.remove(i);
    }

    public void notifyItems(List<T> list){
        addAll(list);
        notifyDataSetChanged();
    }

    public void notifyClear() {
        clear();
        notifyDataSetChanged();
    }


}




