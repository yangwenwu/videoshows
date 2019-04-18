import 'dart:convert';
import 'dart:core';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter_videoshows/import.dart';

class Category extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CategoryState();
  }
}

class _CategoryState extends State<Category> {
  bool loadFail = false;
  String dataStr = "";
  var resList = [];
  ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    _scrollController.addListener(_handleScroll);
    getCategoryData();
    super.initState();
  }


  var centerView = new Column(
    mainAxisAlignment: MainAxisAlignment.center,
    children: <Widget>[new Text("SHOWS")],
  );

  var rightView = new Image.asset(
    "image/search.png",
    width: 20,
    height: 20,
  );

  @override
  Widget build(BuildContext context) {

    var _gridView = new GridView.count(
      physics: AlwaysScrollableScrollPhysics(),
      controller: _scrollController,
      crossAxisCount: 2,
      childAspectRatio: 1.27,
      children: buildWidget(),
    );

    var content;

    if (resList.isEmpty) {
//      content = new Center(child: new CircularProgressIndicator());
      if (loadFail) {
        //加载失败
        content = new Center(
          child: new RaisedButton(
            onPressed: () {
              loadFail = false;
              getCategoryData();
              setState(() {});
            },
            child: new Text("点击重新加载"),
          ),
        );
      } else {
        content = loading;
      }
    } else {
      content = _gridView;
    }

    var _refreshIndicator = new RefreshIndicator(
      child: content,
      onRefresh: _pullRefresh,
    );

    return new Scaffold(
        appBar: new AppBar(
          centerTitle: true,
          title: new Text("SHOWS"),
          actions: <Widget>[
            new IconButton(
                icon: ImageIcon(AssetImage("image/search.png")),
                onPressed: () {
                  Navigator.push(
                    context,
                    new MaterialPageRoute(builder: (context) => new Search()),
                  );
//                Navigator.pushReplacementNamed(context,"/search");
//                Navigator.pushNamedAndRemoveUntil(context,"/search",(_)=>false);
//              Navigator.pushAndRemoveUntil(context, new MaterialPageRoute(builder: (context) => new Search()), (_)=>false);
                })
          ],
          backgroundColor: Colors.black,
        ),
        body: _refreshIndicator
        );
  }

  Future _pullRefresh() async {
    getCategoryData();
  }

  getCategoryData() async {
    DataResult dataResult = await Api.categoryListData();
    if (dataResult.result) {
      CategoryBean bean = dataResult.data;
      String json = jsonEncode(bean);
      await SpUtils.save(SPKey.CATEGORY, json);
      resList.clear();
      resList = bean.resObject;
    } else {
      String top = await SpUtils.get(SPKey.CATEGORY);
      if (top != null && top.isNotEmpty) {
        Map map = jsonDecode(top);
        CategoryBean bean = CategoryBean.fromJson(map);
        resList.clear();
        resList = bean.resObject;
      } else {
        loadFail = true;
      }
    }
    setState(() {});
  }

  List<Widget> buildWidget() {
    List<Widget> widgetList = new List();
    for (int i = 0; i < resList.length; i++) {
      widgetList.add(getItemWidget(context, i));
    }
    return widgetList;
  }

  Widget getItemWidget(BuildContext context, int index) {
    CateResObject model = resList[index];
    //BoxFit 可设置展示图片时 的填充方式
    return new GestureDetector(
      onTap: () {
        Navigator.of(context).push(new MaterialPageRoute(builder: (_) {
          return new CategoryListPage(
            modelbean: model,
          );
        })).then((value) {});
      },
      child: Stack(
        alignment: Alignment.center,
        children: <Widget>[
          CachedNetworkImage(
            imageUrl: model.imageUrl,
            placeholder: (context, url) => new Image.asset(
                  "image/news_big_default.png",
//                width: MediaQuery.of(context).size.width - 30,
//                height: (MediaQuery.of(context).size.width - 30) *9 /16
                  fit: BoxFit.cover,
                ),
            errorWidget: (context, url, error) =>
                new Image.asset("image/news_big_default.png"),
          ),
          Text(
            model.name,
            style: textStyle8,
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    _scrollController?.removeListener(_handleScroll);
    super.dispose();
  }

  void _handleScroll() {
    if (_scrollController.position.pixels ==
        _scrollController.position.maxScrollExtent) {
      // 滑动到最底部了
    }
  }
}
