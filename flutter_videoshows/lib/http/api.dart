import 'package:flutter_videoshows/http/resultData.dart';
import 'package:flutter_videoshows/model/categorybean.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';
import 'package:flutter_videoshows/model/publiclistviewBean.dart';

import 'dataResult.dart';
import 'httpRequest.dart';

class Api {
///首页top接口
  static Future<DataResult> topListData() async {
    ResultData resultData = await  HttpRequest.get("selectVideoHome", null);
    if(resultData != null && resultData.result){
      print(resultData.data);
      HomeNewsBean newsBean = HomeNewsBean.fromJson(resultData.data);
      return new DataResult(newsBean, true);
    }else{
      return new DataResult(null, false);
    }
  }


///首页category接口
  static Future<DataResult> categoryListData() async{
    ResultData resultData = await  HttpRequest.get("selectSubjectList?parentCode=video", null);
    if(resultData != null && resultData.result){
      print(resultData.data);
      CategoryBean newsBean = CategoryBean.fromJson(resultData.data);
      return new DataResult(newsBean, true);
    }else{
      return new DataResult(null, false);
    }
  }


  ///category的栏目，分类查询   https://api.cdeclips.com/hknews-api/selectNewsList?subjectCode=movie_corner&currentPage=1&dataType=3
  static Future<DataResult> categoryItemListData(var code,var page) async{
    ResultData resultData = await  HttpRequest.get("selectNewsList?subjectCode=$code&currentPage=$page&dataType=3", null);
    if(resultData != null && resultData.result){
      print(resultData.data);
      PublicListViewBean newsBean = PublicListViewBean.fromJson(resultData.data);
      return new DataResult(newsBean, true);
    }else{
      return new DataResult(null, false);
    }
  }

}