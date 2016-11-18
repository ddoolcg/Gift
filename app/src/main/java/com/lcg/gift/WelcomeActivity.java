package com.lcg.gift;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.lcg.gift.activity.LoginActivity;
import com.lcg.gift.activity.MainActivity;
import com.lcg.gift.bean.UserInfor;
import com.lcg.gift.databinding.ActivityWelcomeBinding;
import com.lcg.gift.utils.UIUtils;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(0x08000000);
        super.onCreate(savedInstanceState);
        ActivityWelcomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        AdManager.getInstance(this).init("ffbbe1ee11434e5d", "e6388f8c95657302", BuildConfig.DEBUG, BuildConfig.DEBUG);
        OffersManager.getInstance(this).onAppLaunch();
        UIUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserInfor userInfor = MyApplication.getInstance().getUserInfor();
                if (userInfor == null || TextUtils.isEmpty(userInfor.getToken())) {
                    startActivity(LoginActivity.class);
                } else {
                    startActivity(MainActivity.class);
                }
                finish();
            }
        }, 1000);
    }
}
