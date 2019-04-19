import 'package:flutter_videoshows/import.dart';
import 'package:chewie/chewie.dart';
import 'package:flutter_videoshows/view/listviewcustomItem.dart';
import 'package:video_player/video_player.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';

class VideoDetail extends StatefulWidget {
  ResObject resObject;

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
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var titleWidget = Text(widget.resObject.title);
    var desWidget = Text(widget.resObject.description);
    var codeAndTime =
        Text(widget.resObject.subjectName + widget.resObject.publishTime);
    var recommendList0 = ListView(
      scrollDirection: Axis.horizontal,
      children: <Widget>[
        Text("dkjafkdjfladjfl"),
        Text("dkjafkdjfladjfl"),
        Text("dkjafkdjfladjfl"),
        Text("dkjafkdjfladjfl"),
        Text("dkjafkdjfladjfl"),
        Text("dkjafkdjfladjfl"),
        Text("dkjafkdjfladjfl"),
      ],
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

    return new Material(
//      body: new Column(
//        children: <Widget>[
////          playerWidget,
////          titleWidget,
////          desWidget,
////          codeAndTime,
////          recommendList,
//          recommendList2
//        ],
//      )

      child: new CustomScrollView(
//            shrinkWrap: true,
        slivers: <Widget>[
          new SliverPadding(
            padding: EdgeInsets.all(2),
            sliver: SliverToBoxAdapter(
              child: desWidget,
            ),
          ),
          //AppBar，包含一个导航栏
          SliverAppBar(
            pinned: true,
            expandedHeight: 250.0,
            flexibleSpace: FlexibleSpaceBar(
              title: const Text('Demo'),
              background: Image.asset(
                "./images/avatar.png",
                fit: BoxFit.cover,
              ),
            ),
          ),

          SliverPadding(
            padding: const EdgeInsets.all(8.0),
            sliver: new SliverGrid(
              //Grid
              gridDelegate: new SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3, //Grid按两列显示
                mainAxisSpacing: 10.0,
                crossAxisSpacing: 10.0,
                childAspectRatio: 4.0,
              ),
              delegate: new SliverChildBuilderDelegate(
                (BuildContext context, int index) {
                  //创建子widget
                  return new Container(
                    alignment: Alignment.center,
                    color: Colors.cyan[100 * (index % 9)],
                    child: new Text('grid item $index'),
                  );
                },
                childCount: 20,
              ),
            ),
          ),
          //List
          new SliverFixedExtentList(
            itemExtent: 50.0,
            delegate: new SliverChildBuilderDelegate(
                (BuildContext context, int index) {
              //创建列表项
              return new Container(
                alignment: Alignment.center,
                color: Colors.lightBlue[100 * (index % 9)],
                child: new Text('list item $index'),
              );
            }, childCount: 50 //50个列表项
                ),
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    videoPlayerController.dispose();
    chewieController.dispose();
    super.dispose();
  }
}
