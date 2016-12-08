package com.lcg.gift.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.R;
import com.lcg.gift.bean.UserInfor;
import com.lcg.gift.databinding.ActivityRobBinding;
import com.lcg.gift.model.Rob;

public class RobActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRobBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rob);
        UserInfor infor = (UserInfor) getIntent().getSerializableExtra("UserInfor");
        Rob rob = new Rob(this);
        rob.setNickname(infor.getNickname());
        rob.setId(infor.getId());
        rob.setPoints(infor.getPoints() / 100f);
        rob.setUsername(infor.getUsername());
        binding.setRob(rob);
    }
}
