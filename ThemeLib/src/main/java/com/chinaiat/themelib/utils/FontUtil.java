package com.chinaiat.themelib.utils;


import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * replaceFont()
 * <p>
 * 替换一个页面中的所有字体
 * 用递归的方式去查找view是否是TextView或者TextView的子类，然后进行替换
 * 不过这种方法效率不高
 * 用法：
 * 在页面中
 * FontUtils.getInstance().replaceFontFromAsset(View root,  String fontPath)
 * <p>
 * <p>
 * modifyObjectField()
 * <p>
 * 替换App所有字体
 * 利用反射替换系统默认字体
 * 用法：
 * a.新建一个BaseApplication继承Application在onCreate方法中
 * FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this,"fonts/NotoSansCJKsc-Thin.otf");
 * b.在AppTheme中添加
 * <item name="android:typeface">monospace</item>
 * c.清单文件中使用BaseApplication
 *
 * @author: Bob
 * @date :2019/6/24 18:20
 */
public class FontUtil {

    private Map<String, SoftReference<Typeface>> mCache = new HashMap<>();
    private static FontUtil sSingleton = null;
    private static String ASSETS_FILE_NAME = "fonts/";
    private static String ASSETS_FILE_SUFFIX = ".ttf";
    public static Typeface DEFAULT = Typeface.DEFAULT;

    // disable instantiate
    private FontUtil() {
    }

    public static FontUtil getInstance() {
        // double check
        if (sSingleton == null) {
            synchronized (FontUtil.class) {
                if (sSingleton == null) {
                    sSingleton = new FontUtil();
                }
            }
        }
        return sSingleton;
    }

    public static String getTypefaceAssetsPath(String typefaceName) {
        return ASSETS_FILE_NAME + typefaceName + ASSETS_FILE_SUFFIX;
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath font file path relative to 'assets' directory.
     */
    public void replaceFontFromAsset(@NonNull View root, @NonNull String fontPath) {
        replaceFont(root, createTypefaceFromAsset(root.getContext(), fontPath));
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath font file path relative to 'assets' directory.
     * @param style    One of {@link Typeface#NORMAL}, {@link Typeface#BOLD}, {@link Typeface#ITALIC}, {@link Typeface#BOLD_ITALIC}
     */
    public void replaceFontFromAsset(@NonNull View root, @NonNull String fontPath, int style) {
        replaceFont(root, createTypefaceFromAsset(root.getContext(), fontPath), style);
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath The full path to the font data.
     */
    public void replaceFontFromFile(@NonNull View root, @NonNull String fontPath) {
        replaceFont(root, createTypefaceFromFile(fontPath));
    }

    /**
     * <p>Replace the font of specified view and it's children</p>
     *
     * @param root     The root view.
     * @param fontPath The full path to the font data.
     * @param style    One of {@link Typeface#NORMAL}, {@link Typeface#BOLD}, {@link Typeface#ITALIC}, {@link Typeface#BOLD_ITALIC}
     */
    public void replaceFontFromFile(@NonNull View root, @NonNull String fontPath, int style) {
        replaceFont(root, createTypefaceFromFile(fontPath), style);
    }

    /**
     * <p>Replace the font of specified view and it's children with specified typeface</p>
     */
    private void replaceFont(@NonNull View root, @NonNull Typeface typeface) {
        if (root == null || typeface == null) {
            return;
        }

        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's font
            TextView textView = (TextView) root;
            // Extract previous style of TextView
            int style = Typeface.NORMAL;
            if (textView.getTypeface() != null) {
                style = textView.getTypeface().getStyle();
            }
            textView.setTypeface(typeface, style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), typeface);
            }
        } // else return
    }

    /**
     * <p>Replace the font of specified view and it's children with specified typeface and text style</p>
     *
     * @param style One of {@link Typeface#NORMAL}, {@link Typeface#BOLD}, {@link Typeface#ITALIC}, {@link Typeface#BOLD_ITALIC}
     */
    private void replaceFont(@NonNull View root, @NonNull Typeface typeface, int style) {
        if (root == null || typeface == null) {
            return;
        }
        if (style < 0 || style > 3) {
            style = Typeface.NORMAL;
        }

        if (root instanceof TextView) { // If view is TextView or it's subclass, replace it's font
            TextView textView = (TextView) root;
            textView.setTypeface(typeface, style);
        } else if (root instanceof ViewGroup) { // If view is ViewGroup, apply this method on it's child views
            ViewGroup viewGroup = (ViewGroup) root;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                replaceFont(viewGroup.getChildAt(i), typeface, style);
            }
        } // else return
    }

    /**
     * <p>Create a Typeface instance with specified font file</p>
     *
     * @param fontPath font file path relative to 'assets' directory.
     * @return Return created typeface instance.
     */
    private Typeface createTypefaceFromAsset(Context context, String fontPath) {
        SoftReference<Typeface> typefaceRef = mCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            typefaceRef = new SoftReference<>(typeface);
            mCache.put(fontPath, typefaceRef);
        }
        return typeface;
    }

    private Typeface createTypefaceFromFile(String fontPath) {
        SoftReference<Typeface> typefaceRef = mCache.get(fontPath);
        Typeface typeface = null;
        if (typefaceRef == null || (typeface = typefaceRef.get()) == null) {
            typeface = Typeface.createFromFile(fontPath);
            typefaceRef = new SoftReference<>(typeface);
            mCache.put(fontPath, typefaceRef);
        }
        return typeface;
    }

    /**
     * <p>Replace system default font. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app font.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     *
     * @param context  {@link Context Context}
     * @param fontPath font file path relative to 'assets' directory.
     */
    public void replaceSystemDefaultFontFromAsset(@NonNull Context context, @NonNull String fontPath) {
        replaceSystemDefaultFont(createTypefaceFromAsset(context, fontPath));
    }

    /**
     * <p>Replace system default font. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app font.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     *
     * @param context  {@link Context Context}
     * @param fontPath The full path to the font data.
     */
    public void replaceSystemDefaultFontFromFile(@NonNull Context context, @NonNull String fontPath) {
        replaceSystemDefaultFont(createTypefaceFromFile(fontPath));
    }

    /**
     * <p>Replace system default font. <b>Note:</b>you should also add code below to your app theme in styles.xml. </p>
     * {@code <item name="android:typeface">monospace</item>}
     * <p>The best place to call this method is {@link Application#onCreate()}, it will affect
     * whole app font.If you call this method after view is visible, you need to invalid the view to make it effective.</p>
     */
    private void replaceSystemDefaultFont(@NonNull Typeface typeface) {
        modifyObjectField(null, "MONOSPACE", typeface);
    }

    private void modifyObjectField(Object obj, String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(obj, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}