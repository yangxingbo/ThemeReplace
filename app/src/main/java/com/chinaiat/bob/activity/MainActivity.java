package com.chinaiat.bob.activity;


import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.chinaiat.bob.R;
import com.chinaiat.bob.bean.FruitInfo;
import com.chinaiat.bob.fragment.CollectFragment;
import com.chinaiat.bob.fragment.ExploreFragment;
import com.chinaiat.bob.fragment.HomeFragment;
import com.chinaiat.themelib.base.BaseActivity;
import com.chinaiat.themelib.domain.EventMsg;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Bob
 * @date: 2019/5/30
 * @description: 主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.rl_menu)
    RelativeLayout rl_menu;

    private HomeFragment homeFragment;
    private ExploreFragment exploreFragment;
    private CollectFragment collectFragment;
    private Fragment fromFragment;
    private int TITLE_INDEX = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        initToolBar();
        homeFragment = new HomeFragment();
        jumpFragment(homeFragment, "home");
    }

    @Override
    protected void bindButterKnife(BaseActivity baseActivity) {
        ButterKnife.bind(baseActivity);
    }

    private void initToolBar() {
        toolbar.setTitle(R.string.home);
        setSupportActionBar(toolbar);
        //设置点击可以返回主页
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.home, R.string.menu) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(R.string.menu);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (TITLE_INDEX == 1) {
                    toolbar.setTitle(R.string.home);
                } else if (TITLE_INDEX == 2) {
                    toolbar.setTitle(R.string.explore);
                } else if (TITLE_INDEX == 3) {
                    toolbar.setTitle(R.string.collect);
                }
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_1:
                showToastShort("扫一扫");
                break;
            case R.id.menu_2:
                showToastShort("看一看");
                break;
            case R.id.menu_3:
                showToastShort("搜一搜");
                break;
            case R.id.menu_4:
                showToastShort("听一听");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.tv_home, R.id.tv_explore, R.id.tv_collect, R.id.tv_setting})
    public void onClickListener(View v) {
        int id = v.getId();
        if (id == R.id.tv_home) {
            TITLE_INDEX = 1;
            jumpFragment(homeFragment, "home");
        } else if (id == R.id.tv_explore) {
            if (null == exploreFragment) {
                exploreFragment = new ExploreFragment();
            }
            TITLE_INDEX = 2;
            jumpFragment(exploreFragment, "explore");
        } else if (id == R.id.tv_collect) {
            if (null == collectFragment) {
                collectFragment = new CollectFragment();
            }
            TITLE_INDEX = 3;
            jumpFragment(collectFragment, "collect");
        } else if (id == R.id.tv_setting) {
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
            ActivityCompat.startActivityForResult(this, new Intent(MainActivity.this, SettingActivity.class), 100, compat.toBundle());
            drawerLayout.closeDrawer(rl_menu);
        }
    }

    private void jumpFragment(Fragment to, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.add(R.id.rl_fragment, to, tag);
            transaction.show(to);
            if (null != fromFragment) {
                transaction.hide(fromFragment);
            }
        } else {
            if (!fromFragment.getTag().equals(to.getTag())) {
                transaction.show(to).hide(fromFragment);
            }
        }
        fromFragment = to;
        if (drawerLayout.isDrawerOpen(rl_menu)) {
            drawerLayout.closeDrawer(rl_menu);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(rl_menu)) {
            drawerLayout.closeDrawer(rl_menu);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == 103) {
            collectFragment.onActivityResult(requestCode, resultCode, data);
        }else if(requestCode == 104 && resultCode == 105){
            homeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onStyleChange(EventMsg eventMsg) {
        if (null != eventMsg) {
            String action = eventMsg.getAction();
            Intent intent = getIntent();
            //主题发生改变
            if (!TextUtils.isEmpty(action) && CHANGE_THEME.equals(action)) {
                ArrayList<FruitInfo> fruitInfoList = homeFragment.getFruitInfoList();
                if (null != fruitInfoList && fruitInfoList.size() != 0) {
                    intent.putExtra("homeFragmentData", fruitInfoList);
                }
                if (null != collectFragment) {
                    ArrayList<FruitInfo> allCollectFruitInfo = collectFragment.getAllCollectFruitInfo();
                    if (null != allCollectFruitInfo && allCollectFruitInfo.size() != 0) {
                        intent.putExtra("collectFragmentData", allCollectFruitInfo);
                    }
                }
                //字体发生改变
            } else if (!TextUtils.isEmpty(action) && CHANGE_TYPEFACE.equals(action)) {
                ArrayList<FruitInfo> fruitInfoList = homeFragment.getFruitInfoList();
                if (null != fruitInfoList && fruitInfoList.size() != 0) {
                    intent.putExtra("homeFragmentData", fruitInfoList);
                }
                if (null != collectFragment) {
                    ArrayList<FruitInfo> allCollectFruitInfo = collectFragment.getAllCollectFruitInfo();
                    if (null != allCollectFruitInfo && allCollectFruitInfo.size() != 0) {
                        intent.putExtra("collectFragmentData", allCollectFruitInfo);
                    }
                }
                //字体大小发生改变
            } else if (!TextUtils.isEmpty(action) && CHANGE_TYPEFACE_SIZE.equals(action)) {
//                intent.putExtra("homeFragmentData",);
                ArrayList<FruitInfo> fruitInfoList = homeFragment.getFruitInfoList();
                if (null != fruitInfoList && fruitInfoList.size() != 0) {
                    intent.putExtra("homeFragmentData", fruitInfoList);
                }
                if (null != collectFragment) {
                    ArrayList<FruitInfo> allCollectFruitInfo = collectFragment.getAllCollectFruitInfo();
                    if (null != allCollectFruitInfo && allCollectFruitInfo.size() != 0) {
                        intent.putExtra("collectFragmentData", allCollectFruitInfo);
                    }
                }
            }
            restartActivity(intent);
        }
    }
}
