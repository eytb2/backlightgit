package com.lemon.backnightgit.ui;

import android.app.Application;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.lemon.backnightgit.Console;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {



    public static boolean hasGotToken;
    //尝试次数
    public static int times = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        initAccessTokenWithAkSk();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                if ( times++ < 3 ) {
                    initAccessTokenWithAkSk();
                } else {
                    Toast.makeText(App.this, "AK，SK方式获取token失败", Toast.LENGTH_SHORT).show();
                }
            }
        }, getApplicationContext(), Console.APP_KEY, Console.APP_SECRET);
    }
}
