package com.trust.demo.rxseries;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.trust.demo.BaseActivity;
import com.trust.demo.R;
import com.trust.demo.tool.L;
import com.trust.demo.tool.T;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class RxBindingActivity extends BaseActivity {
    private Button onclickBtn,longOnclickBtn,antiShakeOnclickBtn , conditionClickBtn,
            multipleClick;
    private CheckBox conditionClickCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);

        init();
    }

    private void init() {
        onclickBtn = (Button) findViewById(R.id.activity_rx_rxbinding_onclick);
        longOnclickBtn = (Button) findViewById(R.id.activity_rx_rxbinding_long_onclick);
        antiShakeOnclickBtn = (Button) findViewById(R.id.activity_rx_rxbinding_anti_shake_onclick);

        conditionClickBtn = (Button) findViewById(R.id.activity_rx_rxbinding_condition_btn_click);
        conditionClickCheckBox = (CheckBox) findViewById(R.
                id.activity_rx_rxbinding_condition_checkbox_click);

        multipleClick = (Button) findViewById(R.id.activity_rx_rxbinding_multiple_clicks);

        onClick();
    }

    private void onClick() {



        RxView.clicks(onclickBtn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                T.showToast(RxBindingActivity.this,"your click onclickBtn");
            }
        });

        RxView.longClicks(longOnclickBtn).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                T.showToast(RxBindingActivity.this,"your click longOnclickBtn");
            }
        });

        RxView.clicks(antiShakeOnclickBtn).throttleFirst(5, TimeUnit.SECONDS).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        T.showToast(RxBindingActivity.this,"your click antiShakeOnclickBtn");
                    }
                });

        RxCompoundButton.checkedChanges(conditionClickCheckBox).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                conditionClickBtn.setEnabled(aBoolean);
                conditionClickBtn.setBackgroundResource(aBoolean?
                        R.color.colorAccent : R.color.colorPrimary);

                RxView.clicks(conditionClickBtn).subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        T.showToast(RxBindingActivity.this,"your click conditionClickBtn");
                    }
                });
            }
        });

        /**
         * 缺少多次点击的demo
         */
        Observable observable = RxView.clicks(multipleClick);

        observable.buffer(600,TimeUnit.SECONDS).
                subscribe(new Consumer<List<Void>>() {
            @Override
            public void accept(@NonNull List<Void> list) throws Exception {
                L.d( "you click :" + list.size());
            }
        });




    }



}
