import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:flutter/services.dart';
import 'package:flutter_videoshows/http/api.dart';
import 'package:flutter_videoshows/http/dataResult.dart';
import 'package:flutter_videoshows/http/httpRequest.dart';
import 'package:flutter_videoshows/http/resultData.dart';
import 'package:flutter_videoshows/http/spUtils.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:path/path.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:shared_preferences/shared_preferences.dart';

class TobTab extends StatefulWidget {
  @override
  _TobTabState createState() => _TobTabState();
}

class _TobTabState extends State<TobTab> {
  bool loadFail = false;
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
    super.initState();
//    getYY();
    getTop();
    _scrollController.addListener(_scrollListener);
//    getData();
  }

  getTop() async{
    DataResult dataResult = await Api.topListData();
    print("************* dataResult **** ${dataResult.result}");
    HomeNewsBean bean = dataResult.data;
    print("***************  bean....${bean.resObject}");
    print("***************  bean to json....${bean.toJson()}");//将bean转化成一个map

    HomeNewsBean bean2 = HomeNewsBean.fromJson(bean.toJson()) ; //从一个map里面构造一个实例bean
    print("************** bean2 resMsg  *****${bean2.resMsg}");
    String json = jsonEncode(bean2);
    await SpUtils.save("toptop", json);
    String toptop =  await SpUtils.get("toptop") as String;
    print("*********    toptop ********   $toptop");
//    String profile = jsonEncode(toptop);
//    print("************** profile ****  $profile");
    Map mm = jsonDecode(toptop);
    print("************** mm ****  $mm");

    resList.clear();
    resList = bean.resObject;
    setState(() {});
  }

  getYY() async {
    ResultData resultData = await HttpRequest.get("selectVideoHome", null);
    if (resultData != null && resultData.result) {
      HomeNewsBean bean = HomeNewsBean.fromJson(resultData.data);
//      SpUtils.save("top", bean.toJson().toString());   //这样子保存数据，后面获取数据的时候会报错
      SpUtils.save("top", json.encode(resultData.data));
      print(bean.toJson().toString());
      resList.clear();
      resList = bean.resObject;
    } else {
      String topStr = await SpUtils.get("top") as String;
      print("************************* topStr == $topStr");
      if (topStr != null && topStr.isNotEmpty) {
        var profile = jsonDecode(topStr);
        HomeNewsBean bean = HomeNewsBean.fromJson(profile);
        print("************************* profile == $profile");
        resList.clear();
        resList = bean.resObject;
      } else {
        loadFail = true;
      }
    }
    setState(() {});
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
                      child: new ClipRRect(
                        child: new CachedNetworkImage(
                          imageUrl:
                              "https://www.chinadailyhk.com/${resList[index].bigTitleImage}",
                          placeholder: (context, url) => new Image.asset(
                              "image/news_big_default.png",
                              width: MediaQuery.of(context).size.width - 30,
                              height: (MediaQuery.of(context).size.width - 30) *
                                  9 /
                                  16),
                          errorWidget: (context, url, error) =>
                              new Image.asset("image/news_big_default.png"),
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
                      top: ((MediaQuery.of(context).size.width - 30) * 9 / 16 -
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
                                    "${resList[index].subjectName.toUpperCase()}",
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

    var content;

    if (resList.isEmpty) {
//      content = new Center(child: new CircularProgressIndicator());
      if (loadFail) {
        //加载失败
        content = new Center(
          child: new RaisedButton(
            onPressed: () {
              loadFail = false;
              getYY();
              setState(() {});
            },
            child: new Text("点击重新加载"),
          ),
        );
      } else {
        content = new Stack(
          children: <Widget>[
            new Padding(
              padding: new EdgeInsets.fromLTRB(0.0, 0.0, 0.0, 35.0),
              child: new Center(
                child: SpinKitFadingCircle(
                  color: Colors.blueAccent,
                  size: 30.0,
                ),
              ),
            ),
            new Padding(
              padding: new EdgeInsets.fromLTRB(0.0, 35.0, 0.0, 0.0),
              child: new Center(
                child: new Text('正在加载中，莫着急哦~'),
              ),
            ),
          ],
        );
      }
    } else {
      content = new ListView.builder(
        //设置physics属性总是可滚动
        physics: AlwaysScrollableScrollPhysics(),
        controller: _scrollController,
        itemCount: resList.length,
        itemBuilder: _itemBuilder,
      );
    }

    var _refreshIndicator = new RefreshIndicator(
//      key: _refreshIndicatorKey,
      onRefresh: refresh,
      child: content,
    );

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
      body: _refreshIndicator,
    );
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
