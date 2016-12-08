package com.lcg.gift.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.R;
import com.lcg.gift.databinding.ActivityCostBinding;
import com.lcg.gift.model.Cost;

/**
 * 兑换
 */
public class CostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCostBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_cost);
        Cost cost = new Cost(this);
        binding.setCost(cost);
        cost.setTitleText("兑换");
    }
}
