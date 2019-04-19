import 'package:flutter/material.dart';

class BottomComponent extends StatefulWidget {
  final IconData icons;
  final MaterialColor color;
  final String btnName;
  final GestureTapCallback onTap;

  BottomComponent(
      {@required this.icons,
        @required this.btnName,
        this.color = Colors.grey,
        this.onTap});

  @override
  State<BottomComponent> createState() {
    return BottomComponentState();
  }
}

class BottomComponentState extends State<BottomComponent> {
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Icon(widget.icons, color: widget.color),
          Text(widget.btnName, style: TextStyle(color: widget.color))
        ],
      ),
      onTap: widget.onTap,
    );
  }
}