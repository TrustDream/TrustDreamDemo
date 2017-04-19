package com.trust.lhdemo.QuanXian;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.trust.lhdemo.R;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class QuanXianActivity extends AppCompatActivity {
    Context context = QuanXianActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_xian);
        getHistoryApps();
    }

    public void openPermission(View view) {
        if (!checkPermission()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivityForResult(intent, 100);
        } else {
            Toast.makeText(this, "权限已开启！", Toast.LENGTH_SHORT).show();
        }
    }

    public void openService(View view) {
        startService(new Intent(context, MonitorService.class));
        Toast.makeText(this, "服务已开启！", Toast.LENGTH_SHORT).show();
    }

    public void closeService(View view) {
        stopService(new Intent(context, MonitorService.class));
        Toast.makeText(this, "服务已关闭！", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (!checkPermission()) {
                Toast.makeText(this, "权限未开启！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "权限已开启！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private boolean checkPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode;
        mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void getHistoryApps() {
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> usageStatsList = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, startTime, endTime);

        if (usageStatsList != null && !usageStatsList.isEmpty()) {
            HashSet<String> set = new HashSet<>();
            for (UsageStats usageStats : usageStatsList) {
                set.add(usageStats.getPackageName());
            }

            if (!set.isEmpty()) {
                Log.e("size", set.size() + "");
            }
        }
    }

}
