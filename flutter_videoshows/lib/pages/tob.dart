import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';

class TobTab extends StatefulWidget {
  @override
  _TobTabState createState() => _TobTabState();
}

class _TobTabState extends State<TobTab> {
  List<ResObject> resList = [];
  ScrollController _scrollController = new ScrollController();

  @override
  void initState() {
    _scrollController.addListener(_scrollListener);
    getData();
    super.initState();
  }

  _scrollListener() async {
//    if (_scrollController.position.pixels ==
//        _scrollController.position.maxScrollExtent) {
//      if (resList.length > 0) {
//        resList.clear();
//      }
//      setState(() {});
//    }
  }

  @override
  Widget build(BuildContext context) {
    _itemBuilder(BuildContext context, int index) {
      return Card(
        margin: const EdgeInsets.only(
            left: 15.0, top: 10.0, right: 15.0, bottom: 10.0),
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(7.0))),
        elevation: 4.0,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            new Stack(
              alignment: AlignmentDirectional.bottomStart,
              children: <Widget>[
                new Padding(
                    padding: const EdgeInsets.only(bottom: 13.0),
                    child: new Image.network(
                      "https://www.chinadailyhk.com/${resList[index].bigTitleImage}",
                      fit: BoxFit.cover,
                    )),
                new  Row(
                  children: <Widget>[
                    Padding(padding: const EdgeInsets.only(left: 20),
                      child: Material(
                        color: Colors.black,
                        borderRadius: BorderRadius.circular(5.0),
//                shadowColor: Colors.blue.shade200,
                        elevation: 5.0,
                        child: new Container(
                            padding: const EdgeInsets.all(8.0),
//                          margin: const EdgeInsets.only(left: 20),
                            child: new Row(
                              children: <Widget>[
                                new Text(
                                  "chinadaily",
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 10,
//                              fontWeight: FontWeight.bold,
                                  ),
                                ),

                              ],
                            )
                        ),
                      ),)
                  ],
                ),
                new Image.asset("image/video_item_play.png",width: 50,height: 50,),

//                Container(
//                  color: Colors.black,
//                  margin: EdgeInsets.only(left: 10,),
//                  padding: const EdgeInsets.only(left: 10,top: 5,right: 10,bottom: 5),
//                  child: new Text(
//                    "chinadaily",
//                    style: TextStyle(
//                      color: Colors.white,
//                      fontSize: 20,
//                      fontWeight: FontWeight.bold,
//                    ),
//                  ),
//                ),

              ],
            ),
            const ListTile(
              leading: Icon(Icons.album),
              title: Text('The Enchanted Nightingale'),
              subtitle: Text('Music by Julie Gable. Lyrics by Sidney Stein.'),
            ),
            ButtonTheme.bar(
              // make buttons use the appropriate styles for cards
              child: ButtonBar(
                children: <Widget>[
                  FlatButton(
                    child: const Text('BUY TICKETS'),
                    onPressed: () {
                      /* ... */
                    },
                  ),
                  FlatButton(
                    child: const Text('LISTEN'),
                    onPressed: () {
                      /* ... */
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      );
    }

    return new Scaffold(
      appBar: new AppBar(
        centerTitle: true,
        title: new Text('SHOW'),
        elevation: 0.0,
        backgroundColor: Colors.black,
      ),
      body: new RefreshIndicator(
          child: new ListView.builder(
            physics: AlwaysScrollableScrollPhysics(),
            itemBuilder: _itemBuilder,
            itemCount: resList.length,
            controller: _scrollController,
          ),
          onRefresh: refresh),
    );
  }

  Future refresh() async {
    getData();
  }

  Future getData() async {
    Dio dio = new Dio();
    Response response = await dio
        .get("https://api.cdeclips.com/hknews-api/selectVideoHome",
            cancelToken: CancelToken())
        .catchError((DioError onError) {
      if (!CancelToken.isCancel(onError)) {
        print(onError.message);
      }
    });
    print(response.data);
    if (response.data != null) {
      Map map = response.data;
      HomeNewsBean bean = new HomeNewsBean.fromJson(map);
      resList.clear();
      resList = bean.resObject;
      setState(() {});
    }
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }
}
