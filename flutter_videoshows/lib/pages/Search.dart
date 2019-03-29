import 'package:cached_network_image/cached_network_image.dart';
import 'package:dio/dio.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:flutter_videoshows/model/publiclistviewBean.dart';

class Search extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SearchState();
  }
}

class _SearchState extends State<Search> {
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

  var key = "";
  var page = 1;
  var totalPage;
  List<DateList> resList = [];
  ScrollController _scrollController = new ScrollController();
  TextEditingController searchKey = new TextEditingController();
  FocusNode _contentFocusNode = FocusNode();

  @override
  void initState() {
    _scrollController.addListener(_scrollListener);
//    getData();
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

  @override
  Widget build(BuildContext context) {
    var headView = new Container(
        alignment: AlignmentDirectional.center,
      padding: const EdgeInsets.fromLTRB(0.0, 20.0, 0.0, 10.0),
      child:new RichText(
          text: new TextSpan(children: [
            new TextSpan(
                style: const TextStyle(
                    color: const Color(0xff777777),
                    fontWeight: FontWeight.w400,
                    fontFamily: "Lato",
                    fontStyle: FontStyle.normal,
                    fontSize: 15.4),
                text: "Total "),
            new TextSpan(
                style: const TextStyle(
                    color: const Color(0xff111111),
                    fontWeight: FontWeight.w700,
                    fontFamily: "Lato",
                    fontStyle: FontStyle.normal,
                    fontSize: 15.4),
                text: '${resList.length}'),
            new TextSpan(
                style: const TextStyle(
                    color: const Color(0xff777777),
                    fontWeight: FontWeight.w400,
                    fontFamily: "Lato",
                    fontStyle: FontStyle.normal,
                    fontSize: 15.4),
                text: " videos found")
          ]
          )
      )
    );


    _itemBuilder(BuildContext context, int index) {
      if (index == 0) {
//        if(resList.length == 0){
//          return new Text("");
//        }else{
//          return headView;
//        }
        return headView;
      }

      return new InkWell(
        onTap: () {
          _jumpToNativeVideo(resList[index - 1]);
        },
        child: Column(
          children: <Widget>[
            new Container(
              padding: EdgeInsets.fromLTRB(0.0, 15.0, 0.0, 15.0),
              child: new Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  new SizedBox(
                    width: 10,
                  ),
                  new Container(
//                padding: EdgeInsets.all(10.0),
                    width: 120.6,
                    height: 67.5,
                    child: new ClipRRect(
                      child: new CachedNetworkImage(
                        imageUrl:
                        "https://www.chinadailyhk.com/${resList[index - 1]
                            .bigTitleImage}",
                        placeholder: (context, url) =>
                        new Image.asset(
                            "image/news_big_default.png",
                            width: 128,
                            height: 72),
                        errorWidget: (context, url, error) =>
                        new Image.asset("image/news_big_default.png"),
                      ),
                      borderRadius: BorderRadius.all(Radius.circular(3.8)),
                    ),
                  ),
                  new SizedBox(
                    width: 5.0,
                  ),
                  new Expanded(
                    flex: 1,
                    child: new Container(
                      height: 68,
                      child: new Column(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        crossAxisAlignment: CrossAxisAlignment.end,
                        children: <Widget>[
                          new Container(
                            child: new Text(
                              resList[index - 1].title,
                              style: textStyle9,
                            ),
                            alignment: Alignment.topLeft,
                          ),
                          new Align(
                            alignment: Alignment.bottomLeft,
                            child: new Text(
                              resList[index - 1].subjectName.toUpperCase(),
                              style: textStyle10,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
            new Divider(
              height: 2.0,
            )
          ],
        ),
      );
    }

    Widget buildTextField() {
      //theme设置局部主题
      return TextField(
        controller: searchKey,
        textInputAction: TextInputAction.search,
        focusNode: _contentFocusNode,
        autofocus: true,
        maxLines: 1,
        cursorColor: Colors.white,
        //设置光标
        onSubmitted: (_) {
          resList.clear();
          printStr("*****onSubmit***${searchKey.text}");
          _contentFocusNode.unfocus();
          key = searchKey.text;
          getData();
        },
        onChanged: (_) =>{
        printStr("******onChanged****${searchKey.text}")
        },
        onEditingComplete: () =>{
        printStr("*****onEditingComplete****${searchKey.text}")
        },
        decoration: InputDecoration(
          //输入框decoration属性
//            contentPadding: const EdgeInsets.symmetric(vertical: 1.0,horizontal: 1.0),
            contentPadding: new EdgeInsets.only(left: 0.0),
//            fillColor: Colors.white,
            border: InputBorder.none,
//            icon: Icon(Icons.search),
//            icon: ImageIcon(AssetImage("image/search_meeting_icon.png",),),
            icon: ImageIcon(
              AssetImage(
                "image/search.png",
              ),
            ),
            hintText: "Video name",
            hintStyle: new TextStyle(fontSize: 14, color: Colors.white)),
        style: new TextStyle(fontSize: 14, color: Colors.white),
      );
    }

    Widget editView() {
      return Container(
        //修饰黑色背景与圆角
        decoration: new BoxDecoration(
          border: Border.all(color: Colors.grey, width: 1.0), //灰色的一层边框
          color: Colors.grey,
          borderRadius: new BorderRadius.all(new Radius.circular(15.0)),
        ),
        alignment: Alignment.center,
        height: 36,
        padding: EdgeInsets.fromLTRB(10.0, 0.0, 0.0, 0.0),
        child: buildTextField(),
      );
    }

    var cancelView = new Text(
      "cancel",
      style: textStyle11,
    );

    Widget buildSearch() {
      return Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Expanded(
            child: editView(),
            flex: 1,
          ),
          Padding(
            padding: const EdgeInsets.only(
              left: 5.0,
            ),
            child: GestureDetector(
              onTap: () {
//              goBack();
                Navigator.pop(context);
              },
              child: cancelView,
            ),
          )
        ],
      );
    }

    return new Scaffold(
      appBar: new AppBar(
//      centerTitle: true,
        backgroundColor: Colors.black,
        title: buildSearch(),
        automaticallyImplyLeading: false,
//      actions: <Widget>[
//        new Center(
//          child: Padding(
//            padding: const EdgeInsets.all(8.0),
//            child: Text(
//              "Cancel",
//            ),
//          ),
//        )
//      ],
      ),
      body: new RefreshIndicator(
          child: new ListView.builder(
            physics: AlwaysScrollableScrollPhysics(),
            itemBuilder: _itemBuilder,
            itemCount: resList.length + 1,
            controller: _scrollController,
          ),
          onRefresh: refresh),
    );
  }

  Future refresh() async {
    page = 1;
    getData();
  }

  Future getData() async {
//    model.urlEnd = "selectNewsList?dataType=3"+"&currentPage="+page + "&title="+ title
    /// https://api.cdeclips.com/hknews-api/selectNewsList?title=HK&currentPage=1&dataType=3
    try {
      Response response = await Dio().get(
          "${Constant
              .STATICURL}selectNewsList?title=$key&currentPage=$page&dataType=3");
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
}

void goBack() {
  print("*******点击了cancel");
}

void printStr(var str) {
  print(str);
}
