package com.zhongcai.common.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.ViewPager;

import com.zhongcai.base.utils.GlideHelper;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import com.zhongcai.common.R;
import com.zhongcai.common.ui.adapter.ViewPageAdapter;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    public static void startImageActivity(Context context, View view, List<String> dataList, int position){
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("images", (ArrayList<String>) dataList);
        intent.putExtra("position", position);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        try {
            ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            context.startActivity(intent);
        }
//        context.overridePendingTransition(0, 0);
    }


    private ArrayList<String> mDataList;
    private int mPosition;
    private String mPath;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        mDataList = intent.getStringArrayListExtra("images");
        mCurrentPosition = mPosition = intent.getIntExtra("position", 0);

        initViewPager(mDataList);
    }




    PhotoView[] vImageViews;
    private void initViewPager(List<String> data) {
        TextView viewGroup =findViewById(R.id.viewGroup);
        ViewPager viewPager = findViewById(R.id.viewPager);
        vImageViews = new PhotoView[data.size()];
        for (int i = 0; i < vImageViews.length; i++) {
            String url = data.get(i);
            PhotoView photoView = new PhotoView(this);
            photoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            vImageViews[i] = photoView;
            GlideHelper.instance().load(photoView,url);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, 0);
                }
            });
        }
        ViewPageAdapter adapter = new ViewPageAdapter<ImageView>(vImageViews, mPosition, viewPager, viewGroup);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mPosition);
        adapter.setOnPageSelectedListener(new ViewPageAdapter.OnPageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }
        });
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }




}
