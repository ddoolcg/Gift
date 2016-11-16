package com.lcg.gift.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.adapters.ListenerUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lcg.gift.R;


/**
 * demo
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/10/26 11:31
 */
@BindingMethods({@BindingMethod(type = EditText.class, attribute = "text", method = "setText")})
//@InverseBindingMethods({@InverseBindingMethod(type = EditText.class, attribute = "text", method = "getText", event = "contentAC")})
public class MyEditTextBindingAdapter {
    @InverseBindingAdapter(attribute = "text", event = "contentAC")
    public static String getText(EditText et) {
        return et.getText().toString();
    }

    @BindingAdapter(value = {"contentAC"}, requireAll = false)
    public static void setContent(EditText et, final InverseBindingListener listener) {
        TextWatcher watcherNew = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listener.onChange();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        TextWatcher watcherOld = ListenerUtil.trackListener(et, watcherNew, R.id.editText);
        if (watcherOld != null) et.removeTextChangedListener(watcherOld);
        if (listener != null) et.addTextChangedListener(watcherNew);
    }
}
