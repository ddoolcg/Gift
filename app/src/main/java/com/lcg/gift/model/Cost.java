package com.lcg.gift.model;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.RadioGroup;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;
import com.lcg.gift.R;
import com.lcg.gift.bean.SimpleData;
import com.lcg.gift.net.BaseDataHandler;
import com.lcg.gift.net.HttpManager;
import com.lcg.gift.utils.StringUtils;
import com.lcg.gift.utils.UIUtils;
import com.lcg.gift.value.HttpUrl;

import java.util.HashMap;

import okhttp3.Call;

/**
 * 兑换
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/12/6 17:39
 */

public class Cost extends BaseObservableMe {
    private String phone;
    private int id = 4;
    private BaseDataHandler<SimpleData, SimpleData> handler = new BaseDataHandler<SimpleData, SimpleData>() {
        @Override
        public void onNetFinish() {
            notifyProgressDialogdismiss();
        }

        @Override
        public void onSuccess(int code, SimpleData data) {
            UIUtils.showToastSafe("充值成功！");
        }
    };

    public Cost(BaseActivity activity) {
        super(activity);
    }

    public void recharge(View v) {
        if (!StringUtils.isPhone(phone)) {
            UIUtils.showToastSafe("请输入一个合法的手机号码");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("id", id + "");
        Call post = HttpManager.getInstance().post(HttpUrl.PINTS_COST, map, handler);
        notifyProgressDialogShow("充值中", post);
    }

    public RadioGroup.OnCheckedChangeListener listener() {
        return new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        id = 4;
                        break;
                    case R.id.rb_2:
                        id = 5;
                        break;
                    case R.id.rb_3:
                        id = 6;
                        break;
                }
            }
        };
    }

    @BindingAdapter({"listener"})
    public static void listener(RadioGroup rg, RadioGroup.OnCheckedChangeListener listener) {
        rg.setOnCheckedChangeListener(listener);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
