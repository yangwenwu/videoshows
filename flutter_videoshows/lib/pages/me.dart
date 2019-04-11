import 'dart:convert';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:flutter_videoshows/loginInfo.dart';
import 'package:flutter_videoshows/model/userBean.dart';
import 'package:provide/provide.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Me extends StatefulWidget {
  @override
  _MeState createState() => _MeState();
}

class _MeState extends State<Me> {
  bool _isLogin = false;
  UserBean user;

  //获取到插件与原生的交互通道
  static const jumpPlugin = const MethodChannel('com.lemon.jump/plugin');

  @override
  void initState() {
    isLogin();
    super.initState();
  }

  void isLogin() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    var userStr = prefs.getString("user");
    _isLogin = prefs.getBool("isLogin");
    if (userStr != null) {
      print(userStr);
      var profile = jsonDecode(userStr);
      print(profile);
      user = new UserBean.fromJson(profile);
    }
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
//      appBar: new AppBar(
//        backgroundColor: Colors.black,
//      ),
      body: new Center(
        child: new Column(
          children: <Widget>[
            topWidget(),
            bodyWidget(),
          ],
        ),
      ),
    );
  }

  Widget avatar() {
    if (_isLogin) {
      return new Container(
        height: 75,
        width: 75,
        child: new ClipRRect(
          child: new CachedNetworkImage(
            imageUrl: user.resObject.headImage,
            placeholder: (context, url) =>
                new Image.asset("image/avatar.png", width: 75, height: 75),
            errorWidget: (context, url, error) =>
                new Image.asset("image/avatar.png"),
          ),
          borderRadius: BorderRadius.all(Radius.circular(90)),
        ),
      );
    } else {
      return new Image.asset(
        "image/avatar.png",
        width: 75,
        height: 75,
        alignment: Alignment.center,
      );
    }
  }

  Widget nickName() {
    if (_isLogin) {
      return new Text(user.resObject.nickName);
    } else {
      return new OutlineButton(
        borderSide: new BorderSide(color: Colors.white),
        child: new Text(
          'SIGN UP / SIGN IN',
          style: new TextStyle(color: Colors.white),
        ),
        onPressed: () {
//                  goLogin();
          Navigator.push(
              context, new MaterialPageRoute(builder: (_) => new Login()));
        },
      );
    }
  }

  Widget topWidget() {
    return Stack(
      alignment: AlignmentDirectional.center,
      children: <Widget>[
        new Container(
          height: 200,
          width: MediaQuery.of(context).size.width,
          child: Image.asset(
            "image/me_background.png",
            width: MediaQuery.of(context).size.width,
            fit: BoxFit.fitWidth,
          ),
        ),
        Column(
          children: <Widget>[
            SizedBox(
              height: 25,
            ),
            Provide<LoginInfo>(
              builder: (context, child, loginInfo) {
                return Text(
                  '${loginInfo.value}',
                  style: Theme.of(context).textTheme.display1,
                );
              },
            ),
            new GestureDetector(
                onTap: () {
                  changeAvatar();
                },
                child: avatar()

//              new Image.asset(
//                "image/avatar.png",
//                width: 75,
//                height: 75,
//                alignment: Alignment.center,
//              ),
                ),
            new Container(
              padding: EdgeInsets.only(top: 10.0),
              child: nickName(),

//              new OutlineButton(
//                borderSide: new BorderSide(color: Colors.white),
//                child: new Text(
//                  'SIGN UP / SIGN IN',
//                  style: new TextStyle(color: Colors.white),
//                ),
//                onPressed: () {
////                  goLogin();
//                  Navigator.push(
//                      context, new MaterialPageRoute(builder: (_) => new Login()));
//                },
//              ),

//              child: new Row(
//                children: <Widget>[
//                  new Expanded(
//                      child: new OutlineButton(
//                    borderSide: new BorderSide(color: Colors.white),
//                    child: new Text(
//                      'SIGN UP / SIGN IN',
//                      style: new TextStyle(color: Colors.white),
//                    ),
//                    onPressed: () {
//                      goLogin();
//                    },
//                  )
//                  ),
//                ],
//              ),
            ),
          ],
        ),
      ],
    );
  }

  Widget bodyWidget() {
    return new Column(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: <Widget>[
        new SizedBox(
          height: 50,
        ),
        new InkWell(
          onTap: () {
            goProfile();
            Navigator.push(
                context, new MaterialPageRoute(builder: (_) => new Profile()));
          },
          child: itemWidget("image/profile.png", "Profile"),
        ),
        new SizedBox(
          height: 10,
        ),
        new InkWell(
          onTap: () {
            goBookmark();
          },
          child: itemWidget("image/bookmarks.png", "BookeMark"),
        ),
        new SizedBox(
          height: 10,
        ),
        new InkWell(
          onTap: () {
            goFeedback();
            Navigator.push(
                context,
                new MaterialPageRoute(
                  builder: (_) => new FeedBack(),
//              new Counter(),
                ));
          },
          child: itemWidget("image/feedback.png", "Feedback"),
        ),
        new SizedBox(
          height: 10,
        ),
        new InkWell(
          onTap: () {
            Navigator.push(context,
                new MaterialPageRoute(builder: (context) => new Settings()));
          },
          child: itemWidget("image/settings.png", "Setting"),
        )

//        bodyWidget2(),
//        bodyWidget3(),
//        bodyWidget4(),
      ],
    );
  }

  Widget bodyWidget2() {
    return new ListTile(
      leading: new Image.asset(
        "image/profile.png",
        width: 25,
        height: 25,
      ),
      title: new Text(
        "Profile",
      ),
    );
  }

  Widget bodyWidget3() {
    return new ListTile(
      leading: new Image.asset(
        "image/bookmarks.png",
        width: 25,
        height: 25,
      ),
      title: new Text(
        "BookeMark",
      ),
      contentPadding: const EdgeInsets.only(top: -30, bottom: -30),
    );
  }

  Widget bodyWidget4() {
    return new ListTile(
      leading: new Image.asset(
        "image/feedback.png",
        width: 25,
        height: 25,
      ),
      title: new Text(
        "Feedback",
      ),
      contentPadding: const EdgeInsets.only(top: -30, bottom: -30),
    );
  }

  Widget itemWidget(String imagePath, String title) {
    return new Row(
//      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        SizedBox(
          width: MediaQuery.of(context).size.width * 2 / 5,
        ),
        new Image.asset(
          imagePath,
          width: 25,
          height: 25,
        ),
        new SizedBox(
          width: 10,
        ),
        new Text(title),
      ],
    );
  }
}

void goLogin() {
  print("点击了登录按钮");
}

void changeAvatar() {
  print("点击了头像");
}

void goProfile() {
  print("点击了profile");
}

void goBookmark() {
  print("点击了bookmark");
}

void goFeedback() {
  print("点击了feedback");
}

void goSettings() {
  print("点击了setting");
}
