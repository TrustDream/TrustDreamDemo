package com.trust.demo.tinker;

import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import com.trust.demo.BaseActivity;
import com.trust.demo.R;


/**
*使用热更新的时候  需要使用 Gradle 点击 build  找到  ass。。。release   进行正式版的签名。然后再app目录下build中找到bakApk
*文件里面的apk文件，安装。在app.build.gradle 中 把apk，txt。mapping 文件的路径配置进去 mapping文件只有混淆后才有未混淆的话
*可以仿照Demo里面的填上
*安装好了以后，修改本activity里面的代码，修改成功后点击Gradle，找到tinker点击 tinkerrelease  在app目录下 的 outputs文件找到
*tinkerPatch文件找到release文件里面的patch_signed_7zip.apk  这个就是更新的补丁包，把补丁包放到手机内存卡或者手机内存根目录
*运行app点击 loadPath 之后会出现 loadPath Success 等字样 这时候点击kill按钮  重新进入app就会发现之前的页面已经换成更新后补丁
*补丁的页面了，需要注意的是，多次更新补丁的话需要保留 基础版本的apk以及txt，mapping 文件，基础版本就是你app安装的apk时的版本
*更新补丁的版本号可以调节，尽量保证唯一性，防止加载补丁是以前的补丁
**/


public class TinkerActivity extends BaseActivity {
    private Button loadPathBtn,killBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker);
        initView();
        init();
        showToast("BUG修复了  骚年！");
    }

    private void initView() {
        loadPathBtn = (Button) findViewById(R.id.tinker_load_path);
        killBtn = (Button) findViewById(R.id.tinker_kill);

        loadPathBtn.setOnClickListener(this);
        killBtn.setOnClickListener(this);
    }

    private void init() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tinker_load_path:

                loadPath();
                break;
            case R.id.tinker_kill:
                kill();
                break;
        }
    }

    /**
     * 加载热补丁
     */
    private void loadPath() {

        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/patch_signed_7zip.apk");
    }

    /**
     * 删掉进程 并删除热补丁
     */
    private void kill() {
        ShareTinkerInternals.killAllOtherProcess(getApplicationContext());
        android.os.Process.killProcess(android.os.Process.myPid());
    }



}
