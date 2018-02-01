package com.lcg.call;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.lcg.call.databinding.ActivityMainBinding;
import com.lcg.call.model.Main;
import com.lcg.call.model.MainItem;
import com.lcg.mylibrary.BaseActivity;
import com.lcg.mylibrary.PreferenceHandler;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout
                .activity_main);
        Main main = new Main(this);
        binding.setMain(main);
        binding.call.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    binding.getMain().commit(binding.button);
                }
                return false;
            }
        });
        binding.rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        ArrayList<BaseObservableMe> items = new ArrayList<>();
        String calls = PreferenceHandler.getInstance().getString("calls", "");
        if (!TextUtils.isEmpty(calls)) {
            String[] callses = calls.split(",");
            for (int i = 0; i < callses.length; i++) {
                if (!TextUtils.isEmpty(callses[i]))
                    items.add(new MainItem(main, callses[i]));
            }
        }
        binding.rv.setAdapter(new MySimpleAdapter(items, R.layout.activity_main_item, com.lcg
                .call.BR.item));
    }
}
