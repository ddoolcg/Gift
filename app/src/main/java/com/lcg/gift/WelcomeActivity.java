package com.lcg.gift;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.lcg.gift.activity.LoginActivity;
import com.lcg.gift.activity.MainActivity;
import com.lcg.gift.bean.UserInfor;
import com.lcg.gift.databinding.ActivityWelcomeBinding;
import com.lcg.gift.utils.L;
import com.lcg.gift.utils.UIUtils;
import com.lcg.gift.value.HttpUrl;

import xa.qwe.xz.AdManager;
import xa.qwe.xz.onlineconfig.OnlineConfigCallBack;
import xa.qwe.xz.os.OffersBrowserConfig;
import xa.qwe.xz.os.OffersManager;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(0x08000000);
        super.onCreate(savedInstanceState);
        ActivityWelcomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        AdManager.getInstance(this).init("ffbbe1ee11434e5d", "e6388f8c95657302", false, BuildConfig.DEBUG);
        OffersManager.getInstance(this).setCustomUserId(MyApplication.getInstance().getUserInfor().getId() + "");
        OffersBrowserConfig offersBrowserConfig = OffersBrowserConfig.getInstance(this);
        offersBrowserConfig.setBrowserTitleText("赚取佣金");
        offersBrowserConfig.setPointsLayoutVisibility(false);
        AdManager.getInstance(this).asyncGetOnlineConfig("host", new OnlineConfigCallBack() {
            @Override
            public void onGetOnlineConfigSuccessful(String s, String s1) {
                PreferenceHandler.getInstance().setString("host", s);
                HttpUrl.HOST = s;
                nextActivity();
            }

            @Override
            public void onGetOnlineConfigFailed(String s) {
                HttpUrl.HOST = PreferenceHandler.getInstance().getString("host", HttpUrl.HOST);
                nextActivity();
            }

            private void nextActivity() {
            }
        });
        // 默认请求http广告，若需要请求https广告，请设置AdSettings.setSupportHttps为true
        // AdSettings.setSupportHttps(true);
        // the observer of AD
        SplashAdListener listener = new SplashAdListener() {
            @Override
            public void onAdDismissed() {
                L.i("onAdDismissed");
                jumpWhenCanClick(); // 跳转至您的应用主界面
            }

            @Override
            public void onAdFailed(String arg0) {
                L.i("onAdFailed:" + arg0);
                jump();
            }

            @Override
            public void onAdPresent() {
            }

            @Override
            public void onAdClick() {
                // 设置开屏可接受点击时，该回调可用
            }
        };
        String adPlaceId = "2058622"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
        new SplashAd(this, binding.activityWelcome, listener, adPlaceId, true);
    }

    /**
     * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加canJumpImmediately判断。 另外，点击开屏还需要在onResume中调用jumpWhenCanClick接口。
     */
    public boolean canJumpImmediately = false;

    private void jumpWhenCanClick() {
        if (canJumpImmediately) {
            jump();
        } else {
            canJumpImmediately = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        canJumpImmediately = false;
    }

    /**
     * 不可点击的开屏，使用该jump方法，而不是用jumpWhenCanClick
     */
    private void jump() {
        UIUtils.runInMainThread(new Runnable() {
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJumpImmediately) {
            jumpWhenCanClick();
        }
        canJumpImmediately = true;
    }
}
