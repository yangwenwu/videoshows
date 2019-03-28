import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_videoshows/import.dart';

class Category extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _CategoryState();
  }
}

class _CategoryState extends State<Category> {
  String dataStr = "";
  var _items = [];
  ScrollController _scrollController = ScrollController();

  @override
  Widget build(BuildContext context) {
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
      body: new RefreshIndicator(child: contentWidget(), onRefresh: _pullRefresh),
    );
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

  Future _pullRefresh() async {
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
                  item["code"],
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
            } else {
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
//        showCustomDialog(context);

        Navigator.of(context).push(new MaterialPageRoute(builder: (_) {
          return new PublicList(
            modelbean: model,
          );
        })).then((value) {});
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
            style: textStyle8,
          ),
        ],
      ),
    );
  }

  void getData() {
    HttpController.get(Constant.http_category, (data) {
      if (data != null) {
//        final body = JSON.decode(data.toString());
        final body = jsonDecode(data.toString());
        final feeds = body["resObject"];
        var items = [];
        feeds.forEach((item) {
          items.add(Model(
              item["id"],
              item["code"],
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
        _scrollController.position.maxScrollExtent) {
      // 滑动到最底部了
//      requestData(false);
    }
  }
}

//id	"0838b4ec-d16c-4449-9e72-45f1cb0992da"
//code	"seeing_sight"
//name	"Seeing Sights"
//focusUrl	""
//jsonUrl	"/subjects/seeing_sight/list_Vindex/2890/index.json"
//parentCode	"video"
//imageUrl	"https://api.cdeclips.com/ui/videoshow/seeing_sight.jpg"

class Model {
  String id;
  String code;
  String name;
  String focusUrl;
  String jsonUrl;
  String imageUrl;
  String parentCode;
  String des;

  Model(this.id, this.code, this.name, this.imageUrl, this.parentCode,
      this.focusUrl, this.jsonUrl, this.des);
}
