package com.zhongcai.base.theme.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongcai.base.R;
import com.zhongcai.base.base.activity.AbsActivity;
import com.zhongcai.base.https.FileLoadModel;
import com.zhongcai.base.rxbus.RxBus;
import com.zhongcai.base.utils.BaseUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zc3 on 2019/7/22.
 */

public class ProgressLayout extends RelativeLayout implements View.OnClickListener {
    public ProgressLayout(Context context) {
        super(context);
        init(context);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private RelativeLayout rly_progress_failed;
    private ProgressBar pb_loading;
    private ImageView iv_cancel;
    private ImageView iv_failed;
    private RelativeLayout rly_progress;
    private TextView tv_count;
    private TextView tv_count_failed;

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_progress,this,true);
        rly_progress_failed = findViewById(R.id.rly_progress_failed);
        rly_progress = findViewById(R.id.rly_progress);
        pb_loading = findViewById(R.id.pbloading);
        iv_cancel = findViewById(R.id.iv_cancel);
        tv_count = findViewById(R.id.tv_count);
        tv_count_failed = findViewById(R.id.tv_count_failed);
        iv_failed = findViewById(R.id.iv_failed);

    }

    public void setOnClickListener(OnClickListener listener){
        rly_progress_failed.setOnClickListener(listener);
    }

    public boolean isUping(){
        return rly_progress.getVisibility() != View.GONE;
    }

    public interface OnStopListener{
        void onStop();
    }

    private OnStopListener listener;

    public void setOnStopListener(OnStopListener listener){
        this.listener = listener;
    }

    private Disposable mSubscription;
    int len = 0;
    int count = 0;
    public void setProgress(List<String> list){
        count  = list.size();
        if(count == 0)
            return;
        len = 0;
        tv_count.setText("正在上传0/"+count +"张图片…");
        BaseUtils.setVisible(rly_progress_failed,-1);
        BaseUtils.setVisible(rly_progress,1);
        pb_loading.setMax(100*count);
        pb_loading.setProgress(0);
        iv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                RxBus.instance().post(6000,1);
//                mPresenter.stopUpFile()
                if(null != listener)
                    listener.onStop();
                BaseUtils.setVisible(rly_progress,-1);
            }
        });
        if(null == mSubscription) {
            mSubscription = RxBus.instance().toFlowable(FileLoadModel.class)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<FileLoadModel>() {

                        @Override
                        public void accept(FileLoadModel it) throws Exception {

                            Long pt = it.getProgress() * 100 / it.getTotal();
                            int pr = Integer.parseInt(pt.toString());
                            pb_loading.setProgress(pr + len * 100);
                            if (it.getDone()) {
                                len++;
                                tv_count.setText("正在上传" + len + "/" + count + "张图片…");
                            }
                        }

                    });
        }
    }

    public void setRxBus(AbsActivity act){
        //
        RxBus.instance().registerRxBus(act, 5000, new RxBus.OnRxBusListener<Integer>() {

            @Override
            public void OnRxBus(Integer data) {
                len = 0;
                BaseUtils.setVisible(iv_failed,1);
                if(data == 0){//上传成功
                    BaseUtils.setVisible(rly_progress,-1);
                    BaseUtils.setVisible(rly_progress_failed,-1);
                }
                else{
                    BaseUtils.setVisible(rly_progress,-1);
                    BaseUtils.setVisible(rly_progress_failed,1);

                    tv_count_failed.setText("图片上传失败，请重试");
                    iv_failed.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            BaseUtils.setVisible(rly_progress_failed,-1);
                        }
                    });
                }
            }
        });

        RxBus.instance().registerRxBus(act, 6000, new RxBus.OnRxBusListener<String>() {

            @Override
            public void OnRxBus(String data) {
                BaseUtils.setVisible(iv_failed,-1);
                if(data.isEmpty()){//上传成功
                    BaseUtils.setVisible(rly_progress_failed,-1);
                }
                else{
                    BaseUtils.setVisible(rly_progress_failed,1);

                    tv_count_failed.setText(data);
                }
            }
        });

    }


    @Override
    protected void onDetachedFromWindow() {
        if(null != mSubscription)
            mSubscription.dispose();
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {

    }
}
