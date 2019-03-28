import 'package:cached_network_image/cached_network_image.dart';
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
  static const jumpVideoPlugin =
      const MethodChannel('com.lemon.jump.video/plugin');

  Future<Null> _jumpToNativeVideo(DateList dateList) async {
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
          onTap: () {
            _jumpToNativeVideo(resList[index]);
          },
          child: Card(
            margin: const EdgeInsets.only(
                left: 15.0, top: 10.0, right: 15.0, bottom: 10.0),
            shape: const RoundedRectangleBorder(
                borderRadius: BorderRadius.all(Radius.circular(7.0))),
            elevation: 4.0,
            child: Column(
              children: <Widget>[

                    new Container(

                      child:  new Stack(
                        alignment: Alignment.center,
                        children: <Widget>[
                          new ClipRRect(
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

                          new Center(
                            child: Image.asset("image/videoplay_icon.png",width: 67,height: 67,),
                          )

                        ],
                      ),

                    ),
//                    new Center(
//                      heightFactor: 2.0,
//                      child:Image.asset("image/videoplay_icon.png",width: 50,height: 50,) ,
//                    )


                new Align(
                  alignment: Alignment.centerLeft,
                  child: new Padding(
                    padding: const EdgeInsets.fromLTRB(15.0, 30.0, 10.0, 20.0),
                    child: new Text(
                      "${resList[index].title}",
                      style: textStyle6,
                    ),
                  ),
                ),
              ],
            ),
          ));
    }

    return new Scaffold(
      appBar: new AppBar(
          backgroundColor: Colors.black,
          centerTitle: true,
          title: Text(widget.modelbean.name),
          leading: new IconButton(
              icon: new Image.asset(
                "image/back_arrow3.png",
                height: 25,
                width: 25,
              ),
              onPressed: () {
                Navigator.pop(context);
              })),
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

}
