package com.lcg.call.model;

import android.databinding.Bindable;
import android.text.TextUtils;
import android.view.View;

import com.lcg.call.BR;
import com.lcg.call.BaseObservableMe;
import com.lcg.mylibrary.BaseActivity;
import com.lcg.mylibrary.PreferenceHandler;
import com.lcg.mylibrary.net.BaseDataHandler;
import com.lcg.mylibrary.net.HttpManager;
import com.lcg.mylibrary.utils.StringUtils;
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
    private String me, call, account, balance;
    private boolean meEnable, accountEnable;
    private static final String BALANCE_URL = "http://bddoo.tunnel.qydev.com/call";
    private static final String CALL_URL = BALANCE_URL;

    public Main(BaseActivity activity) {
        super(activity);
        me = PreferenceHandler.getInstance().getString("me", "");
        account = PreferenceHandler.getInstance().getString("account", "");
        meEnable = TextUtils.isEmpty(me);
        accountEnable = TextUtils.isEmpty(account);
        call = "";
        balance = "";
        balance();
        call = PreferenceHandler.getInstance().getString("call", "");
    }

    public void commit(final View v) {
        if (TextUtils.isEmpty(me) || TextUtils.isEmpty(call) )
            return;
        if (!StringUtils.isPhone(call)) {
            UIUtils.showToastSafe("请输入正确的手机号码！");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("me", me);
        map.put("call", call);
        if (v != null)
            v.setEnabled(false);
        Call call = HttpManager.getInstance().post(CALL_URL, map, new BaseDataHandler<String,
                String>() {
            @Override
            public void onNetFinish() {
                notifyProgressDialogdismiss();
                if (v != null)
                    v.setEnabled(true);
                PreferenceHandler handler = PreferenceHandler.getInstance();
                handler.setString("me", me);
                handler.setString("call", Main.this.call);
                handler.setString("account", account);
                String calls = handler.getString("calls", "");
                if (TextUtils.isEmpty(calls)) {
                    handler.setString("calls", Main.this.call);
                } else {
                    String[] split = calls.split(",");
                    String s = Main.this.call;
                    for (int i = 0; i < split.length && i < 20; i++) {
                        if (!s.contains(split[i])) s += ("," + split[i]);
                    }
                    handler.setString("calls", s);
                }
            }

            @Override
            public void onSuccess(int code, String data) {
                UIUtils.showToastSafe(data);
                getActivity().finish();
            }
        });
        notifyProgressDialogShow("拨打中...", call);
    }

    private void balance() {
        if (TextUtils.isEmpty(me))
            return;
        HashMap<String, String> map = new HashMap<>();
        map.put("me", me);
        HttpManager.getInstance().get(BALANCE_URL, map, new BaseDataHandler<String, String>() {
            @Override
            public void onNetFinish() {
            }

            @Override
            public void onSuccess(int code, String data) {
                if (!TextUtils.isEmpty(data) && data.startsWith("1")) {
                    String[] split = data.split("\\|");
                    if (split.length > 2) {
                        try {
                            Float valueOf = Float.valueOf(split[1]);
                            setBalance("(剩余" + (int) (valueOf / 0.25f) + "分钟)");
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
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

    @Bindable
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
        notifyPropertyChanged(BR.balance);
    }
}
