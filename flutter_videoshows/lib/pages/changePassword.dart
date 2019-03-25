import 'package:flutter_videoshows/import.dart';

class ChangePassword extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _ChangePasswordState();
  }
  
}

class _ChangePasswordState extends State<ChangePassword>{
  
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
        centerTitle: true,
        elevation: 0.0,
        title: new Text("Changepassword",style: const TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.w700,
            fontFamily: "Lato",
            fontStyle: FontStyle.normal,
            fontSize: 17.3)),
        backgroundColor: Color(0xff171616),
        leading:new IconButton(
            icon: new Image.asset(
              "image/back_arrow3.png",
              height: 25,
              width: 25,
            ),
            onPressed: () {
              Navigator.pop(context);
            })),
      backgroundColor:Color(0xffffffff),
      body: new Column(
        children: <Widget>[
          SizedBox(
            height: 30.0,
          ),
          _buildEdit1(),
          _buildEdit2(),
          _buildEdit3(),
          new Expanded(
            child: new Text(""),
            flex: 1,
          ),
          _buildBottomButton(),
        ],
      ),
    );
  }

  //password
  Widget _buildEdit1() {
    return new Container(
      child: new Column(
        children: <Widget>[
          new TextField(
            textAlign: TextAlign.center,
            decoration: InputDecoration(
                contentPadding: new EdgeInsets.only(
                    left: 10.0, top: 10.0, right: 5.0, bottom: 10.0),
                //特定方向留白
                fillColor: Color(0xffffffff),
                filled: true,
                border: InputBorder.none,
                //下边框
                hintText: "Password",
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

  //new password
  Widget _buildEdit2() {
    return new Container(
      child: new Column(
        children: <Widget>[
          new TextField(
            textAlign: TextAlign.center,
            decoration: InputDecoration(
                contentPadding: new EdgeInsets.only(
                    left: 10.0, top: 10.0, right: 5.0, bottom: 10.0),
                //特定方向留白
                fillColor: Color(0xffffffff),
                filled: true,
                border: InputBorder.none,
                //下边框
                hintText: "New Password",
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

  //Retype
  Widget _buildEdit3() {
    return new Container(
      child: new Column(
        children: <Widget>[
          new TextField(
            textAlign: TextAlign.center,
            decoration: InputDecoration(
                contentPadding: new EdgeInsets.only(
                    left: 10.0, top: 10.0, right: 5.0, bottom: 10.0),
                //特定方向留白
                fillColor: Color(0xffffffff),
                filled: true,
                border: InputBorder.none,
                //下边框
                hintText: "Retype",
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