import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

class FirstTab extends StatefulWidget {
  @override
  _FirstTabState createState() => _FirstTabState();
}

class _FirstTabState extends State<FirstTab> {
  final _suggestions = <WordPair>[];

  final _saved = new Set<WordPair>();

  final _textFont = const TextStyle(fontSize: 18.0);

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        backgroundColor: Colors.black,
        centerTitle: true,
        title: new Text('SHOW'),
        //在appbar上面添加一个列表图标，可点击跳转新节目
        actions: <Widget>[
          new IconButton(icon: new Icon(Icons.search), onPressed: _pushSaved)
        ],
      ),
      body: _buildSuggestions(),
    );
  }

  Widget _buildSuggestions() {
    return new ListView.builder(
        padding: const EdgeInsets.all(16.0),
      // 对于每个建议的单词对都会调用一次itemBuilder，然后将单词对添加到ListTile行中
      // 在偶数行，该函数会为单词对添加一个ListTile row.
      // 在奇数行，该行书湖添加一个分割线widget，来分隔相邻的词对。
      // 注意，在小屏幕上，分割线看起来可能比较吃力。
        itemBuilder: (context, i) {
          // 在每一列之前，添加一个1像素高的分隔线widget
          if (i.isOdd) return new Divider();
//          语法 "i ~/ 2" 表示i除以2，但返回值是整形（向下取整）
          final index = i ~/ 2;

          //是最后一个单词的时候
          if (index >= _suggestions.length) {
            // ...接着再生成10个单词对，然后添加到建议列表
            _suggestions.addAll(generateWordPairs().take(10));
          }

          return _buildRow(_suggestions[index]);
        });
  }

  Widget _buildRow(WordPair pair) {
   
   
    
    final hasSaved = _saved.contains(pair);

    return new ListTile(
      title: new Text(
        pair.asPascalCase,
        style: _textFont,
      ),
      //加入心形收藏
      trailing: new Icon(
        hasSaved ? Icons.favorite : Icons.favorite_border,
        color: hasSaved ? Colors.red : null,
      ),
      //心形收藏被点击事件,点击修改状态
      onTap: (){
        //弹出一个SnackBar
        Scaffold.of(context).showSnackBar(new SnackBar(
          content: Text(pair.asPascalCase),
          action: new SnackBarAction(
            label: "撤销",
            onPressed: (){
              //撤销点击
      
            }),
        ));
        setState(() {
          if(hasSaved){
            _saved.remove(pair);
          }else{
            _saved.add(pair);
          }
        });
      },
    );
  }

  //appbar上的列表图标点击事件
  void _pushSaved() {
    Navigator.of(context).push(
      new MaterialPageRoute(
        builder: (context){
        final titles = _saved.map(
          (pair){
            return new ListTile(
              title: new Text(
                pair.asPascalCase,
                style: _textFont,
              ),
            
            
            );
          }
        
        );
        final divided = ListTile.divideTiles(
          context: context,
          
          tiles: titles).toList();
        
        return new Scaffold(
          appBar: new AppBar(
            title: new Text("new page"),
          ),
          body: new ListView(children: divided),
        );
        
      })
    );
  }
}
