package com.chinaiat.bob.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chinaiat.bob.R;
import com.chinaiat.bob.ThemeReplaceApplication;
import com.chinaiat.themelib.helper.SPHelper;


/**
 * @author: Bob
 * @date: 2019/6/24
 * @description :主题颜色选取dialog
 */
public class ThemeChoicePreference extends DialogPreference {

    private RadioGroup mGroup1;
    private RadioGroup mGroup2;
    private RadioGroup mGroup3;
    private RadioGroup mGroup4;

    //    已保存的主题
    private int mTheme;

    //    所有的radioButton
    private int[] mRdoBtns;

    private boolean changeGroup = false;
    //    现在的value
    private int mCurrentValue;
    //    新的value
    private int mNewValue;

    private static final int DEFAULT_VALUE = SPHelper.getTheme();

    public ThemeChoicePreference(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mTheme = SPHelper.getTheme();
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        View view = LayoutInflater.from(ThemeReplaceApplication.context).inflate(R.layout.dialog_theme_choice, null);
        mGroup1 = (RadioGroup) view.findViewById(R.id.group1);
        mGroup2 = (RadioGroup) view.findViewById(R.id.group2);
        mGroup3 = (RadioGroup) view.findViewById(R.id.group3);
        mGroup4 = (RadioGroup) view.findViewById(R.id.group4);

        mRdoBtns = new int[]{R.id.rdobtn_1, R.id.rdobtn_2, R.id.rdobtn_3, R.id.rdobtn_4, R.id.rdobtn_5
                , R.id.rdobtn_6, R.id.rdobtn_7, R.id.rdobtn_8, R.id.rdobtn_9, R.id.rdobtn_10
                , R.id.rdobtn_11, R.id.rdobtn_12, R.id.rdobtn_13, R.id.rdobtn_14, R.id.rdobtn_15
                , R.id.rdobtn_16, R.id.rdobtn_17, R.id.rdobtn_18, R.id.rdobtn_19, R.id.rdobtn_20};
        for (int i = 0; i < ThemeReplaceApplication.mThemes.length; i++) {
            if (mTheme == ThemeReplaceApplication.mThemes[i]) {
                RadioButton radioButton = (RadioButton) view.findViewById(mRdoBtns[i]);
                radioButton.setChecked(true);
                break;
            }
        }
        mGroup1.setOnCheckedChangeListener(mCheckedChangeListener);
        mGroup2.setOnCheckedChangeListener(mCheckedChangeListener);
        mGroup3.setOnCheckedChangeListener(mCheckedChangeListener);
        mGroup4.setOnCheckedChangeListener(mCheckedChangeListener);
        builder.setView(view).setTitle("更换主题").setNegativeButton("", null).setPositiveButton("", null);
    }

    RadioGroup.OnCheckedChangeListener mCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group != null && checkedId != -1 && changeGroup == false) {
                for (int i = 0; i < mRdoBtns.length; i++) {
                    if (checkedId == mRdoBtns[i]) {
                        mNewValue = ThemeReplaceApplication.mThemes[i];
                        group.check(mRdoBtns[i]);
                        SPHelper.setTheme(i);
                        persistInt(i);
                        getDialog().cancel();
                    }
                }
            }
        }
    };

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        View mContentView = LayoutInflater.from(getContext()).inflate(R.layout.preference_theme_change, parent, false);
        return mContentView;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            // Restore existing state
            mCurrentValue = this.getPersistedInt(DEFAULT_VALUE);
            mNewValue = mCurrentValue;
        } else {
            // Set default state from the XML attribute
            mCurrentValue = (Integer) defaultValue;
            persistInt(mCurrentValue);
            mNewValue = mCurrentValue;
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, DEFAULT_VALUE);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        // When the user selects "OK", persist the new value
        Log.d("currentValue", mCurrentValue + "");
        Log.d("newValue", mNewValue + "");
    }
}
