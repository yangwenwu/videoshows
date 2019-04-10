import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_videoshows/homePage.dart';
import 'package:flutter_videoshows/import.dart';

import 'loginInfo.dart';
import 'package:provide/provide.dart';

void main(){
  var loginInfo = LoginInfo();
  var providers = Providers();

  providers
    ..provide(Provider<LoginInfo>.value(loginInfo));
//  runApp(_widgetForRoute(window.defaultRouteName));
  runApp(ProviderNode(child: _widgetForRoute(window.defaultRouteName), providers: providers));
}

Widget _widgetForRoute(String route) {
  switch (route) {
    case 'route1':
//      return MyApp();
//      return FirstTab();
      return MyTest();
    default:
//      return FirstTab();
      return MyApp();
  }
}





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
//        '/search': (_)=> new Search(),
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
    super.initState();
    new Future.delayed(Duration(seconds: 2),(){
//      Navigator.pushAndRemoveUntil(context, newRoute, predicate)
//      Navigator.pushNamedAndRemoveUntil(context, "/home", (route) => route == null );
      Navigator.pushNamedAndRemoveUntil(context, "/home", (Route<dynamic> route) => false );
    });
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



class MyTest extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
//      routes: {
//        '/home': (_) => new MyHomePage(),
//      },
    );
  }
}


class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Invoke "debug painting" (press "p" in the console, choose the
          // "Toggle Debug Paint" action from the Flutter Inspector in Android
          // Studio, or the "Toggle Debug Paint" command in Visual Studio Code)
          // to see the wireframe for each widget.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.display1,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}