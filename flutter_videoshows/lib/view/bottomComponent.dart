import 'package:flutter/material.dart';

import '../textStyleUtil.dart';

class BottomComponent extends StatefulWidget {
//  final IconData icons;
  final String imageStr;
  final MaterialColor color;
  final String btnName;
  final GestureTapCallback onTap;

  BottomComponent(
      {@required this.imageStr,
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
//          Icon(widget._image, color: widget.color),
          Image.asset(widget.imageStr,height: 20,width: 20,),
//          Text(widget.btnName, style: TextStyle(color: widget.color))
          Text(widget.btnName, style: textStyle20)
        ],
      ),
      onTap: widget.onTap,
    );
  }
}