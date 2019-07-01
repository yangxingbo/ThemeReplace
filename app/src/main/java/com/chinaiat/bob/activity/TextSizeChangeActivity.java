package com.chinaiat.bob.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.chinaiat.bob.R;
import com.chinaiat.bob.View.FontSliderBar;
import com.chinaiat.bob.db.SPHelper;
import com.chinaiat.themelib.base.BaseActivity;
import com.chinaiat.themelib.domain.EventMsg;
import com.chinaiat.themelib.utils.SizeUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date :2019/6/26 16:37
 */
public class TextSizeChangeActivity extends BaseActivity {
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.fontSliderBar)
    FontSliderBar fontSliderBar;
    @BindView(R.id.tv_chatcontent1)
    TextView tvContent1;
    @BindView(R.id.tv_chatcontent)
    TextView tvContent2;
    @BindView(R.id.tv_chatcontent3)
    TextView tvContent3;
    private float textsize1, textsize2, textsize3;
    private float textSizef;//缩放比例
    private float currentIndex = SPHelper.getTypefaceSize();
    private int FONT_SLIDER_BAR = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_size_show;
    }
    @Override
    protected void bindButterKnife(BaseActivity baseActivity) {
        ButterKnife.bind(baseActivity);
    }
    @Override
    protected void initData() {
        toolbar.setTitle("更改字体大小");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        textSizef = 1 + currentIndex * 0.1f;
        textsize1 = tvContent1.getTextSize() / textSizef;
        textsize2 = tvContent2.getTextSize() / textSizef;
        textsize3 = tvContent3.getTextSize() / textSizef;
        if (currentIndex == 0.9f) {
            FONT_SLIDER_BAR = 0;
        } else if (currentIndex == 1.0f) {
            FONT_SLIDER_BAR = 1;
        } else if (currentIndex == 1.1f) {
            FONT_SLIDER_BAR = 2;
        } else if (currentIndex == 1.2f) {
            FONT_SLIDER_BAR = 3;
        } else if (currentIndex == 1.3f) {
            FONT_SLIDER_BAR = 4;
        } else if (currentIndex == 1.4f) {
            FONT_SLIDER_BAR = 5;
        }
        fontSliderBar.setTickCount(6)
                .setTickHeight(SizeUtils.convertDip2Px(this, 15))
                .setBarColor(Color.GRAY)
                .setTextColor(Color.BLACK)
                .setTextPadding(SizeUtils.convertDip2Px(this, 10))
                .setTextSize(SizeUtils.convertDip2Px(this, 14))
                .setThumbRadius(SizeUtils.convertDip2Px(this, 10))
                .setThumbColorNormal(Color.GRAY).setThumbColorPressed(Color.GRAY)
                .setOnSliderBarChangeListener(new FontSliderBar.OnSliderBarChangeListener() {
                    @Override
                    public void onIndexChanged(FontSliderBar rangeBar, int index) {
                        if (index > 5) {
                            return;
                        }
                        index = index - 1;
                        float textSizeFloat = 1 + index * 0.1f;
                        setTextSize(textSizeFloat);
                    }
                }).setThumbIndex(FONT_SLIDER_BAR).withAnimation(false).applay();
    }

    private void setTextSize(float textSize) {
        //改变当前页面的字体大小
        tvContent1.setTextSize(SizeUtils.px2sp(textsize1 * textSize));
        tvContent2.setTextSize(SizeUtils.px2sp(textsize2 * textSize));
        tvContent3.setTextSize(SizeUtils.px2sp(textsize3 * textSize));
        SPHelper.setTypefaceSize(textSize);
    }

    @Override
    public void onBackPressed() {
        finishActivity();
        super.onBackPressed();
    }

    public void finishActivity() {
        if (currentIndex != SPHelper.getTypefaceSize()) {
            EventBus.getDefault().postSticky(new EventMsg(CHANGE_TYPEFACE_SIZE));
        }
        finish();
    }
}
