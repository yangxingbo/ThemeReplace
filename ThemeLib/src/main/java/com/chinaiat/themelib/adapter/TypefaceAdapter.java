package com.chinaiat.themelib.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinaiat.themelib.R;
import com.chinaiat.themelib.ThemeLibApplication;
import com.chinaiat.themelib.utils.FontUtil;

import java.util.List;

/**
 * @author: Bob
 * @date: 2019/6/3
 * @description :TypefaceChangeActivity适配器
 */
public class TypefaceAdapter extends RecyclerView.Adapter<TypefaceAdapter.typefaceHolder> {

    private List<String> typefaceList;
    private onChangeListener listener;
    private int mPosition = 0;
    private boolean isFirst = true;

    public TypefaceAdapter(List<String> typefaceList) {
        this.typefaceList = typefaceList;
    }

    @NonNull
    @Override
    public typefaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new typefaceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typeface_name, parent, false));
    }

    public static Typeface getTypeface(String typefaceName) {
        return Typeface.createFromAsset(ThemeLibApplication.context.getAssets(), FontUtil.getTypefaceAssetsPath(typefaceName));
    }

    @Override
    public void onBindViewHolder(@NonNull final typefaceHolder holder, final int position) {
        final String typefaceName = typefaceList.get(position);
        holder.tv_name.setText(typefaceName);
        holder.tv_name.setTypeface(getTypeface(typefaceName));
        if (position == 0 && isFirst) {
            holder.iv_select.setVisibility(View.VISIBLE);
        } else {
            holder.iv_select.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != mPosition) {
                    isFirst = false;
                    holder.iv_select.setVisibility(View.VISIBLE);
                    if (null != listener) {
                        listener.onChange(typefaceName);
                    }
                    notifyItemChanged(mPosition);
                    mPosition = position;
                }
            }
        });
    }

    public void refreshData(String resetData) {
        isFirst = true;
        mPosition = 0;
        for (int i = 0; i < typefaceList.size(); i++) {
            if (resetData.contains(typefaceList.get(i))) {
                String remove = typefaceList.remove(i);
                typefaceList.add(0, remove);
                break;
            }
        }
        notifyDataSetChanged();
    }


    public interface onChangeListener {
        void onChange(String typefaceName);
    }

    public void setListener(onChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return typefaceList.size();
    }

    static class typefaceHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_select;

        public typefaceHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_typeface);
            iv_select = itemView.findViewById(R.id.iv_select);
        }
    }

}
