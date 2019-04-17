import 'package:flutter_videoshows/http/httpRequest.dart';

import 'dataResult.dart';
import 'resultData.dart';

/// 登录 model
class LoginModel{
  // 手机号码登录
//  static phoneLogin(String phone,String verifyCode) async{
//    ResultData response = await HttpRequest.post(Address.phoneLogin, {"phoneNum" : phone,"captcha":verifyCode});
//    if(response != null && response.result){
//      PhoneLoginEntity phoneLoginEntity = PhoneLoginEntity.fromJson(json.decode(response.data));
//      return new DataResult(phoneLoginEntity, true);
//    }else{
//      return new DataResult(null, false);
//    }
//  }

  // 获取验证码
//  static getVerifyCode(String phone) async{
//    ResultData response = await HttpRequest.get("${Address.getVerifyCode}?phone=${phone}", null);
//
////    var response = await HttpRequest.get(Address.getVerifyCode, {"phone":phone});
//    if(response != null && response.result){
//      VerifyCodeEntity entity = VerifyCodeEntity.fromJson(response.data);
//      return new DataResult(entity, true);
//    }else{
//      return new DataResult(null, false);
//    }
//  }
}
