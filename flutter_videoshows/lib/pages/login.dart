import 'package:flutter_videoshows/import.dart';

class Login extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new LoginState();
  }
}

class LoginState extends State<Login> with SingleTickerProviderStateMixin {
  bool page1IsVisible = false;
  bool page2IsVisible = true;

  @override
  void initState() {
    super.initState();
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
        SizedBox(
          height: 20,
        ),
        _buildBottomButton(),
      ],
    );

    // TODO: implement build
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
          width: MediaQuery.of(context).size.width,
          child: Image.asset(
            "image/signup_background.png",
            width: MediaQuery.of(context).size.width,
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
                            style:  page1IsVisible?textStyle13:textStyle12,
                          ),
                        ],
                      )),
                ),
                Expanded(
                  child: InkWell(
                    onTap: (){
                      setState(() {
                        page1IsVisible = true;
                        page2IsVisible = false;
                      });
                    },
                      child: new Column(
                    children: <Widget>[
                      new Text(
                        "SIGN ON",
                        style: page2IsVisible?textStyle13:textStyle12,
                      ),
                    ],
                  )),

                ),
              ],
            ),
            new Row(
              children: <Widget>[
                Expanded(child: new Offstage(
                    offstage: page1IsVisible,
                    child: new Image.asset(
                      "image/signup_triangle.png",
                      width: 30,
                      height: 14.4,
                    ))),
                Expanded(child: new Offstage(
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
      width: MediaQuery.of(context).size.width,
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
}
