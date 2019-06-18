package com.wenfengtou.xposedsimpledemo;

import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class AutoReadHook implements IXposedHookLoadPackage {
        private static final String TAG = "AutoReadHook";
        private ClassLoader mClassLoader;
        @Override
        public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
            Log.i(TAG, "AutoReadHook start");
            mClassLoader = lpparam.classLoader;
            //Weread
            if(lpparam.packageName.equals("com.tencent.weread")) {
                final Class pageClass = XposedHelpers.findClass("com.tencent.weread.reader.domain.Page", mClassLoader);
                XposedHelpers.findAndHookMethod("com.tencent.weread.reader.container.pageview.BasePageView", lpparam.classLoader, "setPage", pageClass, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Object pageObj = param.args[0];
                        //Class PageSummaryClass = XposedHelpers.findClass("com.tencent.weread.reader.domain.Page.getPageSummary", mClassLoader);
                        String summary = (String) XposedHelpers.callMethod(pageObj,"getSummary");
                        Object pageSummaryObj = XposedHelpers.callMethod(pageObj, "getPageSummary");
                        ArrayList pageContent = (ArrayList) XposedHelpers.getObjectField(pageObj, "mContent");
                        Log.i(TAG, "before setPage" + pageObj
                    /*
                    + " summary=" + summary + " PageSummaryObj=" + pageSummaryObj
                    + " PageSummary.content=" + XposedHelpers.getObjectField(pageSummaryObj,"content")
                    */
                                        + " Page.mContent=" + pageContent
                        );
                    }
                });


                XposedHelpers.findAndHookMethod("android.graphics.Canvas", lpparam.classLoader, "drawText", char[].class, int.class, int.class, float.class, float.class, Paint.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Object charArr = param.args[0];
                        Log.i(TAG, "before drawText" + String.copyValueOf((char[]) charArr));
                    }
                });

                XposedHelpers.findAndHookMethod("android.view.View", lpparam.classLoader, "setContentDescription", CharSequence.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Object charArr = param.args[0];
                        Log.i(TAG, "before setContentDescription" + charArr);
                    }
                });

                XposedHelpers.findAndHookMethod("android.view.View", lpparam.classLoader, "notifyViewAccessibilityStateChangedIfNeeded", int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Object changeType = param.args[0];
                        Log.i(TAG, "before notifyViewAccessibilityStateChangedIfNeeded " + changeType);
                    }
                });

                XposedHelpers.findAndHookMethod("android.view.ViewRootImpl", lpparam.classLoader, "requestSendAccessibilityEvent", View.class, AccessibilityEvent.class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                AccessibilityEvent event = (AccessibilityEvent) param.args[1];
                                Log.i(TAG, "before requestSendAccessibilityEvent " + event);
                            }
                        });

                XposedHelpers.findAndHookMethod("android.view.accessibility.AccessibilityManager", lpparam.classLoader, "sendAccessibilityEvent", AccessibilityEvent.class,
                        new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                AccessibilityEvent event = (AccessibilityEvent) param.args[0];
                                Log.i(TAG, "before AccessibilityManager.sendAccessibilityEvent " + event);
                            }
                        });
            }

            //System Server
            if(lpparam.packageName.equals("android")) {
                XposedHelpers.findAndHookMethod("com.android.server.accessibility.AccessibilityManagerService", lpparam.classLoader, "sendAccessibilityEvent", AccessibilityEvent.class, int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        AccessibilityEvent event = (AccessibilityEvent) param.args[0];
                        Log.i(TAG, "before AccessibilityManagerService.sendAccessibilityEvent " + event);
                    }
                });
            }


        }

}

