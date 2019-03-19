
class CategoryBean {
  String resCode;
  String resMsg;
  List<CateResObject> resObject;

  CategoryBean({this.resCode, this.resMsg, this.resObject});

  CategoryBean.fromJson(Map<String, dynamic> json) {
    resCode = json['resCode'];
    resMsg = json['resMsg'];
    if (json['resObject'] != null) {
      resObject = new List<CateResObject>();
      json['resObject'].forEach((v) {
        resObject.add(new CateResObject.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['resCode'] = this.resCode;
    data['resMsg'] = this.resMsg;
    if (this.resObject != null) {
      data['resObject'] = this.resObject.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class CateResObject {
  String id;
  String code;
  String name;
  String focusUrl;
  String jsonUrl;
  String parentCode;
  String imageUrl;

  CateResObject(
      {this.id,
        this.code,
        this.name,
        this.focusUrl,
        this.jsonUrl,
        this.parentCode,
        this.imageUrl});

  CateResObject.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    code = json['code'];
    name = json['name'];
    focusUrl = json['focusUrl'];
    jsonUrl = json['jsonUrl'];
    parentCode = json['parentCode'];
    imageUrl = json['imageUrl'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['code'] = this.code;
    data['name'] = this.name;
    data['focusUrl'] = this.focusUrl;
    data['jsonUrl'] = this.jsonUrl;
    data['parentCode'] = this.parentCode;
    data['imageUrl'] = this.imageUrl;
    return data;
  }
}