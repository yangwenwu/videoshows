import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginInfo with ChangeNotifier{
  String value = "0";

  increment() async {
    SharedPreferences  prefs = await SharedPreferences.getInstance();
//    value =prefs.getString("user");
    value =prefs.getBool("isLogin").toString();
    print("********* value *********$value");
    notifyListeners();
  }
}