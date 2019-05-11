package com.wenfengtou.xposedsimpledemo;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    private static final String TAG = "HookMain";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(!lpparam.packageName.equals("com.tencent.weread")) return;
        XposedBridge.log("wenfengtou start com.tencent.weread");
        Log.i(TAG,"wenfengtou start com.tencent.weread");
        ClassLoader classLoader = lpparam.classLoader;
        Class<?> accountManagerClass = classLoader.loadClass("com.tencent.weread.account.model.AccountManager");
        Class<?> accountClass = classLoader.loadClass("com.tencent.weread.model.domain.Account");
        Log.i(TAG,"wenfengtou load accountClass " + accountClass);
        if (accountClass != null) {
            XposedBridge.log("wenfengtou load class " + accountClass.toString());
            Log.i(TAG,"wenfengtou load class " + accountClass.toString());
        }
        /*888888888888888888888888888888888*/
        XposedHelpers.findAndHookMethod("com.tencent.weread.account.model.AccountManager", lpparam.classLoader, "saveAccount", accountClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object accountObj = param.args[0];
                Log.i(TAG, "afterHookedMethod saveAccount");
                Object MemberCard = XposedHelpers.callMethod(accountObj, "getMemberCard");
                if (MemberCard != null) {
                    Log.i(TAG, "MemberCard =" + (String) MemberCard.toString());
                }
            }
        });
        /*888888888888888888888888888888888*/

        /*888888888888888888888888888888888*/
        Class<?> friendRankClass = XposedHelpers.findClass("com.tencent.weread.model.domain.FriendRank", classLoader);
        if (friendRankClass != null) {
            XposedBridge.log("wenfengtou load class " + friendRankClass.toString());
            Log.i(TAG,"wenfengtou load class " + friendRankClass.toString());
        }
        XposedHelpers.findAndHookMethod("com.tencent.weread.home.view.PersonalView", lpparam.classLoader, "renderRankAndExchange", friendRankClass, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object friendRankObj = param.args[0];
                Object i = param.args[1];
                Log.i(TAG, "before renderRankAndExchange");
                //修改阅读时间显示
                //Object readingTime = XposedHelpers.callMethod(friendRankObj, "setReadingTime", 6000);
                //Log.i(TAG, "readingTime = " +  readingTime + " i = " + i);
            }
        });
        /*888888888888888888888888888888888*/

        /*888888888888888888888888888888888*/
        Class<?> userInfoClass = XposedHelpers.findClass("com.tencent.weread.model.domain.UserInfo", classLoader);
        if (userInfoClass != null) {
            XposedBridge.log("wenfengtou load class " + userInfoClass.toString());
            Log.i(TAG,"wenfengtou load class " + userInfoClass.toString());
        }
        XposedHelpers.findAndHookMethod("com.tencent.weread.home.fragment.PersonalController", lpparam.classLoader, "renderUserInfo", userInfoClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Object userInfoObj = param.args[0];
                Log.i(TAG, "before renderUserInfo");
                //修改兑换数量显示
                //XposedHelpers.callMethod(userInfoObj, "setCanExchange", 400);
                //Log.i(TAG, "userInfoObj = " +  userInfoObj);
            }
        });
        /*888888888888888888888888888888888*/

        /*888888888888888888888888888888888*/
        XposedHelpers.findAndHookMethod("com.tencent.weread.model.domain.UserInfo", lpparam.classLoader, "convertTo",  new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //Log.i(TAG, "before convertTo" ,new Throwable());
                Log.i(TAG, "userInfoObj = " +  param.thisObject);
                //XposedHelpers.callMethod(param.thisObject,"setCurrentReadingTime", 6000);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.i(TAG, "after convertTo");
                Object currentReadTime = XposedHelpers.callMethod(param.thisObject,"getCurrentReadingTime");
                Log.i(TAG, "currentReadTime = " +  currentReadTime);
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.weread.model.domain.UserInfo", lpparam.classLoader, "convertFrom", Cursor.class,  new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //Log.i(TAG, "before convertFrom" ,new Throwable());
                Log.i(TAG, "userInfoObj = " +  param.thisObject);
                //XposedHelpers.callMethod(param.thisObject,"setCurrentReadingTime", 6000);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //Log.i(TAG, "after convertFrom");
                //Object currentReadTime = XposedHelpers.callMethod(param.thisObject,"getCurrentReadingTime");
               // Log.i(TAG, "currentReadTime = " +  currentReadTime);
            }
        });
        /*888888888888888888888888888888888*/

        /*888888888888888888888888888888888*/
        final Class<?> accelerate100Class = XposedHelpers.findClass("com.tencent.weread.book.feature.Accelerate100", classLoader);
        if (accelerate100Class != null) {
            XposedBridge.log("wenfengtou load class " + accelerate100Class.toString());
            Log.i(TAG,"wenfengtou load class " + accelerate100Class.toString());
        }
        XposedHelpers.findAndHookMethod("com.tencent.weread.book.feature.Accelerate1", lpparam.classLoader, "acceleration", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.i(TAG, "after acceleration");
                //XposedHelpers.callMethod(param.thisObject, "mapIndex",Integer.valueOf(1),1,accelerate100Class);
                //param.setResult(100);
                //修改兑换数量显示
                //XposedHelpers.callMethod(userInfoObj, "setCanExchange", 400);
                //Log.i(TAG, "userInfoObj = " +  userInfoObj);
            }
        });


        final Class<?> readingProgressReporterClass = XposedHelpers.findClass("com.tencent.weread.reader.util.ReadingProgressReporter", classLoader);
        if (accelerate100Class != null) {
            XposedBridge.log("wenfengtou load class " + accelerate100Class.toString());
            Log.i(TAG,"wenfengtou load class " + accelerate100Class.toString());
        }
        XposedHelpers.findAndHookMethod("com.tencent.weread.book.fragment.ProgressReportStrategy", lpparam.classLoader, "onPageChange",int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedHelpers.setStaticIntField(readingProgressReporterClass,"REPORT_READ_PROGRESS_INTERVAL",Integer.MAX_VALUE/4);
                Log.i(TAG, "after onPageChange" + XposedHelpers.getStaticIntField(readingProgressReporterClass, "REPORT_READ_PROGRESS_INTERVAL"));
                //XposedHelpers.callMethod(param.thisObject, "mapIndex",Integer.valueOf(1),1,accelerate100Class);
                //param.setResult(100);
                //修改兑换数量显示
                //XposedHelpers.callMethod(userInfoObj, "setCanExchange", 400);
                //Log.i(TAG, "userInfoObj = " +  userInfoObj);
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.weread.book.fragment.ProgressReportStrategy", lpparam.classLoader, "calculateReadingTime",new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.i(TAG, "after calculateReadingTime" + XposedHelpers.getLongField(param.thisObject,"readingTime"));

                Long readingTime = XposedHelpers.getLongField(param.thisObject,"readingTime");
                XposedHelpers.setLongField(param.thisObject,"readingTime", readingTime + 24*60*60000);
                //XposedHelpers.callMethod(param.thisObject, "mapIndex",Integer.valueOf(1),1,accelerate100Class);
                //param.setResult(100);
                //修改兑换数量显示
                //XposedHelpers.callMethod(userInfoObj, "setCanExchange", 400);
                //Log.i(TAG, "userInfoObj = " +  userInfoObj);
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.weread.util.WRLog", lpparam.classLoader, "log", int.class , String.class, String.class,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.i(TAG, "before WRLog log" + param.args[1] + " " + param.args[2]);
            }
        });

        XposedHelpers.findAndHookMethod("com.tencent.weread.util.WRLog", lpparam.classLoader, "log", int.class , String.class, String.class,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.i(TAG, "before WRLog log" + param.args[1] + " " + param.args[2]);
            }
        });

        final Class<?> offlineReadingInfoClass = XposedHelpers.findClass("com.tencent.weread.model.domain.OfflineReadingInfo", classLoader);
        if (offlineReadingInfoClass != null) {
            XposedBridge.log("wenfengtou load class " + offlineReadingInfoClass.toString());
            Log.i(TAG,"wenfengtou load class " + offlineReadingInfoClass.toString());
        }
        XposedHelpers.findAndHookMethod("com.tencent.weread.book.ReportService", lpparam.classLoader, "getOfflineReadingInfos", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Log.i(TAG, "after getOfflineReadingInfos wenfengtou2");
                Object offlineReadingInfo = XposedHelpers.newInstance(offlineReadingInfoClass);
                XposedHelpers.callMethod(offlineReadingInfo, "setId",1412577512 );
                XposedHelpers.callMethod(offlineReadingInfo, "setBookId","721367" );
                XposedHelpers.callMethod(offlineReadingInfo, "setChapterUid",17 );
                XposedHelpers.callMethod(offlineReadingInfo, "setChapterOffset",10311 );
                XposedHelpers.callMethod(offlineReadingInfo, "setSummary","面对生老病死带来的未知和恐惧，人们总会有某种宗教情结油然而生。且不说已经确诊的患者，即使是健康的人，在做每年一度的例行体检时，医生哪怕一句不经意的提问，类似“平时感觉哪儿不舒服吗”，都会紧张得要命，直到结果出来没问题，这一身汗才算落下去。至于真有病的患者，医生说三句话就能直接把小病吓成中病，中病变成大病，大病直接弄死。第一句“你怎么才来啊”，第二句“想吃点啥就吃点啥吧”，第三句“还真没有什么办法”。尤其是第一句，多坚强的人，一听这话立马吓软了。\n" +
                        "1915年，美国一位名叫特鲁多的医生去世了，他的墓碑上刻着三行字：“偶尔去治愈，常常去帮助，总是在抚慰。”这三行字里，似乎有着对医生这个职业更为深远的定义。\n" +
                        "特鲁多医生年轻的时候，曾患过肺结核，那时的肺结核患者相当于被宣判了死刑。他来到一个宁静的湖畔，等待着独自告别世界，可" );
                XposedHelpers.callMethod(offlineReadingInfo, "setProgress",-1 );
                XposedHelpers.callMethod(offlineReadingInfo, "setReadingTime",36000);
                XposedHelpers.callMethod(offlineReadingInfo, "setInMyShelf",true );
                ArrayList arrayList = new ArrayList();
                arrayList.add(offlineReadingInfo);
                param.setResult(arrayList);

            }
        });
        /*888888888888888888888888888888888*/

    }

}