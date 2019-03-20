package com.lemon.fluttervideoshows.application;

import com.lemon.fluttervideoshows.http.httpModel.UserBean;
import com.lzy.okgo.OkGo;
import com.mob.MobApplication;
import com.mob.MobSDK;

import io.flutter.app.FlutterApplication;

//public class BaseApp extends MobApplication {
public class BaseApp extends FlutterApplication {
    private static BaseApp sInstance;
    public static boolean isLogin = false;
    public static String token = null;

    //是否是第三方登录
    public static boolean isThirdLogin = false;

    public static UserBean user;

    private static String USER_AGENT;
    public static String getUserAgent() {
        return USER_AGENT;
    }

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    public BaseApp() {
    }

    public void onCreate() {
        super.onCreate();

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//		Config.DEBUG = true;

//        QueuedWork.isUseThreadPool = false;
//        UMShareAPI.get(this);
//        ShareConfigKt.initShare(this);

        //mobSDK
        MobSDK.init(this);

        sInstance = this;

        OkGo.getInstance().init(this);
    }


    {
        //设置linkedin  和 google+
//		PlatformConfig.setTwitter("7yb93CK9HkiNz7oq8xy5cJ2SB", "wYCSh3B0eX7uhuPtujbqWGGSqw9XoDGaLm9izvuhzFSRrQAGGn");
    }

    public static BaseApp getInstance() {
        return sInstance;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaseApp.token = token;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setLogin(boolean isLogin) {
        BaseApp.isLogin = isLogin;
    }

    public static UserBean getUserBean() {
        return user;
    }

    public static void setUserBean(UserBean userBean) {
        BaseApp.user = userBean;
    }

    public static boolean isThirdLogin() {
        return isThirdLogin;
    }

    public static void setThirdLogin(boolean isThirdLogin) {
        BaseApp.isThirdLogin = isThirdLogin;
    }


}
