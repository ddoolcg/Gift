package com.lcg.gift.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.R;
import com.lcg.gift.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }
}
