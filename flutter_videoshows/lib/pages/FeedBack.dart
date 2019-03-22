import 'package:flutter_videoshows/import.dart';

class FeedBack extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _FeedBackState();
  }
}

class _FeedBackState extends State<FeedBack> {
  var value = "";

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        centerTitle: true,
        backgroundColor: Colors.black,
        title: Text("Feedback"),
        actions: <Widget>[
          new Center(
            child: new Padding(
              padding: const EdgeInsets.all(10.0),
              child: new Text("submit"),
            ),
          )
        ],
      ), //f5f5f5
      backgroundColor: const Color(0xfff5f5f5),
      body: new Column(
//        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
//          new ConstrainedBox(
//            constraints: BoxConstraints(
//                maxHeight: 200,
//                maxWidth: 200
//            ),
//            child: new TextField(
//              decoration: InputDecoration(
//                contentPadding: const EdgeInsets.symmetric(vertical: 4.0),
//                hintText: '请输入搜索内容',
//                prefixIcon: Icon(Icons.search),
//                // contentPadding: EdgeInsets.all(10),
//                border: OutlineInputBorder(
//                    borderRadius: BorderRadius.circular(15),
//                    borderSide: BorderSide.none),
//                filled: true,
//                fillColor: Color(0xffaaaaaa),
//              ),
//            ),
//          ),

          new Container(
            decoration: new BoxDecoration(
              border: Border.all(color: Color(0xffffffff), width: 1.0), //灰色的一层边框
              color: Color(0xffffffff),
              borderRadius: new BorderRadius.all(new Radius.circular(0.0)),
            ),
//            alignment: Alignment.center,
//            height: 174,
            padding: EdgeInsets.fromLTRB(10.0, 0.0, 10.0, 20.0),
            child: new TextField(
              cursorColor: Colors.black, //设置光标
              maxLines: 10,
              maxLength: 500,

              decoration: InputDecoration(
                  //输入框decoration属性
//            contentPadding: const EdgeInsets.symmetric(vertical: 4.0,horizontal: 4.0),
//                  contentPadding: new EdgeInsets.only(left: 10.0,top: 10.0,right: 10.0),
                  fillColor: Color(0xffffffff),
                  filled: true,
                  border: InputBorder.none,  //下边框
                  hintText: "we are glad to hear from you",
                  hintStyle:
                      new TextStyle(fontSize: 14, color: Color(0xff777777))),
              style: new TextStyle(fontSize: 15.4, color: Color(0xff777777)),
            ),
          ),
          new SizedBox(
            height: 20.0,
            child: new Container(
//              color: Colors.greenAccent,
            ),
          ),
//          new Padding(padding: const EdgeInsets.all(10.0),child: Text("",),),
//          new Divider(height: 30.0,color: Colors.grey,indent: 10.0,),
//          new Text(
//            "Please leave your email (optional)222",
//            textAlign: TextAlign.left,
//          ),

          new Container(
            decoration: new BoxDecoration(
//              border: Border.all(color: Colors.grey, width: 1.0), //灰色的一层边框
              color: Color(0xffffffff),
              borderRadius: new BorderRadius.all(new Radius.circular(0.0)),
            ),
            alignment: Alignment.center,
            height: 36,
            padding: EdgeInsets.fromLTRB(10.0, 0.0, 0.0, 0.0),
            child: new TextField(
              cursorColor: Colors.white, //设置光标
              decoration: InputDecoration(
                  //输入框decoration属性
//            contentPadding: const EdgeInsets.symmetric(vertical: 1.0,horizontal: 1.0),
                  contentPadding: new EdgeInsets.only(left: 0.0),
//            fillColor: Colors.white,
                  border: InputBorder.none,
                  hintText: "Please leave your email (optional)",
                  hintStyle:
                  new TextStyle(fontSize: 14, color: Color(0xff777777))),
              style: new TextStyle(fontSize: 15.4, color: Color(0xff777777)),
            ),
          ),
        ],
      ),
    );
  }
}

//搜索控件widget
class TextFileWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _TextFileWidgetState();
  }
}

class _TextFileWidgetState extends State<TextFileWidget> {
  Widget buildTextField() {
    //theme设置局部主题
    return TextField(
      cursorColor: Colors.white, //设置光标
      decoration: InputDecoration(
          //输入框decoration属性
//            contentPadding: const EdgeInsets.symmetric(vertical: 1.0,horizontal: 1.0),
          contentPadding: new EdgeInsets.only(left: 0.0),
//            fillColor: Colors.white,
          border: InputBorder.none,
          icon: ImageIcon(
            AssetImage(
              "image/search.png",
            ),
          ),
          hintText: "Video name",
          hintStyle: new TextStyle(fontSize: 14, color: Colors.white)),
      style: new TextStyle(fontSize: 14, color: Colors.white),
    );
  }

  @override
  Widget build(BuildContext context) {
    Widget editView() {
      return Container(
        //修饰黑色背景与圆角
        decoration: new BoxDecoration(
          border: Border.all(color: Colors.grey, width: 1.0), //灰色的一层边框
          color: Colors.grey,
          borderRadius: new BorderRadius.all(new Radius.circular(15.0)),
        ),
        alignment: Alignment.center,
        height: 36,
        padding: EdgeInsets.fromLTRB(10.0, 0.0, 0.0, 0.0),
        child: buildTextField(),
      );
    }

    var cancelView = new Text("cancel");

    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Expanded(
          child: editView(),
          flex: 1,
        ),
        Padding(
          padding: const EdgeInsets.only(
            left: 5.0,
          ),
          child: GestureDetector(
            onTap: () {
//              goBack();
              Navigator.pop(context);
            },
            child: cancelView,
          ),
        )
      ],
    );
  }
}
