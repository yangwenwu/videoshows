import 'package:flutter/material.dart';
import 'package:flutter_videoshows/pages/first.dart';
import 'package:flutter_videoshows/pages/tob.dart';
import 'package:flutter_videoshows/pages/category.dart';
import 'package:flutter_videoshows/pages/me.dart';
import 'package:flutter/cupertino.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'import.dart';

class HomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _HomePage();
  }
}

class _HomePage extends State<HomePage> with SingleTickerProviderStateMixin {
  int _selectedIndex = 0;

  @override
  void initState() {
    isLogin();
    super.initState();
  }

  void isLogin() async{
    SharedPreferences  prefs = await SharedPreferences.getInstance();
    String userStr = prefs.getString("user");
    if(userStr != null){
      prefs.setBool("isLogin", true);
    }else{
      prefs.setBool("isLogin", false);
    }
  }

  @override
  Widget build(BuildContext context) {
    // 页面body部分组件
    var _body = new IndexedStack(
      children: <Widget>[new TobTab(), new Category(), new Me()],
      index: _selectedIndex,
    );

    return  new WillPopScope(child:
      new Scaffold(
//        backgroundColor: Colors.black,
        body: _body,
        bottomNavigationBar: new CupertinoTabBar(
          backgroundColor: Colors.black,
          items: [
            BottomNavigationBarItem(
              icon: Image.asset(
                "image/top.png",
                height: 25,
                width: 25,
              ),
              activeIcon: new Image.asset(
                "image/top_selected.png",
                height: 25,
                width: 25,
              ),
            ),
            BottomNavigationBarItem(
              icon: Image.asset(
                "image/category.png",
                height: 25,
                width: 25,
              ),
              activeIcon: new Image.asset(
                "image/category_selected.png",
                height: 25,
                width: 25,
              ),
            ),
            BottomNavigationBarItem(
              icon: Image.asset(
                "image/me.png",
                height: 25,
                width: 25,
              ),
              activeIcon: new Image.asset(
                "image/me_selected.png",
                height: 25,
                width: 25,
              ),
            ),
          ],
          currentIndex: _selectedIndex,
          onTap: _onItemTapped,
        )),
        onWillPop: _requestPop);
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  void dispose() {
    //释放内存，节省开销
    super.dispose();
  }


  _showDialog() {
    showDialog<Null>(
      context: context,
      child: new AlertDialog(content: new Text('退出app'), actions: <Widget>[
        new FlatButton(
            onPressed: () {
              Navigator.pop(context);
              SystemNavigator.pop();
            },
            child: new Text('确定'))
      ]),
    );
  }


  Future<bool> _requestPop() {
    _showDialog();
    return new Future.value(false);
  }
}
