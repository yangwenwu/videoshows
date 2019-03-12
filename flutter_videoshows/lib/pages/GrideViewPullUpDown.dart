import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:flutter_videoshows/import.dart';
class GrideViewPullUpDown extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _GridViewList();
  }
}

class _GridViewList extends State<GrideViewPullUpDown> {
  String dataStr = "";
  var _items = [];
  ScrollController _scrollController = ScrollController();

  @override
  Widget build(BuildContext context) {
    return new RefreshIndicator(child: contentWidget(), onRefresh: _pullRefrsh);
  }

//创建 内容页的GridView
  Widget contentWidget() {
    return new GridView.count(
      physics: AlwaysScrollableScrollPhysics(),
      controller: _scrollController,
      crossAxisCount: 2,
      childAspectRatio: 1.27,
      children: buildWidget(_items.length),
    );
  }

  Future _pullRefrsh() async {
    requestData(true);
}

  Future requestData(bool isRefresh) async {
    HttpController.get(
        Constant.http_category,
            (data) {
          if (data != null) {
            final body = jsonDecode(data.toString());
            final feeds = body["resObject"];
            var items = [];
            feeds.forEach((item) {
              items.add(Model(
                  item["id"],
                  item["name"],
                  item["imageUrl"],
                  item["des"],
                  item["parentCode"],
                  item["focusUrl"],
                  item["jsonUrl"]));
            });

            if (isRefresh) {
              _items.clear();
              _items = items;
            }else{
              _items.addAll(items);
            }

            setState(() {});
          }
        },
        params: null,
        errorCallback: () {
          try {} catch (exception) {
            print("请求失败$exception");
          }
        });
  }

  List<Widget> buildWidget(int number) {
    List<Widget> widgetList = new List();
    for (int i = 0; i < number; i++) {
      widgetList.add(getItemWidget(context, i));
    }
    return widgetList;
  }

  Widget getItemWidget(BuildContext context, int index) {
    Model model = _items[index];
    //BoxFit 可设置展示图片时 的填充方式
    return new GestureDetector(
      onTap: () {
        showCustomDialog(context);
      },
      child: Stack(
        alignment: Alignment.center,
        children: <Widget>[
          Image.network(
            model.imageUrl,
            fit: BoxFit.cover,
          ),
          Text(
            model.name,
            style: new TextStyle(
                fontFamily: "Bold", color: Colors.white, fontSize: 18.0),
          ),
        ],
      ),
    );
  }

  void getData() {
    HttpController.get(
        "http://210.5.58.206/hknews-api/selectSubjectList?parentCode=video",
            (data) {
          if (data != null) {
//        final body = JSON.decode(data.toString());
            final body = jsonDecode(data.toString());
            final feeds = body["resObject"];
            var items = [];
            feeds.forEach((item) {
              items.add(Model(
                  item["id"],
                  item["name"],
                  item["imageUrl"],
                  item["des"],
                  item["parentCode"],
                  item["focusUrl"],
                  item["jsonUrl"]));
            });
            setState(() {
              dataStr = data.toString();
              _items = items;
            });
          }
        }, params: null);
  }

  void showCustomDialog(BuildContext context) {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return new AlertDialog(
            title: new Text("点击提示"),
            content: new SingleChildScrollView(
                child: new ListBody(children: <Widget>[new Text("你点击了Item")])),
            actions: <Widget>[
              new FlatButton(
                child: new Text("取消"),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
              new FlatButton(
                child: new Text("确认"),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        });
  }

  @override
  void initState() {
    _scrollController.addListener(_handleScroll);
//  _scrollController..addListener((){
//
//  });
    requestData(true);
    super.initState();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
  }

  @override
  void dispose() {
    _scrollController?.removeListener(_handleScroll);
    super.dispose();
  }

  void _handleScroll() {

    if (_scrollController.position.pixels ==
        _scrollController.position.maxScrollExtent ) {
      // 滑动到最底部了
      requestData(false);
    }
  }

}

class Model {
  String id;
  String name;
  String imageUrl;
  String parentCode;
  String focusUrl;
  String jsonUrl;
  String des;

  Model(this.id, this.name, this.imageUrl, this.parentCode, this.focusUrl,
      this.jsonUrl, this.des);
}
