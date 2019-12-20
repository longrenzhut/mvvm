package com.zhongcai.common.widget.optaddress;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.base.BaseAdapter;
import com.alibaba.android.vlayout.base.BaseViewHolder;
import com.zhongcai.base.utils.BaseUtils;
import com.zhongcai.common.R;
import com.zhongcai.common.helper.db.AddrItemModelDao;
import com.zhongcai.common.helper.db.helper.DbHelper;
import com.zhongcai.common.utils.CommonUtils;
import com.zhongcai.common.widget.dialog.CommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址选择器
 */
public class OptAddressDialog extends CommonDialog {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_opt_address;
    }


    private TextView mTvCancel;
    private TextView mTvSelector;
    private RecyclerView mRvTab;
    private RecyclerView mRvAddress;
    private AddrInfoAdapter mAddrInfoAdapter;
    private AddrTabAdapter mAddrTabAdpter;

    private  AddrItemModel mDefalutModel;

    private List<AddrItemModel> curTabList;

    @Override
    protected void initView() {
        mDefalutModel = new AddrItemModel();
        mDefalutModel.setId(-1);
        mDefalutModel.setName("");
        mDefalutModel.setPid(-1);

        setAdapter(new ViewHoldListener() {
            @Override
            public void onBindViewHolder(BaseViewHolder holder) {
                mTvCancel = holder.getView(R.id.mTvCancel);
                mTvSelector = holder.getView(R.id.mTvSelector);
                mRvTab = holder.getView(R.id.mRvTab);
                mRvAddress = holder.getView(R.id.mRvAddress);

            }
        });

        super.initView();
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mTvSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvSelector.isSelected()) {
                    AddressModel amodel = new AddressModel();

                    List<AddrItemModel> tablist = mAddrTabAdpter.getDatas();
                    for(int i = 0;i < tablist.size() - 1;i ++){
                        if(i == 0){
                            amodel.setProvince(tablist.get(i).getName());
                            amodel.setProvinceId(tablist.get(i).getId() + "");
                        }
                        else if(i == 1){
                            amodel.setCity(tablist.get(i).getName());
                            amodel.setCityId(tablist.get(i).getId() + "");
                        }
                        if(i == 2){
                            amodel.setArea(tablist.get(i).getName());
                            amodel.setAreaId(tablist.get(i).getId() + "");

                        }
                    }
                    if(null != listener)
                        listener.onSelectAddr(amodel);
                    dismiss();

                }
            }
        });

        mAddrTabAdpter = new AddrTabAdapter();
        mRvTab.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mRvTab.setAdapter(mAddrTabAdpter);
        mAddrTabAdpter.isSelected = false;
        mAddrTabAdpter.clear();
        mAddrTabAdpter.notifyItem(mDefalutModel);
        mAddrTabAdpter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<AddrItemModel>() {

            @Override
            public void onItemClick(View itemView, int position, AddrItemModel model) {
                if(model.getId() == -1)
                    return;
                if(mAddrTabAdpter.isSelected && position == mAddrTabAdpter.getItemCount() - 1)
                    return;
                mAddrTabAdpter.isSelected = false;
                mTvSelector.setSelected(false);
                if(null == curTabList)
                    curTabList = new ArrayList<>();
                curTabList.clear();


                List<AddrItemModel> list = getAddrList(model.getPid());

                for(int i = 0;i < position; i ++){
                    curTabList.add(mAddrTabAdpter.getDatas().get(i));
                }
                curTabList.add(mDefalutModel);
                mAddrTabAdpter.clear();
                mAddrTabAdpter.notifyItems(curTabList);

                mAddrInfoAdapter.clear();
                mAddrInfoAdapter.notifyItems(list);

            }
        });

        mRvAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvAddress.setAdapter(mAddrInfoAdapter = new AddrInfoAdapter());

        List<AddrItemModel> proviceList = getAddrList(0);
        mAddrInfoAdapter.notifyItems(proviceList);

    }

    private OnSelectAddrListener listener;

    public void setOnSelectAddrListener(OnSelectAddrListener listener){
        this.listener = listener;
    }

    public interface OnSelectAddrListener{
        void onSelectAddr(AddressModel model);
    }

    private List<AddrItemModel> getAddrList(int id){
        List<AddrItemModel> list = DbHelper.getLocalDaoSession().getAddrItemModelDao().queryBuilder()
                .where(AddrItemModelDao.Properties.Pid.eq(id)).list();

        return list;
    }

    class AddrInfoAdapter extends BaseAdapter<AddrItemModel>{

        @Override
        public int inflaterItemLayout(int viewType) {
            return R.layout.adapter_addr_info;
        }

        @Override
        public void bindData(BaseViewHolder holder, int position, AddrItemModel model) {
            TextView mTvContent = holder.getView(R.id.mTvContent);
            ImageView mIvSelector = holder.getView(R.id.mIvSelector);

            mTvContent.setText(model.getName());
            if (model.getSelected() == 1) {
                BaseUtils.setTvColor(mTvContent, R.color.cl_067C5F);
                BaseUtils.setVisible(mIvSelector, 1);
            } else {
                BaseUtils.setTvColor(mTvContent, R.color.cl_111928);
                BaseUtils.setVisible(mIvSelector, -1);
            }

        }

        @Override
        public void onItemClickListener(View itemView, int pos, AddrItemModel model) {
            if(mAddrTabAdpter.getItemCount() == 3){
                if(!mAddrTabAdpter.isSelected) {
                    mAddrTabAdpter.remove(mDefalutModel);
                    mAddrTabAdpter.notifyItem(model);
                }
                else{
                    mAddrTabAdpter.remove(CommonUtils.getListLast(mAddrTabAdpter.getDatas()));
                    mAddrTabAdpter.notifyItem(model);
                }
                mAddrTabAdpter.isSelected =  true;
                mTvSelector.setSelected(true);
                return;
            }

            List<AddrItemModel> list = getAddrList(model.getId());
            if(null != list &&list.size() > 0){
                mAddrTabAdpter.remove(mDefalutModel);
                mAddrTabAdpter.add(model);
                mAddrTabAdpter.notifyItem(mDefalutModel);

                mAddrInfoAdapter.clear();
                mAddrInfoAdapter.notifyItems(list);
            }
            else{
                if(!mAddrTabAdpter.isSelected) {
                    mAddrTabAdpter.remove(mDefalutModel);
                    mAddrTabAdpter.notifyItem(model);
                }
                else{
                    mAddrTabAdpter.remove(CommonUtils.getListLast(mAddrTabAdpter.getDatas()));
                    mAddrTabAdpter.notifyItem(model);
                }
            }
            mAddrTabAdpter.isSelected =  null == list || list.size() == 0;
        }
    }


    class  AddrTabAdapter extends BaseAdapter<AddrItemModel>{


        boolean isSelected = false;

        @Override
        public int inflaterItemLayout(int viewType) {
            return R.layout.adapter_addr_tab;
        }

        @Override
        public void bindData(BaseViewHolder holder, int position, AddrItemModel model) {
            TextView mTvContent = holder.getView(R.id.mTvContent);
            RelativeLayout mRlySelector = holder.getView(R.id.mRlySelector);

            mTvContent.setText(model.getName());
            if(isSelected){
                BaseUtils.setVisible(mRlySelector,-1);
                BaseUtils.setVisible(mTvContent,1);
            }
            else{
                if(position == getItemCount() - 1){
                    BaseUtils.setVisible(mRlySelector,1);
                    BaseUtils.setVisible(mTvContent,-1);
                }
                else{
                    BaseUtils.setVisible(mRlySelector,-1);
                    BaseUtils.setVisible(mTvContent,1);
                }
            }

        }

        @Override
        public void onItemClickListener(View itemView, int pos, AddrItemModel model) {

        }
    }
}
