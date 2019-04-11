class UserBean {
  String resCode;
  String resMsg;
  ResObject resObject;

  UserBean({this.resCode, this.resMsg, this.resObject});

  UserBean.fromJson(Map<String, dynamic> json) {
    resCode = json['resCode'];
    resMsg = json['resMsg'];
    resObject = json['resObject'] != null
        ? new ResObject.fromJson(json['resObject'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['resCode'] = this.resCode;
    data['resMsg'] = this.resMsg;
    if (this.resObject != null) {
      data['resObject'] = this.resObject.toJson();
    }
    return data;
  }
}

class ResObject {
  String nickName;
  String headImage;
  String id;
  Null account;
  String otherType;
  String token;

  ResObject(
      {this.nickName,
        this.headImage,
        this.id,
        this.account,
        this.otherType,
        this.token});

  ResObject.fromJson(Map<String, dynamic> json) {
    nickName = json['nickName'];
    headImage = json['headImage'];
    id = json['id'];
    account = json['account'];
    otherType = json['otherType'];
    token = json['token'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['nickName'] = this.nickName;
    data['headImage'] = this.headImage;
    data['id'] = this.id;
    data['account'] = this.account;
    data['otherType'] = this.otherType;
    data['token'] = this.token;
    return data;
  }
}