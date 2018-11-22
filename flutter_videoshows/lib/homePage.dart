import 'package:flutter/material.dart';
import 'package:flutter_videoshows/pages/FirstTab.dart';
import 'package:flutter_videoshows/pages/SecondTab.dart';
import 'package:flutter_videoshows/pages/ThirdTab.dart';
import 'package:flutter/cupertino.dart';

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
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    // 页面body部分组件
    var _body = new IndexedStack(
      children: <Widget>[new FirstTab(), new SecondTab(), new ThirdTab()],
      index: _selectedIndex,
    );

    return new Scaffold(
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

        ));
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
}
