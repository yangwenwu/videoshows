package com.lemon.fluttervideoshows.plugin;

import android.app.Activity;
import android.content.Intent;


import com.lemon.fluttervideoshows.video.VideoDetailActivity;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FlutterPluginJumpToVideoDetail implements MethodChannel.MethodCallHandler {

    public static String CHANNEL = "com.lemon.jump.video/plugin";

    static MethodChannel channel;

    private Activity activity;

    private FlutterPluginJumpToVideoDetail(Activity activity) {
        this.activity = activity;
    }

    public static void registerWith(PluginRegistry.Registrar registrar) {
        channel = new MethodChannel(registrar.messenger(), CHANNEL);
        FlutterPluginJumpToVideoDetail instance = new FlutterPluginJumpToVideoDetail(registrar.activity());
        //setMethodCallHandler在此通道上接收方法调用的回调
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {

        //通过MethodCall可以获取参数和方法名，然后再寻找对应的平台业务，本案例做了2个跳转的业务
        if (call.method.equals("VideoDetail")) {
            //解析参数
//            String text = call.argument("VideoDetail");
//            DateList dateList = call.argument("VideoDetail");
            //带参数跳转到指定Activity
            String title = call.argument("title");
            String bigTitleImage = call.argument("bigTitleImage");
            String subjectCode = call.argument("subjectCode");
            String titleImage = call.argument("titleImage");
            String dataId = call.argument("dataId");
            String jsonUrl = call.argument("jsonUrl");
            String description = call.argument("description");
            Intent intent = new Intent(activity, VideoDetailActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("bigTitleImage", bigTitleImage);
            intent.putExtra("subjectCode", subjectCode);
            intent.putExtra("titleImage", titleImage);
            intent.putExtra("dataId", dataId);
            intent.putExtra("jsonUrl", jsonUrl);
            intent.putExtra("description", description);
            activity.startActivity(intent);

            //返回给flutter的参数
            result.success("success");
        }
        else {
            result.notImplemented();
        }
    }

}
