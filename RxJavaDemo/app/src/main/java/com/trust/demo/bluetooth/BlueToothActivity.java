package com.trust.demo.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.trust.demo.R;
import com.trust.demo.bean.BlueDeviceBean;
import com.trust.demo.perssion.PermissionUtils;
import com.trust.demo.swipebacklayout.app.SwipeBackActivity;
import com.trust.demo.tool.Config;
import com.trust.demo.tool.L;
import com.trust.demo.tool.RecyclerViewDivider;
import com.trust.demo.tool.T;

import java.util.ArrayList;
import java.util.List;

public class BlueToothActivity extends SwipeBackActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private BlueToothadapter blueToothadapter;
    private List<BlueDeviceBean> ml = new ArrayList<>();
    private Button startFound,closeFound;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private final int BLEUETOOTH_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        init();
    }

    private void init() {
        initView();
//        initDate();
        initBlueTooth();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initBlueTooth() {
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_ACCESS_COARSE_LOCATION,
                mPermissionGrant);
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if(bluetoothAdapter == null){
            T.showToast(this,"yours phone is no BlueTooth!");
            return;
        }

        if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,BLEUETOOTH_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Config.BlueClose){
            L.d("blue close ");
            return;
        }
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_blue_tooth_recycler);
        blueToothadapter = new BlueToothadapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(blueToothadapter);
//        recyclerView.addItemDecoration(new RecyclerViewDivider(this,
//                LinearLayoutManager.HORIZONTAL));
        blueToothadapter.blueRecyclerOnclik = new BlueToothadapter.BlueRecyclerOnclik(){

            @Override
            public void callBack(View v, BlueDeviceBean blueDeviceBean) {
                L.d("you click");
                bluetoothAdapter.cancelDiscovery();
                BluetoothDevice device = bluetoothAdapter.getRemoteDevice(blueDeviceBean.
                        getAddress());
                Intent intent = new Intent();
                intent.putExtra(Config.BlueeDeviceName,blueDeviceBean.getName());
                intent.putExtra(Config.BlueeDeviceAddress,blueDeviceBean.getAddress());
                intent.setClass(BlueToothActivity.this,BlueDeviceActivity.class);
                startActivity(intent);
            }
        };

        startFound = (Button) findViewById(R.id.activity_blue_tooth_found_blue);
        closeFound = (Button) findViewById(R.id.activity_blue_tooth_close_blue);

        startFound.setOnClickListener(this);
        closeFound.setOnClickListener(this);

    }


    public void ext(View v){
        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_blue_tooth_found_blue:
                startFound.setVisibility(View.GONE);
                closeFound.setVisibility(View.VISIBLE);

                startFoundBlue();
                break;
            case R.id.activity_blue_tooth_close_blue:
                startFound.setVisibility(View.VISIBLE);
                closeFound.setVisibility(View.GONE);

                bluetoothAdapter.cancelDiscovery();
                break;
        }
    }

    public void startFoundBlue(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(blueToothBrocastReceiver,filter);
        bluetoothAdapter.startDiscovery();
    }

    public BroadcastReceiver blueToothBrocastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                ml.add(new BlueDeviceBean(deviceName,device.getAddress()));
                blueToothadapter.setMl(ml);
                blueToothadapter.notifyDataSetChanged();
            }
        }
    };




    public  PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(BlueToothActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    L.d("sucess");

                    break;
            }
        }
    };

}
