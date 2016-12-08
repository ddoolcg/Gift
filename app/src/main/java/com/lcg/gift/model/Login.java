package com.lcg.gift.model;

import android.text.TextUtils;
import android.view.View;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;
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
 * 登陆
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/13 11:33
 */
public class Login extends BaseObservableMe {
    private String username, password;

    public Login(BaseActivity activity) {
        super(activity);
    }

    //    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
//        notifyPropertyChanged(BR.username);
    }

    //    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
//        notifyPropertyChanged(BR.password);
    }

    public void login(final View view) {
        if (!check())
            return;
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", MD5.GetMD5Code(password));
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
                getActivity().finish();
            }
        });
        notifyProgressDialogShow("登陆中...", call);
    }

    private boolean check() {
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
        }
        return true;
    }
//    @Bindable
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//        notifyPropertyChanged(BR.avatar);
//    }
//
//    @BindingAdapter({"avatar"})
//    public static void avatar(final ImageView iv, final String avatar) {
//        Glide.with(iv.getContext()).load(avatar).error(R.mipmap.ic_launcher).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(iv);
//        UIUtils.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Glide.with(iv.getContext()).load("http://img4q.duitang.com/uploads/item/201406/18/20140618215937_hBXeP.thumb.700_0.gif").error(R.mipmap.ic_launcher).
//                        override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(iv);
//            }
//        }, 10000);
//    }

//    @BindingAdapter({"username", "password"})
//    public static void login(View view, final String username, final String password) {
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(view.getContext(), "--账号：" + username + "\n密码：" + password, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//    }
}

