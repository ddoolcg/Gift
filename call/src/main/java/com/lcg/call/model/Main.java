package com.lcg.call.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.lcg.call.BaseObservableMe;
import com.lcg.mylibrary.BaseActivity;
import com.lcg.mylibrary.PreferenceHandler;
import com.lcg.mylibrary.net.BaseDataHandler;
import com.lcg.mylibrary.net.HttpManager;
import com.lcg.mylibrary.utils.MD5;
import com.lcg.mylibrary.utils.UIUtils;

import java.util.HashMap;

import okhttp3.Call;

/**
 * TODO
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2017/1/11 14:10
 */
public class Main extends BaseObservableMe {
    private String me, call, account;
    private boolean meEnable, accountEnable;
    private static final String url = "http://118.178.35.162:8008/api/user2.0/call.php";

    public Main(BaseActivity activity) {
        super(activity);
        me = PreferenceHandler.getInstance().getString("me", "");
        call = PreferenceHandler.getInstance().getString("call", "");
        account = PreferenceHandler.getInstance().getString("account", "");
        meEnable = TextUtils.isEmpty(me);
        accountEnable = TextUtils.isEmpty(account);
    }

    public void commit(final View v) {
        HashMap<String, String> map = getParams();
        if (map == null)
            return;
        v.setEnabled(false);
        Call call = HttpManager.getInstance().get(url, map, new BaseDataHandler<String, String>() {
            @Override
            public void onNetFinish() {
                notifyProgressDialogdismiss();
                v.setEnabled(true);
                PreferenceHandler.getInstance().setString("me", me);
                PreferenceHandler.getInstance().setString("call", Main.this.call);
                PreferenceHandler.getInstance().setString("account", account);
            }

            @Override
            public void onSuccess(int code, String data) {
                UIUtils.showToastSafe("发起成功，请等待接听！");
                getActivity().finish();
            }
        });
        notifyProgressDialogShow("拨打中...",call);
    }

    @NonNull
    public HashMap<String, String> getParams() {
        if (TextUtils.isEmpty(me) || TextUtils.isEmpty(call) || TextUtils.isEmpty(account))
            return null;
        HashMap<String, String> map = new HashMap<>();
        map.put("act", "call");
        map.put("platform", "android");
        map.put("multiAgent", "1");
        map.put("versionCode", "8");
        map.put("version", "2");
        map.put("apiurl", "http://118.178.35.162:8008/api/user2.0");
        map.put("isapi", "1");
        map.put("cert", "0261");
        map.put("softid", "6749");
        map.put("mobile", me);
        map.put("called", call);
        map.put("account", account);
        map.put("md5", MD5.GetMD5Code(me + map.get("softid") + call + "ysw"));
        return map;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isMeEnable() {
        return meEnable;
    }

    public void setMeEnable(boolean meEnable) {
        this.meEnable = meEnable;
    }

    public boolean isAccountEnable() {
        return accountEnable;
    }

    public void setAccountEnable(boolean accountEnable) {
        this.accountEnable = accountEnable;
    }
}
