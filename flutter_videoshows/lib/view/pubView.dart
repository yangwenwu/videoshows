import 'package:flutter_videoshows/import.dart';

//loading  = new Center(child: new CircularProgressIndicator());
var loading = new Stack(
  children: <Widget>[
    new Padding(
      padding: new EdgeInsets.fromLTRB(0.0, 0.0, 0.0, 35.0),
      child: new Center(
        child: SpinKitFadingCircle(
          color: Colors.blueAccent,
          size: 30.0,
        ),
      ),
    ),
    new Padding(
      padding: new EdgeInsets.fromLTRB(0.0, 35.0, 0.0, 0.0),
      child: new Center(
        child: new Text(""),
      ),
    ),
  ],
);