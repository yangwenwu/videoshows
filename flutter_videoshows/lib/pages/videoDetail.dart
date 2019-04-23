import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter_videoshows/import.dart';
import 'package:chewie/chewie.dart';
import 'package:flutter_videoshows/model/publiclistviewBean.dart';
import 'package:flutter_videoshows/view/bottomComponent.dart';
import 'package:flutter_videoshows/view/listviewcustomItem.dart';
import 'package:video_player/video_player.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';

class VideoDetail extends StatefulWidget {
  Res resObject;

  VideoDetail({Key key, @required this.resObject, String title})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _VideoDetail();
  }
}

final playerWidget = Chewie(
  controller: chewieController,
);
final chewieController = ChewieController(
  videoPlayerController: videoPlayerController,
  aspectRatio: 3 / 2,
  autoPlay: true,
  looping: true,
);

final videoPlayerController = VideoPlayerController.network(
    'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4');

class _VideoDetail extends State<VideoDetail> {
  List<DateList> resList = [];

  @override
  void initState() {
    getRecommendData();
    super.initState();
  }

  getRecommendData() async {
    DataResult dataResult =
        await Api.recommendListData(widget.resObject.subjectCode);
    if (dataResult.result) {
      PublicListViewBean bean = dataResult.data;
      String json = jsonEncode(bean);
//      await SpUtils.save(SPKey.TOP, json);
      resList.clear();
      resList = bean.resObject.dateList;
    }
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    var titleWidget = Text(
      widget.resObject.title,
      style: textStyle15,
    );
    var desWidget = Text(
      widget.resObject.description,
      style: textStyle16,
    );
    var recommendTitle = Text(
      "OUR RECOMMENDATIONS",
      style: textStyle19,
    );
    var codeAndTime = Text.rich(TextSpan(children: [
      TextSpan(
          text: widget.resObject.subjectName.toUpperCase(), style: textStyle17),
      TextSpan(
          text: "    ${widget.resObject.publishTime.toUpperCase()}",
          style: textStyle18),
    ]));

    var bottomShadow = new Container(
      // grey box
      child: new Center(
        child: new Container(
          // red box
          child: new Text(
            "Lorem ipsum just come on ,go back to fish",
            style: textStyle21,
          ),
          decoration: new BoxDecoration(
              borderRadius: BorderRadius.only(
                  bottomLeft: Radius.circular(7.0),
                  bottomRight: Radius.circular(7.0))),
          padding: new EdgeInsets.only(left: 10.0, right: 10.0, bottom: 10.0),
        ),
      ),
      width: 190.0,
      height: 50.0,
//      color: Colors.grey[300],
      foregroundDecoration: BoxDecoration(
        backgroundBlendMode: BlendMode.exclusion,
        gradient: LinearGradient(
          colors: const [
            Colors.black12,
            Colors.black54,
          ],
        ),
      ),
    );

    var recommendList = ListView.custom(
      itemExtent: 10,
      scrollDirection: Axis.horizontal,
      childrenDelegate: MyChildrenDelegate((BuildContext context, int i) {
        return new Text("alflasflasfls");
      }),
    );

    var recommendList2 = ListView.builder(
        itemCount: 10,
        physics: AlwaysScrollableScrollPhysics(),
        scrollDirection: Axis.horizontal,
        itemBuilder: (BuildContext context, int i) {
          return Text("dajdjflasjfldjfl");
        });

    List<Widget> recommendWidget(List<DateList> resList) {
      List<Widget> listW = [];
      resList.forEach((f) {
        listW.add(
          new GestureDetector(
            onTap: (){
              print("点击了推荐列表");
            },
            child: new Container(
              padding: EdgeInsets.fromLTRB(10.0, 0.0, 10.0, 0.0),
              width: 200,
              height: 100,
              child: new Stack(
                children: <Widget>[
                  new Container(
                    child: new ClipRRect(
                      child: new CachedNetworkImage(
                        imageUrl: "https://www.chinadailyhk.com/${f.bigTitleImage}",
                        placeholder: (context, url) => new Image.asset(
                            "image/news_big_default.png",
                            width: 312.0,
                            height: 175.0),
                        errorWidget: (context, url, error) =>
                        new Image.asset("image/news_big_default.png"),
                      ),
                      borderRadius: BorderRadius.all(Radius.circular(7.0)),
                    ),
                  ),
                  new Positioned(
                      bottom: 1.0,
                      child: new Container( // grey box
                        child: new Center(
                          child: new Container( // red box
                            child: new Text(
                              f.title,
                              style: textStyle21,
                            ),
                            decoration: new BoxDecoration(
                                borderRadius: BorderRadius.only(bottomLeft: Radius.circular(7.0),bottomRight:Radius.circular(7.0) )
                            ),
                            padding: new EdgeInsets.only(left: 10.0,right: 10.0,bottom: 10.0),
                          ),
                        ),
                        width: 190.0,
                        height: 50.0,
//      color: Colors.grey[300],
                        foregroundDecoration: BoxDecoration(
                          backgroundBlendMode: BlendMode.exclusion,
                          gradient: LinearGradient(
                            colors: const [
                              Colors.black12,
                              Colors.black54,
                            ],
                          ),
                        ),
                      )

                  ),
//              new Align(
//                alignment: Alignment.bottomLeft,
//                child: new Text(f.title,style: textStyle21,),
//              )
                ],
              ),
            )
          )

        );
      });
      return listW;
    }

    return new Material(
        child: new Column(
      children: <Widget>[
        playerWidget,
        Expanded(
            child: new CustomScrollView(
          physics: ScrollPhysics(),
          shrinkWrap: true,
          slivers: <Widget>[
            new SliverPadding(
              padding: EdgeInsets.all(2),
              sliver: SliverToBoxAdapter(
                child: Container(
                  padding: const EdgeInsets.all(10.0),
                  child: titleWidget,
                ),
              ),
            ),
            new SliverPadding(
              padding: EdgeInsets.all(2),
              sliver: SliverToBoxAdapter(
                child: Container(
                  padding: const EdgeInsets.all(10.0),
                  child: desWidget,
                ),
              ),
            ),
            new SliverPadding(
              padding: EdgeInsets.all(2),
              sliver: SliverToBoxAdapter(
                child: Container(
                  padding: const EdgeInsets.all(10.0),
                  child: codeAndTime,
                ),
              ),
            ),
            new SliverPadding(
              padding: EdgeInsets.all(1.0),
              sliver: SliverToBoxAdapter(
                child: Container(
                  alignment: AlignmentDirectional.centerStart,
                  height: 50.0,
                  color: Color(0xfff5f5f5),
                  padding: const EdgeInsets.all(10.0),
                  child: recommendTitle,
                ),
              ),
            ),
            new SliverPadding(
              padding: EdgeInsets.all(0.0),
              sliver: SliverToBoxAdapter(
                child: SingleChildScrollView(
                  scrollDirection: Axis.horizontal,
                  child: new Row(
                    children: recommendWidget(resList),
                  ),
                ),
              ),
            ),
            SliverPadding(padding: const EdgeInsets.all(10.0))
          ],
        )),
        Column(
          children: <Widget>[
            new Divider(
              height: 1.0,
            ),
            new Container(
              alignment: AlignmentDirectional.center,
              height: 45,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: <Widget>[
                  BottomComponent(
                    imageStr: "image/detail_heart.png",
                    btnName: 'like',
                    onTap: () {},
                  ),
                  BottomComponent(
                    imageStr: "image/detail_comments.png",
                    btnName: 'comments',
                    onTap: () {},
                  ),
                  BottomComponent(
                    imageStr: "image/detail_bookmark.png",
                    btnName: 'bookmarks',
                    onTap: () {},
                  ),
                  BottomComponent(
                    imageStr: "image/detail_share.png",
                    btnName: 'share',
                    onTap: () {},
                  ),
                ],
              ),
            )
          ],
        )
      ],
    ));
  }

  @override
  void dispose() {
    videoPlayerController.dispose();
    chewieController.dispose();
    super.dispose();
  }
}
