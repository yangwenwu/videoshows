
import 'package:flutter/material.dart';

class ThirdTab extends StatefulWidget {
  @override
  _ThirdTabState createState() => _ThirdTabState();
}

class _ThirdTabState extends State<ThirdTab> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: Colors.black,
        title: new Text('第三个界面'),
      ),
      body: new Center(
        child: new Text('third'),
      ),
    );
  }
}
