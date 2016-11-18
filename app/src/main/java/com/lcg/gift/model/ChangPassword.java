package com.lcg.gift.model;

import android.view.View;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;
import com.lcg.gift.net.BaseDataHandler;
import com.lcg.gift.net.HttpManager;
import com.lcg.gift.utils.MD5;
import com.lcg.gift.utils.UIUtils;
import com.lcg.gift.value.HttpUrl;

import java.util.HashMap;

import okhttp3.Call;

/**
 * 修改密码
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/11/2 17:25
 */

public class ChangPassword extends BaseObservableMe {
    private String pwd, newPwd, reNewPwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getReNewPwd() {
        return reNewPwd;
    }

    public void setReNewPwd(String reNewPwd) {
        this.reNewPwd = reNewPwd;
    }

    public ChangPassword(BaseActivity activity) {
        super(activity);
    }

    /**
     * 提交
     */
    public void commit(final View view) {
        //TODO 检查一下合法性
        HashMap<String, String> map = new HashMap<>();
        map.put("pwd", MD5.GetMD5Code(pwd));
        map.put("newPwd", MD5.GetMD5Code(newPwd));
        Call call = HttpManager.getInstance().post(HttpUrl.PASSWORD, map, new BaseDataHandler<String, String>() {
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
        notifyProgressDialogShow("修改中...", call);
    }

}
