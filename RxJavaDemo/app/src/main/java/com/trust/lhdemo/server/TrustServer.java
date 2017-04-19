package com.trust.lhdemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.trust.lhdemo.bluetooth.BlueToothService;
import com.trust.lhdemo.tool.L;

/**
 * Created by Trust on 2017/4/18.
 */
public class TrustServer extends Service {
    private BlueToothService blueToothService;
    public MsgBinder msgBinder = new MsgBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return msgBinder;
    }

    @Override
    public void onCreate() {
        blueToothService = new BlueToothService(this);
        L.d("TrustServer onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public class MsgBinder extends Binder{
        public TrustServer getService (){return TrustServer.this;};
    }

    @Override
    public void onDestroy() {
        blueToothService.disconnect();
        blueToothService.close();
    }

    //--------------------BlueTooth----------------------------------------------------------

    /**
     *   blue tooth setting
     * @param address
     */
    public boolean startConnection(String address){
        boolean  status = blueToothService.connection(address);
        if(status){
            L.d("connection is success");
        }else{
            L.err("connection is failure");
        }
        return status;
    }

    public void closeConnection(){
        blueToothService.disconnect();
    }

    public void writeBlueData(byte[] data){
        blueToothService.writeData(data);
    }

}
