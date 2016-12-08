package com.lcg.gift.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.R;
import com.lcg.gift.databinding.ActivityRegistBinding;
import com.lcg.gift.model.Regist;

public class RegistActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegistBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_regist);
        Regist regist = new Regist(this);
        regist.setTitleText("注册");
        binding.setRegist(regist);
    }
}
