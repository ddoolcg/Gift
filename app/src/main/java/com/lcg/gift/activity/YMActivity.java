package com.lcg.gift.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.R;
import com.lcg.gift.databinding.ActivityAppsItemBinding;
import com.lcg.gift.model.AD;
import com.lcg.gift.utils.UIUtils;

import java.util.ArrayList;

import xa.qwe.xz.os.df.AppSummaryDataInterface;
import xa.qwe.xz.os.df.AppSummaryObjectList;
import xa.qwe.xz.os.df.DiyOfferWallManager;

public class YMActivity extends BaseActivity {
    private RecyclerView rv;
    private ArrayList<AD> mADs = new ArrayList<AD>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);
        assignViews();
        setView();
        loadDate();
    }

    private void assignViews() {
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    private void setView() {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new MyAdapter(mADs));
    }

    private void loadDate() {
        /**
         *  请求第一页100个广告，游戏先于应用展示
         */
        if (mADs.isEmpty())
            showProgressDialog("加载中...", null);
        mADs.clear();
        DiyOfferWallManager.getInstance(this).loadOfferWallAdList(DiyOfferWallManager.REQUEST_ALL, 1, 100,
                new AppSummaryDataInterface() {

                    /**
                     *  当成功获取到积分墙列表数据的时候，会回调本方法
                     *  注意：列表数据有可能为空（比如：没有广告的时候），开发者处理之前，请先判断列表是否为空d
                     *  注意：本接口不在UI线程中执行，所以请不要在本接口中进行UI线程方面的操作
                     */
                    @Override
                    public void onLoadAppSumDataSuccess(Context context, AppSummaryObjectList adList) {
                        dismissProgressDialog("");
                        if (adList != null && !adList.isEmpty()) {
                            int size = adList.size();
                            for (int i = 0; i < size; i++) {
                                mADs.add(new AD(YMActivity.this, adList.get(i)));
                            }
                            UIUtils.runInMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    rv.getAdapter().notifyDataSetChanged();
                                }
                            });
                        } else {
                            UIUtils.showToastSafe("当前没有广告哦~ 晚点再来吧");
                            Log.i("YoumiSdk", "当前没有广告哦~ 晚点再来吧");
                        }
                    }

                    /**
                     *  因为网络问题而导致请求失败时，会回调本方法
                     *  注意：本接口不在UI线程中执行，所以请不要在本接口中进行UI线程方面的操作
                     */
                    @Override
                    public void onLoadAppSumDataFailed() {
                        dismissProgressDialog("");
                        Log.e("YoumiSdk", "请求失败，请检查网络");
                    }

                    /**
                     *  请求成功，但是返回有米错误代码时候，会回调本方法
                     *  注意：本接口不在UI线程中执行，所以请不要在本接口中进行UI线程方面的操作
                     */
                    @Override
                    public void onLoadAppSumDataFailedWithErrorCode(int code) {
                        dismissProgressDialog("");
                        Log.e("YoumiSdk", String.format("请求错误，错误代码 ： %d， 请联系客服", code));
                    }
                });
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<AD> ads;

        public MyAdapter(ArrayList<AD> ads) {
            this.ads = ads;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ActivityAppsItemBinding binding = DataBindingUtil.inflate(LayoutInflater
                    .from(parent.getContext()), R.layout.activity_apps_item, parent, false);
            MyViewHolder holder = new MyViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.getBinding().setAd(ads.get(position));
        }

        @Override
        public int getItemCount() {
            if (ads == null) {
                return 0;
            } else {
                return ads.size();
            }
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        ActivityAppsItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public ActivityAppsItemBinding getBinding() {
            return binding;
        }

        public void setBinding(ActivityAppsItemBinding binding) {
            this.binding = binding;
        }
    }
}
