package com.chinaiat.bob.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chinaiat.bob.R;
import com.chinaiat.bob.activity.FruitDescriptionActivity;
import com.chinaiat.bob.activity.MainActivity;
import com.chinaiat.bob.bean.FruitInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date: 2019/6/3
 * @description :HomeFragment适配器
 */
public class CollectDataAdapter extends RecyclerView.Adapter<CollectDataAdapter.HomeHolder> {

    private List<FruitInfo> mFruitInfoList;
    private Context mContext;

    public CollectDataAdapter(Context context, List<FruitInfo> fruitInfoList) {
        this.mFruitInfoList = fruitInfoList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeHolder(LayoutInflater.from(mContext).inflate(R.layout.item_fruit_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeHolder holder, int position) {
        final FruitInfo fruitInfo = mFruitInfoList.get(position);
//        setImgHeight(holder.iv_img);
        holder.tv_fruitName.setText(fruitInfo.getFruitName());
        holder.tv_fruitDesc.setText(fruitInfo.getFruitDescription());
        Glide.with(mContext).load(fruitInfo.getImgResId()).into(holder.iv_img);
        holder.rl_fruitGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MainActivity) mContext, FruitDescriptionActivity.class);
                intent.putExtra("fruitInfo", fruitInfo);
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) mContext, holder.iv_img, mContext.getString(R.string.transitionName));
                ActivityCompat.startActivityForResult((MainActivity) mContext, intent, 102, compat.toBundle());
            }
        });
    }

    public void removeItem(FruitInfo info) {
        for (int i = 0; i < mFruitInfoList.size(); i++) {
            if (mFruitInfoList.get(i).getId() == info.getId()) {
                mFruitInfoList.remove(info);
                notifyItemRemoved(i);
                notifyItemRangeRemoved(0, mFruitInfoList.size());
                break;
            }
        }
    }
//
//    /**
//     * 设置ImageView不规则的高
//     *
//     * @param view
//     */
//    private void setImgHeight(View view) {
//        ViewGroup.LayoutParams params = view.getLayoutParams();
//        //设置图片的相对于屏幕的宽高比
//        params.height = (int) (200 + Math.random() * 400);
//        view.setLayoutParams(params);
//    }

    @Override
    public int getItemCount() {
        return mFruitInfoList.size();
    }

    /**
     * 设置数据
     *
     * @param fruitInfoList
     */
    public void setData(List<FruitInfo> fruitInfoList) {
        mFruitInfoList = fruitInfoList;
        notifyDataSetChanged();
    }

    static class HomeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_fruitGroup)
        RelativeLayout rl_fruitGroup;
        @BindView(R.id.iv_img)
        ImageView iv_img;
        @BindView(R.id.tv_fruit_name)
        TextView tv_fruitName;
        @BindView(R.id.tv_fruit_desc)
        TextView tv_fruitDesc;

        public HomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
