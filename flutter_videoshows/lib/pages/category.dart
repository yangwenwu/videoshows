import 'package:flutter/material.dart';

class SecondTab extends StatefulWidget {
  @override
  _SecondTabState createState() => _SecondTabState();
}

class _SecondTabState extends State<SecondTab> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: Colors.black,
        title: new Text('第二个界面'),
      ),
      body: new Center(
        child: new Text('second'),
      ),
    );
  }
}
