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
import com.chinaiat.bob.adapter.CollectDataAdapter;
import com.chinaiat.bob.bean.FruitInfo;
import com.chinaiat.bob.db.DatabaseManager;
import com.chinaiat.themelib.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

/**
 * @author: Bob
 * @date: 2019/5/31
 * @description :收藏页
 */
public class CollectFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SpotsDialog spotsDialog;

    private CollectDataAdapter collectDataAdapter;
    private ArrayList<FruitInfo> allCollectFruitInfo;
    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            collectDataAdapter.setData(allCollectFruitInfo);
            if (spotsDialog.isShowing()) {
                spotsDialog.dismiss();
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collect;
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
        //设置布局方式,瀑布流2列，垂直
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<FruitInfo> infoList = (ArrayList<FruitInfo>) getActivity().getIntent().getSerializableExtra("collectFragmentData");
        if (null != infoList && infoList.size() > 0) {
            allCollectFruitInfo = infoList;
        } else {
            allCollectFruitInfo = (ArrayList<FruitInfo>) DatabaseManager.getInstance().getAllCollectFruitInfo();
        }
        collectDataAdapter = new CollectDataAdapter(getActivity(), allCollectFruitInfo);
        recyclerView.setAdapter(collectDataAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            refreshData();
        }
    }

    public ArrayList<FruitInfo> getAllCollectFruitInfo() {
        return allCollectFruitInfo;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        spotsDialog.setMessage("正在获取数据");
        spotsDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        }, 1000);
    }

    private void refreshData() {
        allCollectFruitInfo = (ArrayList<FruitInfo>) DatabaseManager.getInstance().getAllCollectFruitInfo();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FruitInfo fruitInfo = (FruitInfo) data.getSerializableExtra("cancelCollect");
        collectDataAdapter.removeItem(fruitInfo);
    }
}
