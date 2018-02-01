package com.lcg.call;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 简单通用的RecyclerView.Adapter
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2017/6/20 19:58
 */
public class MySimpleAdapter extends RecyclerView.Adapter<MySimpleAdapter.MyViewHolder> {
    private ArrayList<BaseObservableMe> mItems;
    private int mLayout, mVariable;

    public MySimpleAdapter(@NonNull ArrayList<BaseObservableMe> items, @LayoutRes int layout,
                           int variable) {
        mItems = items;
        mLayout = layout;
        mVariable = variable;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()
        ), mLayout, parent, false);
        MyViewHolder holder = new MyViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.getBinding().setVariable(mVariable, mItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        } else {
            return mItems.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        MyViewHolder(View itemView) {
            super(itemView);
        }

        ViewDataBinding getBinding() {
            return binding;
        }

        void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
