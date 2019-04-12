import 'package:flutter_videoshows/import.dart';
import 'package:flutter_videoshows/loginInfo.dart';
import 'package:provide/provide.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Settings extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SettingsState();
  }
}

class _SettingsState extends State<Settings> {
  bool logout = false;

  @override
  Widget build(BuildContext context) {
    return new WillPopScope(child:
      new Scaffold(
      appBar: new AppBar(
        backgroundColor: Color(0xff171616),
        centerTitle: true,
        title: Text(
          "Settings",
          style: const TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.w700,
              fontFamily: "Lato",
              fontStyle: FontStyle.normal,
              fontSize: 17.3),
        ),
        leading: new IconButton(
            icon: Image.asset(
              "image/back_arrow3.png",
              width: 25,
              height: 25,
            ),
            onPressed: () {
              Navigator.pop(context,"out");
            }),
      ),
      backgroundColor: Color(0xfff5f5f5),
      body: Column(
        children: <Widget>[
          SizedBox(
            height: 10.0,
          ),
          _buildView('Clear cache', true),
          SizedBox(
            height: 10.0,
          ),
          _buildView('Version and update', false),
          SizedBox(
            height: 10.0,
          ),
          _buildView('Privacy policy', false),
          SizedBox(
            height: 10.0,
          ),
          _buildView('About us', false),
          SizedBox(
            height: 10.0,
          ),
          _buildView('Accessibility statement', false),
          SizedBox(
            height: 10.0,
          ),
          _buildLogOut()
        ],
      ),
    ),
        onWillPop: (){
          Navigator.pop(context,"out");
        });
  }

  Widget _buildView(var value, bool isVisible) {
    return new Container(
      color: Color(0xffffffff),
      height: 45,
      child: new Row(
        children: <Widget>[
          new Expanded(
              flex: 1,
              child: new Padding(
                padding: const EdgeInsets.only(left: 10.0),
                child: Text(
                  value,
                  style: textStyle1,
                ),
              )),
          _buildViewText(isVisible),
          new Padding(
            padding: const EdgeInsets.only(left: 10.0, right: 10.0),
            child: Text(
              ">",
              style: textStyle2,
            ),
          )
        ],
      ),
    );
  }

  Widget _buildViewText(bool isVisible) {
    if (isVisible) {
      return Text("2.04MB");
    } else {
      return Text("");
    }
  }

  Widget _buildLogOut() {
    return InkWell(
      onTap: (){
        print("******* 退出*****");
        logoutUser();
        setState(() {

        });
      },
      child: new Container(
        color: Color(0xffffffff),
        height: 45,
        alignment: Alignment.center,
        child: Text(
          logout ? "" :"Log Out",
          style: textStyle1,
        ),
      ),
    );
  }


  void  logoutUser() async {
    SharedPreferences  prefs = await SharedPreferences.getInstance();
    prefs.setString("user", null);
    prefs.setBool("isLogin", false);
    logout = true;
    Provide.value<LoginInfo>(context).increment();
  }
}
