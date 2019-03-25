import 'package:flutter_videoshows/import.dart';

class ChangeName extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _ChangeNameState();
  }
}

class _ChangeNameState extends State<ChangeName> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
          elevation: 0.0,
          backgroundColor: Color(0xff171616),
          centerTitle: true,
          title: new Text(
            "Name",
            style: const TextStyle(
                color: Colors.white,
                fontWeight: FontWeight.w700,
                fontFamily: "Lato",
                fontStyle: FontStyle.normal,
                fontSize: 17.3),
          ),
          leading: new IconButton(
              icon: new Image.asset(
                "image/back_arrow3.png",
                height: 25,
                width: 25,
              ),
              onPressed: () {
                Navigator.pop(context);
              })),
      backgroundColor: Color(0xffffffff),
      body: Column(
        children: <Widget>[
          SizedBox(
            height: 30.0,
          ),
          _buildEdit(),
          new Expanded(
            child: new Text(""),
            flex: 1,
          ),
          _buildBottomButton(),
        ],
      ),
    );
  }

  Widget _buildEdit() {
    return new Container(
      child: new Column(
        children: <Widget>[
          new TextField(
//            autofocus: false,
            textAlign: TextAlign.center,
            decoration: InputDecoration(
                //输入框decoration属性
//            contentPadding: const EdgeInsets.symmetric(vertical: 4.0,), //调整文字跟外面边框的留白，垂直上下方向
//            contentPadding: const EdgeInsets.symmetric(horizontal: 4.0,), //调整文字跟外面边框的留白，水平左右方向
                contentPadding: new EdgeInsets.only(
                    left: 10.0, top: 10.0, right: 5.0, bottom: 10.0),
                //特定方向留白
                fillColor: Color(0xffffffff),
//                  fillColor: Colors.blue,
                filled: true,
                border: InputBorder.none,
                //下边框
                hintText: "name",
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
    return
//      new FlatButton(onPressed: (){}, child: new Text("Save Changes"),);

//      new RaisedButton(
//      onPressed: () {
//        print("dd");
//      },
//      child: new Text("Save Changes"),
//    );

        new Container(
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
//      borderSide: new BorderSide(
//        color: Color(0xffbababa),
//        width: 0.5,
//      ),
        child: new Text(
          "Save Changes",
          style: textStyle5,
        ),
      ),
    );

//      new Container(
//      child: new Text("Save Changes"),
//    );
  }
}
