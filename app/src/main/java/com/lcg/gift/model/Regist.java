package com.lcg.gift.model;

import android.view.View;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.net.BaseDataHandler;
import com.lcg.gift.net.HttpManager;
import com.lcg.gift.utils.MD5;
import com.lcg.gift.utils.UIUtils;
import com.lcg.gift.value.HttpUrl;

import java.util.HashMap;

import okhttp3.Call;

/**
 * 注册
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/13 15:57
 */

public class Regist extends Login {
    private String repassword;

    public Regist(BaseActivity activity) {
        super(activity);
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public void regist(View view) {
        String username = getUsername();
        String password = getPassword();
        //TODO 检查一下合法性
        HashMap<String, String> map = new HashMap<>();
        map.put("email", username);
        map.put("pwd", MD5.GetMD5Code(password));
        Call call = HttpManager.getInstance().post(HttpUrl.REGIST, map, new BaseDataHandler<String, String>() {
            @Override
            public void onNetFinish() {
                notifyProgressDialogdismiss();
            }

            @Override
            public void onFail(int code, String data) {
                if (code > 0) {
                    UIUtils.showToastSafe("服务器繁忙！");
                } else {
                    UIUtils.showToastSafe("网络堵塞！");
                }
            }

            @Override
            public void onSuccess(int code, String data) {
                //TODO
            }
        });
        notifyProgressDialogShow("注册中...", call);
    }
}
