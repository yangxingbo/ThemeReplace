package com.chinaiat.bob.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinaiat.bob.R;
import com.chinaiat.bob.adapter.HomeDataAdapter;
import com.chinaiat.bob.bean.FruitInfo;
import com.chinaiat.bob.db.CreateDataHelper;
import com.chinaiat.themelib.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

/**
 * @author: Bob
 * @date: 2019/5/31
 * @description :主页
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HomeDataAdapter homeDataAdapter;
    private ArrayList<FruitInfo> fruitInfoList;

    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            homeDataAdapter.setData(fruitInfoList);
            if (spotsDialog.isShowing()) {
                spotsDialog.dismiss();
            }
        }
    };

    private SpotsDialog spotsDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void bindButterKnife(BaseFragment baseFragment, View rootView) {
        ButterKnife.bind(baseFragment,rootView);
    }

    @Override
    protected void initData() {
        spotsDialog = new SpotsDialog(getActivity(), R.style.Custom);
        refreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<FruitInfo> infoList = (ArrayList<FruitInfo>) getActivity().getIntent().getSerializableExtra("homeFragmentData");
        if (null != infoList && infoList.size() > 0) {
            fruitInfoList = infoList;
        } else {
            fruitInfoList = CreateDataHelper.getInstance().getFruitInfoArrayList();
        }
        homeDataAdapter = new HomeDataAdapter(getActivity(), fruitInfoList);
        recyclerView.setAdapter(homeDataAdapter);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        spotsDialog.setMessage("正在获取数据");
        spotsDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addData();
            }
        }, 2000);
    }

    private void addData() {
        fruitInfoList.addAll(CreateDataHelper.getInstance().getFruitInfoArrayList());
        handler.sendEmptyMessage(0);
    }

    public ArrayList<FruitInfo> getFruitInfoList() {
        return fruitInfoList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FruitInfo fruitInfo = (FruitInfo) data.getSerializableExtra("cancelCollect");
        homeDataAdapter.refreshData(fruitInfo);
        for (FruitInfo info : fruitInfoList) {
            if (fruitInfo.getId().equals(info.getId())) {
                if (!fruitInfo.isCollect() == info.getIsCollect()) {
                    info.setCollect(fruitInfo.getIsCollect());
                }
                break;
            }
        }
    }
}
