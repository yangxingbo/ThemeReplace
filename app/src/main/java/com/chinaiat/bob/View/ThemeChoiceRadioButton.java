package com.chinaiat.bob.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.chinaiat.bob.R;
import com.chinaiat.themelib.utils.SizeUtils;

/**
 * @author: Bob
 * @date: 2019/6/24
 * @description :主题颜色原形RadioButton
 */
@SuppressLint("AppCompatCustomView")
public class ThemeChoiceRadioButton extends RadioButton {

    private int backgroundColor;

    public ThemeChoiceRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ThemeChoiceRadioButton);
        backgroundColor = ta.getColor(R.styleable.ThemeChoiceRadioButton_backgroundColor, 0);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, SizeUtils.dp2px(20), paint);
        if (isChecked()) {
            Bitmap bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.mipmap.ic_check_white_24dp);
            canvas.drawBitmap(bitmap, getMeasuredWidth() / 2 - SizeUtils.dp2px(12), getMeasuredHeight() / 2 - SizeUtils.dp2px(12), paint);
        }
    }

    @Override
    public void toggle() {
        super.toggle();
    }
}
