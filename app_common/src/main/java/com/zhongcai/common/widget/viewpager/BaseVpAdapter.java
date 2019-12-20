package com.zhongcai.common.widget.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class BaseVpAdapter extends FragmentPagerAdapter {


    private List<Fragment> list;

    public BaseVpAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.list = list;
    }


    private List<String> titles;

    public  BaseVpAdapter setTitles(List<String> titles){
        this.titles = titles;
        return this;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(null == titles)
            return super.getPageTitle(position);

        return titles.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return null == list? 0 : list.size();
    }
}
