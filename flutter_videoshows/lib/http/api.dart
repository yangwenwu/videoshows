
import 'dart:convert';

import 'package:flutter_videoshows/http/resultData.dart';
import 'package:flutter_videoshows/model/homenewsbean.dart';

import 'dataResult.dart';
import 'httpRequest.dart';

class Api {

  static topListData() async {
    ResultData resultData = await  HttpRequest.get("selectVideoHome", null);
    if(resultData != null && resultData.result){
      print(resultData.data);
//      HomeNewsBean phoneLoginEntity = HomeNewsBean.fromJson(json.decode(resultData.data));
      HomeNewsBean phoneLoginEntity = HomeNewsBean.fromJson(resultData.data);
      return new DataResult(phoneLoginEntity, true);
    }else{
      return new DataResult(null, false);
    }
  }
}