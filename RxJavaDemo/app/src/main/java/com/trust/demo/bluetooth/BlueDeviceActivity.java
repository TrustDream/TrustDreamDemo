package com.trust.demo.bluetooth;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.trust.demo.R;
import com.trust.demo.server.TrustServer;
import com.trust.demo.tool.Config;

public class BlueDeviceActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title,result;
    private EditText    sendEx;
    private Button connection,disconnection,send,resultClear,sendClear;

    private TrustServer trustServer = null;

    private String address;

    private long recvBytes=0;
    private long lastSecondBytes=0;
    private long sendBytes;
    private StringBuilder mData;

    private boolean mConnected = false;

    int sendIndex = 0;
    int sendDataLen=0;
    byte[] sendBuf;

    static long recv_cnt = 0;


    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BlueToothService.ACTION_GATT_CONNECTED.equals(action)) {

            } else if (BlueToothService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                trustServer.startConnection(address);
            } else if (BlueToothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //特征值找到才代表连接成功
                mConnected = true;
                invalidateOptionsMenu();
                updateConnectionState(R.string.connected);
            }else if (BlueToothService.ACTION_GATT_SERVICES_NO_DISCOVERED.equals(action)){
                trustServer.startConnection(address);
            }else if (BlueToothService.ACTION_DATA_AVAILABLE.equals(action)) {
//                final byte[] data = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
//                final StringBuilder stringBuilder = new StringBuilder();
//                 for(byte byteChar : data)
//                      stringBuilder.append(String.format("%02X ", byteChar));
//                Log.v("log",stringBuilder.toString());
                displayData(intent.getByteArrayExtra(BlueToothService.EXTRA_DATA));
            }else if (BlueToothService.ACTION_WRITE_SUCCESSFUL.equals(action)) {
//                mSendBytes.setText(sendBytes + " ");
                if (sendDataLen>0)
                {
                    Log.v("log","Write OK,Send again");
                    onSendBtnClicked();
                }
                else {
                    Log.v("log","Write Finish");
                }
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (trustServer != null) {
            final boolean result = trustServer.startConnection(address);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGattUpdateReceiver);
        unbindService(conn);
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BlueToothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BlueToothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BlueToothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BlueToothService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BlueToothService.ACTION_WRITE_SUCCESSFUL);
        intentFilter.addAction(BlueToothService.ACTION_GATT_SERVICES_NO_DISCOVERED);
        return intentFilter;
    }


    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // mConnectionState.setText(resourceId);
            }
        });
    }



    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            trustServer = ((TrustServer.MsgBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            trustServer = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_device);
        initView();

        if(trustServer == null){
            Intent intent = new Intent(BlueDeviceActivity.this, TrustServer.class);

            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    private void initView() {
        title = (TextView) findViewById(R.id.activity_blue_device_name);
        connection = (Button) findViewById(R.id.activity_blue_device_connection);
        disconnection = (Button) findViewById(R.id.activity_blue_device_disconnect);
        result = (TextView) findViewById(R.id.activity_blue_device_result);
        resultClear = (Button) findViewById(R.id.activity_blue_device_result_clear);

        sendEx = (EditText) findViewById(R.id.activity_blue_device_send_ex);
        send = (Button) findViewById(R.id.activity_blue_device_send);
        sendClear = (Button) findViewById(R.id.activity_blue_device_send_clear);

        resultClear.setOnClickListener(this);
        sendClear.setOnClickListener(this);
        send.setOnClickListener(this);

        connection.setOnClickListener(this);
        disconnection.setOnClickListener(this);

        mData = new StringBuilder();

        initDate();
    }

    private void initDate() {
        String name = getIntent().getStringExtra(Config.BlueeDeviceName);
        address = getIntent().getStringExtra(Config.BlueeDeviceAddress);
        if(name != null && address!= null){
            title.setText(name);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_blue_device_connection:
                connection.setVisibility(View.GONE);
                disconnection.setVisibility(View.VISIBLE);

                startConnection();
                break;
            case R.id.activity_blue_device_disconnect:
                connection.setVisibility(View.VISIBLE);
                disconnection.setVisibility(View.GONE);
                closeConnection();
                break;

            case R.id.activity_blue_device_send:
                sendDate();
                break;

            case R.id.activity_blue_device_send_clear:
                break;

            case R.id.activity_blue_device_result_clear:
                break;
        }
    }

    private void sendDate() {
        getSendBuf();
        onSendBtnClicked();
    }

    private void getSendBuf(){
        sendIndex = 0;

            sendBuf = sendEx.getText().toString().trim().getBytes();

        sendDataLen = sendBuf.length;
    }

    private byte[] stringToBytes(String s) {
        byte[] buf = new byte[s.length() / 2];
        for (int i = 0; i < buf.length; i++) {
            try {
                buf[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return buf;
    }


    private void onSendBtnClicked() {
        if (sendDataLen>20) {
            sendBytes += 20;
            final byte[] buf = new byte[20];
            // System.arraycopy(buffer, 0, tmpBuf, 0, writeLength);
            for (int i=0;i<20;i++)
            {
                buf[i] = sendBuf[sendIndex+i];
            }
            sendIndex+=20;
            trustServer.writeBlueData(buf);
            sendDataLen -= 20;
        }
        else {
            sendBytes += sendDataLen;
            final byte[] buf = new byte[sendDataLen];
            for (int i=0;i<sendDataLen;i++)
            {
                buf[i] = sendBuf[sendIndex+i];
            }
            trustServer.writeBlueData(buf);
            sendDataLen = 0;
            sendIndex = 0;
        }
    }


    //获取输入框十六进制格式
    private String getHexString() {
        String s = sendEx.getText().toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (('0' <= c && c <= '9') || ('a' <= c && c <= 'f') ||
                    ('A' <= c && c <= 'F')) {
                sb.append(c);
            }
        }
        if ((sb.length() % 2) != 0) {
            sb.deleteCharAt(sb.length());
        }
        return sb.toString();
    }

    private void closeConnection() {
        trustServer.closeConnection();
    }

    private void startConnection() {
        trustServer.startConnection(address);
    }

    private void displayData(byte[] buf) {
        recvBytes += buf.length;
        recv_cnt += buf.length;

        if (recv_cnt>=1024)
        {
            recv_cnt = 0;
            mData.delete(0,mData.length()/2); //UI界面只保留512个字节，免得APP卡顿
        }


        String s =asciiToString(buf);
        mData.append(s);

        result.setText(mData.toString());

    }

    public String asciiToString(byte[] bytes) {
        char[] buf = new char[bytes.length];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (char) bytes[i];
            sb.append(buf[i]);
        }
        return sb.toString();
    }


    public String bytesToString(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];

            sb.append(hexChars[i * 2]);
            sb.append(hexChars[i * 2 + 1]);
            sb.append(' ');
        }
        return sb.toString();
    }
}
