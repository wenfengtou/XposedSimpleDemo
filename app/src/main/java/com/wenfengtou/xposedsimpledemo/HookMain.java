package com.wenfengtou.xposedsimpledemo;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    private static final String TAG = "HookMain";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(!lpparam.packageName.equals("com.google.android.dialer")) return;
        Log.i(TAG, "wenfengtou load com.google.android.dialer");
        XposedHelpers.findAndHookMethod("com.yymjr.android.xposedproject.MainActivity", lpparam.classLoader, "getInfmation", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                param.setResult("破解成功——by 一夜梦惊人");

            }

        });
    }

}