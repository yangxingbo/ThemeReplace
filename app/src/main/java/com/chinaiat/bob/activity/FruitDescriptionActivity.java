package com.chinaiat.bob.activity;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinaiat.bob.R;
import com.chinaiat.bob.bean.FruitInfo;
import com.chinaiat.bob.db.DatabaseManager;
import com.chinaiat.themelib.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date: 2019/6/5
 * @description :水果描述
 */
public class FruitDescriptionActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @BindView(R.id.iv_fruit)
    ImageView iv_fruit;
    @BindView(R.id.tv_fruit_description)
    TextView tv_fruit_description;

    private boolean isCollect = false;
    private FruitInfo fruitInfo;
    private String isWho;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fruit_description;
    }

    @Override
    protected void bindButterKnife(BaseActivity baseActivity) {
        ButterKnife.bind(baseActivity);
    }

    @Override
    protected void initData() {
        isWho = getIntent().getStringExtra("isWho");
        fruitInfo = (FruitInfo) getIntent().getSerializableExtra("fruitInfo");
        isCollect = fruitInfo.isCollect();
        toolbar.setTitle(fruitInfo.getFruitName() + "详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCancelResult();
                ActivityCompat.finishAfterTransition(FruitDescriptionActivity.this);
            }
        });
        Glide.with(this).load(fruitInfo.getImgResId()).into(iv_fruit);
        tv_fruit_description.setText(fruitInfo.getFruitDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collect, menu);
        MenuItem item = menu.findItem(R.id.action_collect);
        item.setIcon(fruitInfo.isCollect() ? R.mipmap.collection_select : R.mipmap.collection_normal);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        setCancelResult();
        super.onBackPressed();
    }

    private void setCancelResult() {
        //CollectFragment中查看取消收藏
        if (isWho.equals("collect") && !fruitInfo.isCollect()) {
            setResult(103, new Intent().putExtra("cancelCollect", fruitInfo));
            //HomeFragment 中查看添加收藏
        } else if (isWho.equals("home")) {
            setResult(105, new Intent().putExtra("cancelCollect", fruitInfo));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_collect) {
            if (isCollect) {
                fruitInfo.setCollect(false);
                item.setIcon(R.mipmap.collection_normal);
                showToastShort("取消收藏");
            } else {
                fruitInfo.setCollect(true);
                item.setIcon(R.mipmap.collection_select);
                showToastShort("收藏成功");
            }
            DatabaseManager.getInstance().saveFruit(fruitInfo);
            isCollect = !isCollect;
        }
        return super.onOptionsItemSelected(item);
    }
}
