import 'package:flutter/material.dart';

class LoginInfo with ChangeNotifier{
  int value = 0;

  increment(){
    value++;
    notifyListeners();
  }
}