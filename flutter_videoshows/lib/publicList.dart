import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:flutter_videoshows/model/publiclistviewBean.dart';
import 'package:flutter_videoshows/pages/category.dart';

class PublicList extends StatefulWidget {
  Model modelbean;

  PublicList({Key key, this.modelbean}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _publicListState();
  }
}

class _publicListState extends State<PublicList> {
  //获取到插件与原生的交互通道
  static const jumpVideoPlugin = const MethodChannel('com.lemon.jump.video/plugin');

  Future<Null> _jumpToNativeVideo(DateList dateList) async {
    Map<String, String> map = {  };
    map.putIfAbsent("title", ()=>dateList.title);
    map.putIfAbsent("bigTitleImage", ()=>dateList.bigTitleImage);
    map.putIfAbsent("subjectCode", ()=>dateList.subjectCode);
    map.putIfAbsent("titleImage", ()=>dateList.titleImage);
    map.putIfAbsent("dataId", ()=>dateList.dataId);
    map.putIfAbsent("jsonUrl", ()=>dateList.jsonUrl);
    map.putIfAbsent("description", ()=>dateList.description);
    String result = await jumpVideoPlugin.invokeMethod('VideoDetail',map);
//    String result = await jumpVideoPlugin.invokeMethod('VideoDetail',dateList);

    print(result);
  }

  var code;

  var page = 1;
  var totalPage;

  List<DateList> resList = [];
  ScrollController _scrollController = new ScrollController();

  @override
  void initState() {
    code = widget.modelbean.code;
    _scrollController.addListener(_scrollListener);
    getData();
    super.initState();
  }

  _scrollListener() async {
    if (_scrollController.position.pixels ==
        _scrollController.position.maxScrollExtent) {
//      if (resList.length > 0) {
//        resList.clear();
//      }
//      page++;
      if (totalPage >= page) {
        getData();
      }
      setState(() {});
    }
  }

  Future refresh() async {
    page = 1;
    getData();
  }

//  https://api.cdeclips.com/hknews-api/selectNewsList?subjectCode=movie_corner&currentPage=1&dataType=3
  Future getData() async {
    try {
      Response response = await Dio().get(
          "${Constant.STATICURL}selectNewsList?subjectCode=$code&currentPage=$page&dataType=3");
      print(response);
      print(response.data);
      if (response.data != null) {
        Map map = response.data;
        PubliclistviewBean bean = new PubliclistviewBean.fromJson(map);
        if (page == 1) {
          resList.clear();
          resList = bean.resObject.dateList;
          totalPage = bean.resObject.totalPage;
        } else {
          if (bean.resObject.dateList.length > 0) {
            resList.addAll(bean.resObject.dateList);
          }
        }
        page++;
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

  @override
  Widget build(BuildContext context) {
    _itemBuilder(BuildContext context, int index) {
      return new GestureDetector(
        onTap: (){
          print("****************************点击跳转");
          _jumpToNativeVideo(resList[index]);
        },
          child: Card(
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
                    padding: const EdgeInsets.only(bottom: 20.0),
                    child: new Image.network(
                      "https://www.chinadailyhk.com/${resList[index].bigTitleImage}",
                      fit: BoxFit.cover,
                    )

//          new CachedNetworkImage(
//          placeholder: CircularProgressIndicator(),
//      imageUrl: 'https://github.com/flutter/website/blob/master/_includes/code/layout/lakes/images/lake.jpg?raw=true',
//      ),

                    ),
                new Row(
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.only(left: 20),
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
                            )),
                      ),
                    )
                  ],
                ),
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
      ));
    }

    return new Scaffold(
      appBar: new AppBar(
        centerTitle: true,
        title: navigateView(),
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

  Widget navigateView() {
    return new Stack(
      children: <Widget>[
//      new Row(
//          children: <Widget>[
//            Image.asset("image/back_arrow3.png",width: 30,height: 30,),
//          ],
//        ),
        new Row(
//          mainAxisAlignment: MainAxisAlignment.center,
//          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[Text(widget.modelbean.name)],
        )
      ],
    );
  }
}
