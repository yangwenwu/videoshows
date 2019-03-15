package com.lemon.fluttervideoshows;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.FrameLayout;

import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;

public class FlutterActivity extends io.flutter.app.FlutterActivity {


    private String routeStr = "";

    @Override
    public FlutterView createFlutterView(Context context) {
        WindowManager.LayoutParams matchParent = new WindowManager.LayoutParams(-1, -1);
        FlutterNativeView nativeView = this.createFlutterNativeView();
        FlutterView flutterView = new FlutterView(FlutterActivity.this, (AttributeSet) null, nativeView);
        flutterView.setInitialRoute(routeStr);
        flutterView.setLayoutParams(matchParent);
        this.setContentView(flutterView);
        return flutterView;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        routeStr = "route1";
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {//API>21,设置状态栏颜色透明
            getWindow().setStatusBarColor(0);
        }
        GeneratedPluginRegistrant.registerWith(this);
    }
}
