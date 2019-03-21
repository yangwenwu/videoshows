import 'package:dio/dio.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';

class Search extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new _SearchState();
  }
}

class _SearchState extends State<Search> {
  static const jumpVideoPlugin = const MethodChannel('com.lemon.jump.video/plugin');

  Future<Null> _jumpToNativeVideo(ResObject dateList) async {
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

  var key = "";
  var page = 1;

  List<ResObject> resList = [];
  ScrollController _scrollController = new ScrollController();
  TextEditingController searchKey = new TextEditingController();
  FocusNode _contentFocusNode = FocusNode();

  @override
  void initState() {
//    _scrollController.addListener(_scrollListener);
//    getData();
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
          onTap: (){
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
                        child:
                        new Image.network(
                          "https://www.chinadailyhk.com/${resList[index].bigTitleImage}",
                          fit: BoxFit.cover,
                        )

//          new CachedNetworkImage(
//          placeholder: CircularProgressIndicator(),
//      imageUrl: 'https://github.com/flutter/website/blob/master/_includes/code/layout/lakes/images/lake.jpg?raw=true',
//      ),

                    )
                    ,
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
                        child: Padding(padding: const EdgeInsets.only(top: 30),child: new Image.asset(
                          "image/video_item_play.png",
                          width: 50,
                          height: 50,
                        ),)
                    ),

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
          )
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
        cursorColor: Colors.white, //设置光标
        onSubmitted: (_){
          printStr("*****onSubmit***${searchKey.text}");
          _contentFocusNode.unfocus();
        },
        onChanged: (_)=>{
        printStr( "******onChanged****${searchKey.text}")
        },
        onEditingComplete: ()=>{
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

    var cancelView = new Text("cancel");

    Widget buildSearch(){
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
//    model.urlEnd = "selectNewsList?dataType=3"+"&currentPage="+page + "&title="+ title
    try {
      Response response = await Dio().get("${Constant.STATICURL}selectNewsList?title=$key&currentPage=$page&dataType=3");
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

///搜索控件widget
class TextFileWidget extends StatelessWidget {


  Widget buildTextField() {
    //theme设置局部主题
    return TextField(
      cursorColor: Colors.white, //设置光标
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

  @override
  Widget build(BuildContext context) {
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

    var cancelView = new Text("cancel");

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
}

void goBack() {
  print("*******点击了cancel");

}

void printStr(var str){
  print(str);
}
