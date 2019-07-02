package com.chinaiat.themelib.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chinaiat.themelib.domain.EventMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * @author: Bob
 * @date: 2019/5/29
 * @description : BaseFragment
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 改变主题Flag
     */
    protected String CHANGE_THEME = "changeTheme";
    /**
     * 改变字体Flag
     */
    protected String CHANGE_TYPEFACE = "changeTypeface";
    /**
     * 改变字体大小Flag
     */
    protected String CHANGE_TYPEFACE_SIZE = "changeTypefaceSize";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        bindButterKnife(rootView);
        return rootView;
    }

    protected void bindButterKnife(View rootView) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        initData();
    }

    protected abstract int getLayoutId();

    protected void initData() {

    }


    /**
     * 使用EventBus通信改变页面的主题,字体,字体大小
     *
     * @param eventMsg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStyleChange(EventMsg eventMsg) {

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

}
