package com.lcg.gift.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.MyApplication;
import com.lcg.gift.R;
import com.lcg.gift.databinding.ActivityMainBinding;
import com.lcg.gift.model.Main;

import xa.qwe.xz.os.OffersManager;
import xa.qwe.xz.os.df.DiyOfferWallManager;


public class MainActivity extends BaseActivity {
    private Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        main = new Main(this);
        main.setPoints(MyApplication.getInstance().getUserInfor().getPoints());
        binding.setMain(main);
        OffersManager.getInstance(this).onAppLaunch();
        DiyOfferWallManager.getInstance(this).onAppLaunch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        main.updataInfor();
    }

    @Override
    protected void onDestroy() {
        OffersManager.getInstance(this).onAppExit();
        DiyOfferWallManager.getInstance(this).onAppExit();
        super.onDestroy();
    }
}
