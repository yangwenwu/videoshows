import 'package:flutter/material.dart';

class ThirdTab extends StatefulWidget {
  @override
  _ThirdTabState createState() => _ThirdTabState();
}

class _ThirdTabState extends State<ThirdTab> {
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
            SizedBox(height: 25,),
            new GestureDetector(
              onTap: (){
                changeAvatar();
              },
              child:
              new Image.asset(
                "image/avatar.png",
                width: 75,
                height: 75,
                alignment: Alignment.center,
              ),
            )
            ,
            new Padding(
              padding: new EdgeInsets.fromLTRB(60.0, 10.0, 60.0, 5.0),
              child: new Row(
                children: <Widget>[
                  new Expanded(
                      child: new OutlineButton(
                        borderSide:
                        new BorderSide(color: Colors.white),
                        child: new Text(
                          'SIGN UP / SIGN IN',
                          style: new TextStyle(color: Colors.white),
                        ),
                        onPressed: () {
                          goLogin();
                        },
                      )),
                ],
              ),
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
        new SizedBox(height: 50,),
        new GestureDetector(
          onTap: (){
            goProfile();
          },
          child:itemWidget("image/profile.png", "Profile"),
        ),
        new SizedBox(height: 10,),
        new GestureDetector(
          onTap: (){
            goBookmark();
          },
          child: itemWidget("image/bookmarks.png", "BookeMark"),
        ),
        new SizedBox(height: 10,),
        new GestureDetector(
          onTap: (){
            goFeedback();
          },
          child: itemWidget("image/feedback.png", "Feedback"),
        ),
        new SizedBox(height: 10,),
        new GestureDetector(
          onTap: (){
            goSettings();
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
      title: new Text("Profile",),
    );
  }

  Widget bodyWidget3() {
    return new ListTile(
      leading: new Image.asset(
        "image/bookmarks.png",
        width: 25,
        height: 25,
      ),
      title: new Text("BookeMark",),
      contentPadding: const EdgeInsets.only(top: -30,bottom: -30),
    );
  }

  Widget bodyWidget4() {
    return new ListTile(
      leading: new Image.asset(
        "image/feedback.png",
        width: 25,
        height: 25,
      ),
      title: new Text("Feedback",),
      contentPadding: const EdgeInsets.only(top: -30,bottom: -30),
    );
  }


  Widget itemWidget(String imagePath, String title) {
    return new Row(
//      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        SizedBox(width: MediaQuery.of(context).size.width*2/5,),
        new Image.asset(
          imagePath,
          width: 25,
          height: 25,
        ),
        new SizedBox(width: 10,),
        new Text(title),
      ],
    );
  }
}

void goLogin(){
  print("点击了登录按钮");
}

void changeAvatar(){
  print("点击了头像");
}

void goProfile(){
  print("点击了profile");
}

void goBookmark(){
  print("点击了bookmark");
}

void goFeedback(){
  print("点击了feedback");
}

void goSettings(){
  print("点击了setting");
}
