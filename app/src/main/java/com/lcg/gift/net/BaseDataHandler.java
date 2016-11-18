package com.lcg.gift.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.lcg.gift.bean.SimpleData;
import com.lcg.gift.utils.L;
import com.lcg.gift.utils.UIUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基本数据处理器，主线程执行on方法。S为成功的数据类型，E为错误的数据类型。
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/14 14:17
 */

public abstract class BaseDataHandler<S, E> implements DataHandler {
    @Override
    public void start() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                onStart();
            }
        });
    }

    @Override
    public void netFinish() {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                onNetFinish();
            }
        });
    }

    @Override
    public void fail(final int code, String errorData) {
        L.w("NET", "code=" + code + " errorData=" + errorData + "");
        if (code == 402) {//TODO 去登陆
        } else if (code == 403) {
            SimpleData simpleData = null;
            try {
                simpleData = JSON.parseObject(errorData, SimpleData.class);
            } catch (Exception e) {
            }
            if (simpleData == null || TextUtils.isEmpty(simpleData.getMsg())) {
                UIUtils.showToastSafe("服务器异常");
            } else {
                UIUtils.showToastSafe(simpleData.getMsg());
            }
        } else {
            final Object data = parseData(errorData, 1);
            UIUtils.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    onFail(code, (E) data);
                }
            });
        }
    }

    @Override
    public void success(final int code, String successData) {
        L.d("NET", successData);
        final Object data = parseData(successData, 0);
        if (data == null) fail(code, successData);
        else UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                onSuccess(code, (S) data);
            }
        });
    }

    /**
     * 数据解析
     */
    private Object parseData(String successData, int position) {
        ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        Type type2 = type.getActualTypeArguments()[position];
        if (!type2.equals(String.class)) {
            try {
                Object obj = JSON.parseObject(successData, type2, new Feature[0]);
                Class<? extends Object> class2 = obj.getClass();
                JSONType annotation = class2.getAnnotation(JSONType.class);
                if (annotation != null) {
                    String[] orders = annotation.orders();
                    for (int i = 0; i < orders.length; i++) {
                        Field field2 = class2.getDeclaredField(orders[i]);
                        field2.setAccessible(true);
                        Object object = field2.get(obj);
                        if (object == null) {
                            throw new Exception("goto Failure");
                        }
                    }
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return successData;
        }
    }

    /**
     * 网络连接之前调用
     */
    public void onStart() {
    }

    /**
     * 网络执行完后调用
     */
    public abstract void onNetFinish();

    /**
     * 网络异常调用
     *
     * @param code > 0服务器返回异常，其他表示连接失败。
     * @param data
     */
    public void onFail(int code, E data) {
        if (code > 0) {
            UIUtils.showToastSafe("服务器繁忙！");
        } else {
            UIUtils.showToastSafe("网络堵塞！");
        }
    }

    /**
     * 服务器返回正确的数据时调用
     *
     * @param code
     * @param data
     */
    public abstract void onSuccess(int code, S data);
}
