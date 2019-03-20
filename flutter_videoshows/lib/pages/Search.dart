import 'package:flutter_videoshows/import.dart';

class Search extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _SearchState();
  }
}

class _SearchState extends State<Search> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
        appBar: new AppBar(
      centerTitle: true,
      backgroundColor: Colors.black,
      title: Text("标题"),
      leading: Offstage(
        offstage: true,
        child:
          new IconButton(
            tooltip: 'Previous choice',
            icon: const Icon(Icons.arrow_back),
            onPressed: () {},
          )
      )
      ,
      actions: <Widget>[
//        new IconButton(
//          icon: const Icon(Icons.arrow_forward),
//          tooltip: 'Next choice',
//          onPressed: () {},
//        ),
        new Center(
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              "Cancel",
            ),
          ),
        )
      ],
    ));
  }
}
