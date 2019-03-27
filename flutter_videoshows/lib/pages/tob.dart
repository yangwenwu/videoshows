import 'dart:async';

import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:flutter/services.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:cached_network_image/cached_network_image.dart';

class TobTab extends StatefulWidget {
  @override
  _TobTabState createState() => _TobTabState();
}

class _TobTabState extends State<TobTab> {
  static const jumpVideoPlugin =
  const MethodChannel('com.lemon.jump.video/plugin');

  Future<Null> _jumpToNativeVideo(ResObject dateList) async {
    Map<String, String> map = {};
    map.putIfAbsent("title", () => dateList.title);
    map.putIfAbsent("bigTitleImage", () => dateList.bigTitleImage);
    map.putIfAbsent("subjectCode", () => dateList.subjectCode);
    map.putIfAbsent("titleImage", () => dateList.titleImage);
    map.putIfAbsent("dataId", () => dateList.dataId);
    map.putIfAbsent("jsonUrl", () => dateList.jsonUrl);
    map.putIfAbsent("description", () => dateList.description);
    String result = await jumpVideoPlugin.invokeMethod('VideoDetail', map);
//    String result = await jumpVideoPlugin.invokeMethod('VideoDetail',dateList);

    print(result);
  }

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
      return new GestureDetector(
          onTap: () {
            _jumpToNativeVideo(resList[index]);
          },
          child: Card(
            margin: const EdgeInsets.only(
                left: 15.0, top: 10.0, right: 15.0, bottom: 10.0),
            shape: const RoundedRectangleBorder(
                borderRadius: BorderRadius.all(Radius.circular(7.0))),
            elevation: 4.0,
            child: new Stack(
              children: <Widget>[
                Column(
                  children: <Widget>[
                    new Container(
//                padding: const EdgeInsets.all(10.0),
                      child: new ClipRRect(
//                        child: new Image.network(
//                          "https://www.chinadailyhk.com/${resList[index].bigTitleImage}",
//                          fit: BoxFit.cover,
//                          width: MediaQuery.of(context).size.width - 30,
//                          height:
//                              (MediaQuery.of(context).size.width - 30) * 9 / 16,
//                        ),

//                        child: new FadeInImage.memoryNetwork(
//                            placeholder: new Image.asset(""), image: null),
                        child:
                        new CachedNetworkImage(
                          imageUrl: "https://www.chinadailyhk.com/${resList[index].bigTitleImage}",
                          placeholder: (context, url) =>new Image.asset(
                            "image/news_big_default.png", width: MediaQuery
                              .of(context)
                              .size
                              .width - 30, height:(MediaQuery.of(context).size.width - 30) * 9 / 16),

//                placeholder: (context, url) => new CircularProgressIndicator(),
                          errorWidget: (context, url, error) =>new Image.asset("image/news_big_default.png"),
                        ),


                        borderRadius: BorderRadius.only(
                            topLeft: Radius.circular(7.0),
                            topRight: Radius.circular(7.0)),
                      ),
                    ),
                    new Align(
                      alignment: Alignment.centerLeft,
                      child: new Padding(
                        padding:
                        const EdgeInsets.fromLTRB(15.0, 30.0, 10.0, 20.0),
                        child: new Text(
                          "${resList[index].title}",
                          style: textStyle6,
                        ),
                      ),
                    ),
                  ],
                ),
                new Padding(
                  padding: EdgeInsets.only(
                      top: ((MediaQuery
                          .of(context)
                          .size
                          .width - 30) * 9 / 16 -
                          25)),
                  child: new Row(children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.only(left: 20),
                      child: Material(
                        color: Colors.black,
                        borderRadius: BorderRadius.circular(5.0),
//                shadowColor: Colors.blue.shade200,
                        elevation: 5.0,
                        child: new Container(
                            padding: const EdgeInsets.all(8.0),
                            child: new Row(
                              children: <Widget>[
                                new Text(
                                    "${resList[index].subjectName
                                        .toUpperCase()}",
                                    style: textStyle7),
                              ],
                            )),
                      ),
                    ),
                    Expanded(
                      child: new Text(""),
                      flex: 1,
                    ),
                    new Padding(
                      padding: const EdgeInsets.only(right: 20),
                      child: new Image.asset(
                        "image/video_item_play.png",
                        width: 50,
                        height: 50,
                      ),
                    )
                  ]),
                ),
              ],
            ),
          ));
    }

    return new Scaffold(
      appBar: new AppBar(
        centerTitle: true,
        title: new Text("LATEST"),
        actions: <Widget>[
          new IconButton(
              icon: ImageIcon(AssetImage("image/search.png")),
              onPressed: () {
                Navigator.push(
                  context,
                  new MaterialPageRoute(builder: (context) => new Search()),
                );
              })
        ],
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

  Widget _buildStackView() {
    return new Row(children: <Widget>[
      Padding(
        padding: const EdgeInsets.only(left: 20),
        child: Material(
          color: Colors.black,
          borderRadius: BorderRadius.circular(5.0),
//                shadowColor: Colors.blue.shade200,
          elevation: 5.0,
          child: new Container(
              padding: const EdgeInsets.all(8.0),
              child: new Row(
                children: <Widget>[
                  new Text(
                    "chinadaily",
                    style: TextStyle(
                      color: Colors.white,
                      fontSize: 10,
                    ),
                  ),
                ],
              )),
        ),
      ),
      Expanded(child: new Text("")),
      new Positioned(
          right: 30,
          child: Padding(
            padding: const EdgeInsets.only(top: 30),
            child: new Image.asset(
              "image/video_item_play.png",
              width: 50,
              height: 50,
            ),
          )),
    ]);
  }

  Future refresh() async {
    getData();
  }

  Future getData() async {
    try {
      Response response = await Dio()
          .get("https://api.cdeclips.com/hknews-api/selectVideoHome");
      print(response);
      print(response.data);
      if (response.data != null) {
        Map map = response.data;
        HomeNewsBean bean = new HomeNewsBean.fromJson(map);
        resList.clear();
        resList = bean.resObject;
        setState(() {});
      }
    } catch (e) {
      print(e);
    }
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }
}
