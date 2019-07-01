package com.chinaiat.bob.activity;

import android.graphics.Typeface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chinaiat.bob.R;
import com.chinaiat.bob.ThemeReplaceApplication;
import com.chinaiat.bob.adapter.TypefaceAdapter;
import com.chinaiat.bob.db.SPHelper;
import com.chinaiat.bob.utils.ToastUtil;
import com.chinaiat.themelib.base.BaseActivity;
import com.chinaiat.themelib.domain.EventMsg;
import com.chinaiat.themelib.utils.FontUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date :2019/6/25 15:54
 * @description 字体更换
 */
public class TypefaceChangeActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.tv_show_txt)
    TextView tv_show_txt;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String currentTypeface = SPHelper.getTypefacePath();
    private String selectTypeface = currentTypeface;
    private TypefaceAdapter typefaceAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_typeface_change;
    }
    @Override
    protected void bindButterKnife(BaseActivity baseActivity) {
        ButterKnife.bind(baseActivity);
    }
    @Override
    protected void initData() {
        toolbar.setTitle("更换字体");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> typefaceList = new ArrayList<String>() {
            {
                add("幼圆");
                add("楷体");
                add("华文新宋");
                add("华文行楷");
                add("方正粗圆");
                add("苹果丽黑");
                add("华康少女字体");

            }
        };
        String typefacePath = SPHelper.getTypefacePath();
        if (!SPHelper.MONOSPACE.equals(typefacePath)) {
            for (int i = 0; i < typefaceList.size(); i++) {
                if (typefacePath.contains(typefaceList.get(i))) {
                    String remove = typefaceList.remove(i);
                    typefaceList.add(0, remove);
                    break;
                }
            }
        }
        typefaceAdapter = new TypefaceAdapter(typefaceList);
        typefaceAdapter.setListener(new TypefaceAdapter.onChangeListener() {
            @Override
            public void onChange(String typefaceName) {
                changeTxtShow(typefaceName);
            }
        });
        recyclerView.setAdapter(typefaceAdapter);
    }

    private void changeTxtShow(String typefaceName) {
        selectTypeface = FontUtil.getTypefaceAssetsPath(typefaceName);
        Typeface typeface = Typeface.createFromAsset(getAssets(), selectTypeface);
        tv_show_txt.setTypeface(typeface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_ok) {
            if (!selectTypeface.contains(currentTypeface)) {
                //修改整个App字体,但是不会刷新界面
                FontUtil.getInstance().replaceSystemDefaultFontFromAsset(ThemeReplaceApplication.context, selectTypeface);
                //刷新所有界面字体
                EventBus.getDefault().postSticky(new EventMsg(CHANGE_TYPEFACE));
                SPHelper.setTypefacePath(selectTypeface);
                //避免字体设置里面字体样式发生改变,重新刷新Adapter
                typefaceAdapter.refreshData(selectTypeface);
                ToastUtil.showShortToast("字体更改成功");
                finish();
            } else {
                ToastUtil.showShortToast("当前字体未更改");
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
