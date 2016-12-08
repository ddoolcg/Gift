package com.lcg.gift.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.R;
import com.lcg.gift.databinding.ActivityHistroyItemBinding;
import com.lcg.gift.model.History;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 历史（包含提现）
 */
public class HistroyActivity extends BaseActivity {
    private RecyclerView rv;

    private void assignViews() {
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroy);
        assignViews();
        setView();
    }

    private void setView() {
        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        Serializable historysSerializable = getIntent().getSerializableExtra("Historys");
        ArrayList<History> histories = new ArrayList<History>();
        if (historysSerializable != null) {
            ArrayList<com.lcg.gift.bean.History> historys = (ArrayList<com.lcg.gift.bean.History>) historysSerializable;
            for (com.lcg.gift.bean.History h : historys) {
                histories.add(new History(this, h));
            }
        }
        rv.setAdapter(new MyAdapter(histories));
    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<History> mHistories;

        public MyAdapter(ArrayList<History> histories) {
            mHistories = histories;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ActivityHistroyItemBinding binding = DataBindingUtil.inflate(LayoutInflater
                    .from(parent.getContext()), R.layout.activity_histroy_item, parent, false);
            MyViewHolder holder = new MyViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.getBinding().setHistory(mHistories.get(position));
        }

        @Override
        public int getItemCount() {
            if (mHistories == null) {
                return 0;
            } else {
                return mHistories.size();
            }
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        ActivityHistroyItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public ActivityHistroyItemBinding getBinding() {
            return binding;
        }

        public void setBinding(ActivityHistroyItemBinding binding) {
            this.binding = binding;
        }
    }
}
