package com.lcg.gift;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lcg.gift.databinding.ActivityWelcomeBinding;
import com.lcg.gift.model.Login;

import net.youmi.android.offers.OffersManager;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OffersManager.getInstance(this).onAppLaunch();
        ActivityWelcomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        Login login = new Login(this);
        binding.setLogin(login);
        login.setUsername("aaa");
//        login.setAvatar("http://www.qqtu8.com/f/20140113192547.gif");
    }
}
