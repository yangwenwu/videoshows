import 'package:flutter/material.dart';
import 'package:flutter_videoshows/homePage.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
//    theme: new ThemeData(
//      brightness: Brightness.dark,
//      primaryColor: Colors.lightBlue[800],
//      accentColor: Colors.cyan[600],
//    ),

      home: Welcome(),
      routes: {
        '/home': (_) => new HomePage(),
      },
    );
  }
}

class Welcome extends StatefulWidget {
  Welcome({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _WelcomeState createState() => _WelcomeState();
}

class _WelcomeState extends State<Welcome> {


  @override
  void initState() {
    new Future.delayed(Duration(seconds: 2),(){
//      Navigator.pushAndRemoveUntil(context, newRoute, predicate)
//      Navigator.pushNamedAndRemoveUntil(context, "/home", (route) => route == null );
      Navigator.pushNamedAndRemoveUntil(context, "/home", (Route<dynamic> route) => false );
    });
    super.initState();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
//      appBar: AppBar(
//        title: Text(widget.title),
//      ),
      body: new Stack(
        alignment: Alignment.center,
        children: <Widget>[
          new ConstrainedBox(
            constraints: new BoxConstraints.expand(),
            child: new Image.asset(
              "image/video_launch.png",
              fit: BoxFit.cover,
            ),
          ),
          new Positioned(
            child: new Image.asset(
               "image/launch_logo.png",
               width: 170,
               height: 189,
               alignment: Alignment.center,
             ),
            bottom: MediaQuery.of(context).size.height / 2,
          ),
        ],
      ),
    );
  }
}
