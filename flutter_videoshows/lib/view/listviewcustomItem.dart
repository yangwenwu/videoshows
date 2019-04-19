import '../import.dart';

/**
 * 继承SliverChildBuilderDelegate  可以对列表的监听
 */
class MyChildrenDelegate extends SliverChildBuilderDelegate {
  MyChildrenDelegate(
      Widget Function(BuildContext, int) builder, {
        int childCount,
        bool addAutomaticKeepAlive = true,
        bool addRepaintBoundaries = true,
      }) : super(builder,
      childCount: childCount,
      addAutomaticKeepAlives: addAutomaticKeepAlive,
      addRepaintBoundaries: addRepaintBoundaries);

  ///监听 在可见的列表中 显示的第一个位置和最后一个位置
  @override
  void didFinishLayout(int firstIndex, int lastIndex) {
    print('firstIndex: $firstIndex, lastIndex: $lastIndex');
  }

  ///可不重写 重写不能为null  默认是true  添加进来的实例与之前的实例是否相同 相同返回true 反之false
  ///listView 暂时没有看到应用场景 源码中使用在 SliverFillViewport 中
  @override
  bool shouldRebuild(SliverChildBuilderDelegate oldDelegate) {
    // TODO: implement shouldRebuild
    print("oldDelegate$oldDelegate");
    return super.shouldRebuild(oldDelegate);
  }
}
