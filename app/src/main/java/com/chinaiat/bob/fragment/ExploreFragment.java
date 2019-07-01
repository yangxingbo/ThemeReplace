package com.chinaiat.bob.fragment;

import android.view.View;

import com.chinaiat.bob.R;
import com.chinaiat.themelib.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date: 2019/5/31
 * @description :发现页
 */
public class ExploreFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void bindButterKnife(BaseFragment baseFragment, View rootView) {
        ButterKnife.bind(baseFragment,rootView);
    }
}
