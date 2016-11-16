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
        //TODO 检查一下合法性
        HashMap<String, String> map = new HashMap<>();
        map.put("email", username);
        map.put("pwd", MD5.GetMD5Code(password));
        Call call = HttpManager.getInstance().post(HttpUrl.LOGIN, map, new BaseDataHandler<String, String>() {
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
        notifyProgressDialogShow("登陆中...", call);
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

