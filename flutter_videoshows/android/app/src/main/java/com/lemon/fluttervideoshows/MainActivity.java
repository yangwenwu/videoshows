package com.lemon.fluttervideoshows;

import android.os.Build;
import android.os.Bundle;

import com.lemon.fluttervideoshows.plugin.FlutterPluginJumpToAct;
import com.lemon.fluttervideoshows.plugin.FlutterPluginJumpToVideoDetail;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
    {//API>21,设置状态栏颜色透明
      getWindow().setStatusBarColor(0);
    }
    GeneratedPluginRegistrant.registerWith(this);
    registerCustomPlugin(this);
  }

  private static void registerCustomPlugin(PluginRegistry registrar) {
    FlutterPluginJumpToAct.registerWith(registrar.registrarFor(FlutterPluginJumpToAct.CHANNEL));
    FlutterPluginJumpToVideoDetail.registerWith(registrar.registrarFor(FlutterPluginJumpToVideoDetail.CHANNEL));
  }
}
