import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _HomePage();
  }
}

class _HomePage extends State<HomePage> {
  int _selectedIndex = 0;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new MaterialApp(
      theme: new ThemeData(
        brightness: Brightness.dark,
        primaryColor: Colors.black,
        accentColor: Colors.black,
      ),
      home: new Scaffold(
      backgroundColor: Colors.black,
      body: new Text("dddd"),
      bottomNavigationBar: new BottomNavigationBar(
        items: [
          BottomNavigationBarItem(
              icon: Image.asset("image/top.png",height: 30,width: 30,),
              activeIcon: new Image.asset("image/top_selected.png",height: 30,width: 30,),
              title: Text('Top'),
            backgroundColor: Colors.grey
          ),
          BottomNavigationBarItem(
              icon: Image.asset("image/category.png",height: 30,width: 30,),
              activeIcon: new Image.asset("image/category_selected.png",height: 30,width: 30,),
              title: Text('Category'),
              backgroundColor: Colors.red),
          BottomNavigationBarItem(
              icon: Image.asset("image/me.png",height: 30,width: 30,),
              activeIcon: new Image.asset("image/me_selected.png",height: 30,width: 30,),
              title: Text('Me'),
              backgroundColor: Colors.green),
        ],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
        type: BottomNavigationBarType.fixed,
        fixedColor: Colors.blue,
      ),
      ),
    );
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }
}
