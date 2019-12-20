package com.zhongcai.common.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zhongcai.common.R;


public class ViewPageAdapter<T extends View> extends PagerAdapter {

	private T[] mArrays;
	private Context mContext;
	private ViewPager vViewPager;
	private ViewGroup vViewGroup;
	private TextView vTextView;
	private ImageView[] vTips;

	private int mNavigationWidth;//指示器宽高

	public ViewPageAdapter(T[] arrays) {
		this.mArrays = arrays;
	}

	public ViewPageAdapter(T[]arrays, Context context, ViewPager viewPager, ViewGroup viewGroup) {
		this.mContext = context;
		this.vViewPager = viewPager;
		this.mArrays = arrays;
		this.vViewGroup = viewGroup;

		mNavigationWidth = mContext.getResources().getDimensionPixelOffset(com.zhongcai.base.R.dimen.x8);

		initTips();
	}

	public ViewPageAdapter(T[]arrays, int position, ViewPager viewPager, TextView textView) {
		this.vViewPager = viewPager;
		this.mArrays = arrays;
		this.vTextView = textView;

		initTextTips(position);
	}

	private void initTextTips(int position) {
		if(mArrays != null && mArrays.length > 1){
			vTextView.setText(String.valueOf((position + 1) + "/" + mArrays.length));
		}else {
			vTextView.setVisibility(View.GONE);
		}
		vViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if(onPageSelectedListener != null){
					onPageSelectedListener.onPageSelected(position);
				}
				vTextView.setText(String.valueOf((position + 1) + "/" + mArrays.length));
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	private void initTips() {
		vViewGroup.removeAllViews();
		vTips = new ImageView[mArrays.length];
		for (int i = 0; i < vTips.length; i++) {
			ImageView imageView = new ImageView(mContext);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 0, 10, 0);
			params.width = mNavigationWidth;
			params.height = mNavigationWidth;
			params.gravity = Gravity.CENTER_VERTICAL;
			imageView.setLayoutParams(params);
			vTips[i] = imageView;
			if (i == 0) {
				vTips[i].setBackgroundResource(R.drawable.shape_indicator_focused);
			} else {
				vTips[i].setBackgroundResource(R.drawable.shape_indicator_unfocused);
			}
			vViewGroup.addView(imageView);
		}

		vViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if(onPageSelectedListener != null){
					onPageSelectedListener.onPageSelected(position);
				}
				setImageBackground(position % mArrays.length);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	@Override
	public int getCount() {
		return mArrays.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(mArrays[position
				% mArrays.length]);

	}
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(mArrays[position
				% mArrays.length], 0);
		return mArrays[position % mArrays.length];
	}

	private void setImageBackground(int selectItems) {
		for (int i = 0; i < vTips.length; i++) {
			if (i == selectItems) {
				vTips[i].setBackgroundResource(R.drawable.shape_indicator_focused);
			} else {
				vTips[i].setBackgroundResource(R.drawable.shape_indicator_unfocused);
			}
		}
	}


	public OnPageSelectedListener onPageSelectedListener;
	public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener){
		this.onPageSelectedListener = onPageSelectedListener;
	}

	public interface OnPageSelectedListener{
		void onPageSelected(int position);
	}

}
