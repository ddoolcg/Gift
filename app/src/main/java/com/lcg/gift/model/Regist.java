package com.lcg.gift.model;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.MyApplication;
import com.lcg.gift.activity.MainActivity;
import com.lcg.gift.bean.UserInfor;
import com.lcg.gift.net.BaseDataHandler;
import com.lcg.gift.net.HttpManager;
import com.lcg.gift.utils.MD5;
import com.lcg.gift.utils.StringUtils;
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
        if (!check())
            return;
        HashMap<String, String> map = new HashMap<>();
        map.put("username", getUsername());
        map.put("password", MD5.GetMD5Code(getPassword()));
        Call call = HttpManager.getInstance().put(HttpUrl.AUTH, map, new BaseDataHandler<UserInfor, String>() {
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
            public void onSuccess(int code, UserInfor data) {
                MyApplication.getInstance().setUserInfor(data);
                startActivity(MainActivity.class);
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });
        notifyProgressDialogShow("注册中...", call);
    }

    private boolean check() {
        String username = getUsername();
        String password = getPassword();
        if (TextUtils.isEmpty(username)) {
            UIUtils.showToastSafe("用户名不能为空");
            return false;
        } else if (!StringUtils.isEmail(username)) {
            UIUtils.showToastSafe("请输入一个有效的邮箱地址");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            UIUtils.showToastSafe("密码不能为空");
            return false;
        } else if (password.length() <= 2) {
            UIUtils.showToastSafe("密码必须大于两位");
            return false;
        } else if (TextUtils.isEmpty(repassword)) {
            UIUtils.showToastSafe("重复密码不能为空");
            return false;
        } else if (!password.equals(repassword)) {
            UIUtils.showToastSafe("两次密码不一致");
            return false;
        }
        return true;
    }
}
