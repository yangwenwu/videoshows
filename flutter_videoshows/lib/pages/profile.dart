import 'package:flutter_videoshows/import.dart';

class Profile extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new ProfileState();
  }
}

class ProfileState extends State<Profile> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: Color(0xff171616),
        centerTitle: true,
        elevation: 0.0,
        title: Text(
          "Profile",
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
              Navigator.pop(context);
            }),
      ),
      backgroundColor: Color(0xffffffff),
      body: Column(
        children: <Widget>[
          SizedBox(
            height: 20.0,
          ),
          new InkWell(
            onTap: (){
              print("*************name");
              Navigator.push(context, new MaterialPageRoute(builder: (_)=> new ChangeName()));
            },
            child:_buildItem("NAME", "杨文武", true),
          ),
          new InkWell(
            onTap: (){
              Navigator.push(context, new MaterialPageRoute(builder: (_)=> new ChangePassword()));
            },
            child: _buildItem("CHANGE PASSWORD", "", false),
          ),

        ],
      ),
    );
  }

  Widget _buildItem(var titleString, var value, bool isVisible) {
    return new Column(
      children: <Widget>[
          new Container(
//            color: Color(0xffffffff),
            height: 45,
            child: new Row(
              children: <Widget>[
                new Expanded(
                    flex: 1,
                    child: new Padding(
                      padding: const EdgeInsets.only(left: 10.0),
                      child: Text(
                        titleString,
                        style: textStyle3,
                      ),
                    )),
                _buildViewText(isVisible, value),
                new Padding(
                  padding: const EdgeInsets.only(left: 10.0, right: 10.0),
                  child: Text(
                    ">",
                    style: textStyle2,
                  ),
                )
              ],
            ),
          ),
        new Padding(
          padding: const EdgeInsets.only(left: 10.0, right: 10.0),
          child: Divider(height: 2.0,),
        )
      ],
    );
  }

  Widget _buildViewText(bool isVisible, var value) {
    if (isVisible) {
      return Text(
        value,
        style: textStyle4,
      );
    } else {
      return Text("");
    }
  }
}
