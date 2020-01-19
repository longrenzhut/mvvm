package com.zhongcai.base.theme.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhongcai.base.R;
import com.zhongcai.base.rxbus.RxBus;
import com.zhongcai.base.utils.BaseUtils;

/**
 * Created by zc3 on 2019/10/22.
 */

public class PromptLayout extends RelativeLayout   {
    public PromptLayout(Context context) {
        super(context);
        init(context);
    }

    public PromptLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PromptLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    RelativeLayout rly_progress_failed;
    TextView tv_count_failed;

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_prompt,this,true);
        rly_progress_failed = findViewById(R.id.rly_progress_failed);
        tv_count_failed = findViewById(R.id.tv_count_failed);
//        iv_failed = findViewById(R.id.iv_failed);


    }

    public void setRxBus(AbsActivity act){
        //
        RxBus.instance().registerRxBus(act, 6000, new RxBus.OnRxBusListener<String>() {

            @Override
            public void OnRxBus(String data) {
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

    public void setContent(String text){
        tv_count_failed.setText(text);
    }

    public void hide(){
        BaseUtils.setVisible(rly_progress_failed,-1);
    }


}
