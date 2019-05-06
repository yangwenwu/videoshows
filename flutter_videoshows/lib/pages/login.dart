import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_videoshows/http/resultData.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:flutter_videoshows/loginInfo.dart';
import 'package:flutter_videoshows/model/facebookLogin.dart';
import 'package:flutter_videoshows/model/userBean.dart';
import 'package:provide/provide.dart';
import 'package:shared_preferences/shared_preferences.dart';


class Login extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new LoginState();
  }
}

class LoginState extends State<Login> with SingleTickerProviderStateMixin {
  static final FacebookLogin facebookSignIn = new FacebookLogin();

  String _message = 'Log in/out by pressing the buttons below.';

  Future<Null> _login() async {
    final FacebookLoginResult result = await facebookSignIn.logInWithReadPermissions(['email']);
    final token = result.accessToken.token;
    switch (result.status) {
      case FacebookLoginStatus.loggedIn:
        final FacebookAccessToken accessToken = result.accessToken;
        _showMessage('''
         Logged in!
         Token: ${accessToken.token}
         User id: ${accessToken.userId}
         Expires: ${accessToken.expires}
         Permissions: ${accessToken.permissions}
         Declined permissions: ${accessToken.declinedPermissions}
         ''');
        Response response = await Dio().get(
            'https://graph.facebook.com/v2.12/me?fields=name,first_name,last_name,email&access_token=${token}');
        print(response.data);
        if(response.data != null){
          final profile = jsonDecode(response.data);
          print(profile);
          var user = new FacebookLoginBean.fromJson(profile);
          print(user.id);
          print(user.name);
          _thirdLogin(user.id,user.name);
        }

        break;
      case FacebookLoginStatus.cancelledByUser:
        _showMessage('Login cancelled by the user.');
        break;
      case FacebookLoginStatus.error:
        _showMessage('Something went wrong with the login process.\n'
            'Here\'s the error Facebook gave us: ${result.errorMessage}');
        break;
    }
  }

  Future<Null> _logOut() async {
    await facebookSignIn.logOut();
    _showMessage('Logged out.');
  }

  void _showMessage(String message) {
    setState(() {
      _message = message;
      print("*********$_message");
    });
  }



  static final TwitterLogin twitterLogin = new TwitterLogin(
    consumerKey: '4gJpDfTN0nQTjIlbFX3iVXC2z',
    consumerSecret: 'jedy0Ju8iqd3cv4qfDmBealiI15EgKJpc4VhYfnEIopXaLEhdk',
  );

  String _tmessage = 'Logged out.';

  void _twitterlogin() async {
    final TwitterLoginResult result = await twitterLogin.authorize();
    String newMessage;

    switch (result.status) {
      case TwitterLoginStatus.loggedIn:
        newMessage = 'Logged in! username: ${result.session.username}';
        _thirdLogin(result.session.userId,result.session.username);
        break;
      case TwitterLoginStatus.cancelledByUser:
        newMessage = 'Login cancelled by user.';
        break;
      case TwitterLoginStatus.error:
        newMessage = 'Login error: ${result.errorMessage}';
        break;
    }

    setState(() {
      _tmessage = newMessage;
      print("*********$_tmessage");
    });
  }

  void _twitterlogout() async {
    await twitterLogin.logOut();

    setState(() {
      _tmessage = 'Logged out.';
    });
  }


  bool page1IsVisible = false;
  bool page2IsVisible = true;


  @override
  void initState() {
    ShareSDKRegister register = ShareSDKRegister();

    register.setupWechat(
        "wxb53cc8df037de602", "7637db23798e83b1aa52d4a8a4b6b470");
    ShareSDK.regist(register);
    ShareSDK.listenNativeEvent();

    super.initState();
  }

  void authToWechat(BuildContext context) {
    ShareSDK.auth(
        ShareSDKPlatforms.wechatSession, null, (SSDKResponseState state,
        Map user, SSDKError error) {
      showAlert(state, user != null ? user : error.rawData, context);
    });
  }

  void getWechatInfo(BuildContext context) {
    ShareSDK.getUserInfo(
        ShareSDKPlatforms.wechatSession, (SSDKResponseState state,
        Map user, SSDKError error) {
          print("*******************$user");
      showAlert(state, user != null ? user : error.rawData, context);
    });


  }


  void shareToWechat(BuildContext context) {
    SSDKMap params = SSDKMap()
      ..setGeneral(
          "title",
          "text",
          [
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541565611543&di=4615c8072e155090a2b833059f19ed5b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F06%2F20150106003502_Ajcte.jpeg"
          ],
          "http://wx3.sinaimg.cn/large/006nLajtly1fpi9ikmj1kj30dw0dwwfq.jpg",
          null,
          "http://www.mob.com/",
          "http://wx4.sinaimg.cn/large/006WfoFPly1fw9612f17sj30dw0dwgnd.jpg",
          "http://i.y.qq.com/v8/playsong.html?hostuin=0&songid=&songmid=002x5Jje3eUkXT&_wv=1&source=qq&appshare=iphone&media_mid=002x5Jje3eUkXT",
          "http://f1.webshare.mob.com/dvideo/demovideos.mp4",
          SSDKContentTypes.webpage);

    ShareSDK.share(
        ShareSDKPlatforms.wechatSession, params, (SSDKResponseState state,
        Map userdata, Map contentEntity, SSDKError error) {
      showAlert(state, error.rawData, context);
    });
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var page1 = new Column(
      children: <Widget>[
        SizedBox(
          height: 20,
        ),
        bodyView("E-mail"),
        bodyView("Password"),
        bodyView("Confirm Password"),
//        SizedBox(
//          height: 20,
//        ),
//        _buildBottomButton(),
      ],
    );

    var page2 = new Column(
      children: <Widget>[
        SizedBox(
          height: 20,
        ),
        bodyView("E-mail"),
        bodyView("Password"),
        Align(
          alignment: Alignment.centerRight,
          child: new Padding(
            padding: EdgeInsets.fromLTRB(0.0, 13.0, 10.0, 30.0),
            child: Text(
              "Foget password?",
              style: textStyle14,
            ),
          ),
        ),
        new Container(
          width: 200,
          padding: EdgeInsets.fromLTRB(10.0, 30.0, 10.0, 10.0),
          child: new OutlineButton(
            highlightedBorderColor: Color(0xffbababa),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.all(Radius.circular(20)),
            ),
            child: new Text(
              'Sign In',
              style: textStyle5,
            ),
            onPressed: () {
//              _goLogin("124976507@qq.com", "gspepjbd");
              login("124976507@qq.com", "gspepjbd");
//                  goLogin();
//              Navigator.push(
//                  context, new MaterialPageRoute(builder: (_) => new Login()));
            },
          ),
        ),
        new Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Container(
              width: 50,
              child: new Divider(
                height: 3.0, color: Color(0xff161616),
              ),
            ),
            new Text("  or sign in with  "),
            new Container(
              width: 50,
              child: new Divider(
                height: 3.0, color: Color(0xff161616),
              ),
            ),
          ],
        ),
        SizedBox(
          height: 30,
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.center, //视图主轴居中
          children: <Widget>[
            new InkWell(
              onTap: () {
                print("facebook");
//                authToWechat(context);
                _login();



              },
              child: new Image.asset(
                "image/share_facebook.png", width: 50, height: 50,),
            ),
            new Padding(padding: EdgeInsets.only(left: 10.0, right: 10.0)),
            new InkWell(
              onTap: () {
                print("twitter");
//                shareToWechat(context);
              _twitterlogin();
              },
              child: new Image.asset(
                "image/share_twitter.png", width: 50, height: 50,),
            ),
            new Padding(padding: EdgeInsets.only(left: 10.0, right: 10.0)),
            new InkWell(
              onTap: () {
                print("wechat");
//                authToWechat(context);
                getWechatInfo(context);
              },
              child: new Image.asset(
                "image/share_wechat.png", width: 50, height: 50,),
            ),

          ],
        )
      ],
    );

    return Scaffold(
        body: new Column(
          children: <Widget>[
            topWidget(),
            Offstage(
              child: page1,
              offstage: page1IsVisible,
            ),
            Offstage(
              child: page2,
              offstage: page2IsVisible,
            ),
          ],
        ));
  }

  Widget topWidget() {
    return Stack(
      children: <Widget>[
        new Container(
          height: 180,
          width: MediaQuery
              .of(context)
              .size
              .width,
          child: Image.asset(
            "image/signup_background.png",
            width: MediaQuery
                .of(context)
                .size
                .width,
            fit: BoxFit.fitWidth,
          ),
        ),
        new IconButton(
            icon: Image.asset(
              "image/back_arrow3.png",
              height: 25,
              width: 25,
            ),
            onPressed: () {
              Navigator.pop(context);
            }),
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            SizedBox(
              height: 135,
            ),
            new Divider(
              color: Colors.white,
            ),
            new Row(
              children: <Widget>[
                Expanded(
                  child: InkWell(
                      onTap: () {
                        setState(() {
                          page1IsVisible = false;
                          page2IsVisible = true;
                        });
                      },
                      child: new Column(
                        children: <Widget>[
                          new Text(
                            "SIGN UP",
                            style: page1IsVisible ? textStyle13 : textStyle12,
                          ),
                        ],
                      )),
                ),
                Expanded(
                  child: InkWell(
                      onTap: () {
                        setState(() {
                          page1IsVisible = true;
                          page2IsVisible = false;
                        });
                      },
                      child: new Column(
                        children: <Widget>[
                          new Text(
                            "SIGN ON",
                            style: page2IsVisible ? textStyle13 : textStyle12,
                          ),
                        ],
                      )),
                ),
              ],
            ),
            new Row(
              children: <Widget>[
                Expanded(
                    child: new Offstage(
                        offstage: page1IsVisible,
                        child: new Image.asset(
                          "image/signup_triangle.png",
                          width: 30,
                          height: 14.4,
                        ))),
                Expanded(
                    child: new Offstage(
                        offstage: page2IsVisible,
                        child: new Image.asset(
                          "image/signup_triangle.png",
                          width: 30,
                          height: 14.4,
                        ))),
              ],
            )
          ],
        ),
      ],
    );
  }

  Widget bodyView(var title) {
    return new Container(
      child: new Column(
        children: <Widget>[
          new TextField(
            textAlign: TextAlign.center,
            decoration: InputDecoration(
                contentPadding: new EdgeInsets.only(
                    left: 10.0, top: 10.0, right: 5.0, bottom: 10.0),
                fillColor: Color(0xffffffff),
                filled: true,
                border: InputBorder.none,
                hintText: title,
                hintStyle:
                new TextStyle(fontSize: 14, color: Color(0xff777777))),
          ),
          new Divider(
            height: 2.0,
          )
        ],
      ),
    );
  }

  Widget _buildBottomButton() {
    return new Container(
      width: MediaQuery
          .of(context)
          .size
          .width,
      padding: const EdgeInsets.fromLTRB(20.0, 0.0, 20.0, 20.0),
      child: new OutlineButton(
        highlightedBorderColor: Color(0xffbababa),
        onPressed: () {
          print("提交数据");
        },
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(20)),
        ),
        child: new Text(
          "Save Changes",
          style: textStyle5,
        ),
      ),
    );
  }

  void showAlert(SSDKResponseState state, Map content, BuildContext context) {
    print("--------------------------> state:" + state.toString());
    String title = "失败";
    switch (state) {
      case SSDKResponseState.Success:
        title = "成功";
        print("wechat ******* unionid = ${content["unionid"]} **nickname = ${content["nickname"]} *** headimgurl = ${content["headimgurl"]}");
          wechatLogin(content["unionid"], content["nickname"], content["headimgurl"]);
        break;
      case SSDKResponseState.Fail:
        title = "失败";
        break;
      case SSDKResponseState.Cancel:
        title = "取消";
        break;
      default:
        title = state.toString();
        break;
    }

//    showDialog(
//        context: context,
//        builder: (BuildContext context) =>
//            CupertinoAlertDialog(
//                title: new Text(title),
//                content: new Text(content != null ? content.toString() : ""),
//                actions: <Widget>[
//                  new FlatButton(
//                    child: new Text("OK"),
//                    onPressed: () {
//                      Navigator.of(context).pop();
//                    },
//                  )
//                ]
//            ));
  }

  Future wechatLogin(var id ,var name,var headImage) async {
    Response response = await Dio().get(
        '${Constant.STATICURL}otherLogin?otherAccount=$id&nickname=$name&headImage=headImage');
    print(response.data);
    if(response.data != null){
      Map map = response.data;
      var userBean = new UserBean.fromJson(map);

      print("map[resObject]*****************");
      print(map["resObject"]);
      print("map[resObject].toString()*****************");
      print(map["resObject"].toString());
      saveUser(response.data);
      Navigator.pop(context,json.encode(response.data));
    }
  }

  Future _thirdLogin(var id,var name) async{
    Response response = await Dio().get(
        '${Constant.STATICURL}otherLogin?otherAccount=$id&nickname=$name&headImage=');
    print(response.data);
    if(response.data != null){
      Map map = response.data;
      var userBean = new UserBean.fromJson(map);

      print("map[resObject]*****************");
      print(map["resObject"]);
      print("map[resObject].toString()*****************");
      print(map["resObject"].toString());
      saveUser(response.data);
      Navigator.pop(context,json.encode(response.data));
    }
  }

  Future _goLogin(var account,var password) async{
    FormData formData = new FormData.from({
      "account": account,
      "password": password,
    });
    Response response = await Dio().post(
        '${Constant.STATICURL}login',data: formData);
    print(response.data);
    if(response.data != null){
      Map map = response.data;
      var userBean = new UserBean.fromJson(map);
      print("map[resObject]*****************");
      print(map["resObject"]);
      print("map[resObject].toString()*****************");
      print(map["resObject"].toString());
//      saveUser(response.data);
//      Navigator.pop(context,json.encode(response.data));
    }
  }

  login(String account,String psw ) async {
    ResultData resultData = await Api.login(account, psw);
    print(resultData);
  }




  void  saveUser(var userStr) async {
    print("**************user******$userStr");
    SharedPreferences  prefs = await SharedPreferences.getInstance();
    prefs.setString("user", json.encode(userStr));
    prefs.setBool("isLogin", true);
    Provide.value<LoginInfo>(context).increment();
  }

}
