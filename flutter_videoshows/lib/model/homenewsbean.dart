
//https://api.cdeclips.com/hknews-api/selectVideoHome

class HomeNewsBean {
  String resCode;
  String resMsg;
  List<ResObject> resObject;

  HomeNewsBean({this.resCode, this.resMsg, this.resObject});

  HomeNewsBean.fromJson(Map<String, dynamic> json) {
    resCode = json['resCode'];
    resMsg = json['resMsg'];
    if (json['resObject'] != null) {
      resObject = new List<ResObject>();
      json['resObject'].forEach((v) {
        resObject.add(new ResObject.fromJson(v));
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

class ResObject {
  int pageSize;
  int totalPage;
  int pagecode;
  int currentPage;
  int totalCount;
  int offset;
  String id;
  String title;
  String titleImage;
  String bigTitleImage;
  String authorName;
  String publishTime;
  String local;
  String jsonUrl;
  String murl;
  String description;
  String dataId;
  int dataType;
  String subjectName;
  String subjectCode;
  int state;
  String fullPublishTime;
  Null image;
  Null filterStr;
  int firstIndex;

  ResObject(
      {
        this.pageSize,
        this.totalPage,
        this.pagecode,
        this.currentPage,
        this.totalCount,
        this.offset,
        this.id,
        this.title,
        this.titleImage,
        this.bigTitleImage,
        this.authorName,
        this.publishTime,
        this.local,
        this.jsonUrl,
        this.murl,
        this.description,
        this.dataId,
        this.dataType,
        this.subjectName,
        this.subjectCode,
        this.state,
        this.fullPublishTime,
        this.image,
        this.filterStr,
        this.firstIndex});

  ResObject.fromJson(Map<String, dynamic> json) {
    pageSize = json['pageSize'];
    totalPage = json['totalPage'];
    pagecode = json['pagecode'];
    currentPage = json['currentPage'];
    totalCount = json['totalCount'];
    offset = json['offset'];
    id = json['id'];
    title = json['title'];
    titleImage = json['titleImage'];
    bigTitleImage = json['bigTitleImage'];
    authorName = json['authorName'];
    publishTime = json['publishTime'];
    local = json['local'];
    jsonUrl = json['jsonUrl'];
    murl = json['murl'];
    description = json['description'];
    dataId = json['dataId'];
    dataType = json['dataType'];
    subjectName = json['subjectName'];
    subjectCode = json['subjectCode'];
    state = json['state'];
    fullPublishTime = json['fullPublishTime'];
    image = json['image'];
    filterStr = json['filterStr'];
    firstIndex = json['firstIndex'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['pageSize'] = this.pageSize;
    data['totalPage'] = this.totalPage;
    data['pagecode'] = this.pagecode;
    data['currentPage'] = this.currentPage;
    data['totalCount'] = this.totalCount;
    data['offset'] = this.offset;
    data['id'] = this.id;
    data['title'] = this.title;
    data['titleImage'] = this.titleImage;
    data['bigTitleImage'] = this.bigTitleImage;
    data['authorName'] = this.authorName;
    data['publishTime'] = this.publishTime;
    data['local'] = this.local;
    data['jsonUrl'] = this.jsonUrl;
    data['murl'] = this.murl;
    data['description'] = this.description;
    data['dataId'] = this.dataId;
    data['dataType'] = this.dataType;
    data['subjectName'] = this.subjectName;
    data['subjectCode'] = this.subjectCode;
    data['state'] = this.state;
    data['fullPublishTime'] = this.fullPublishTime;
    data['image'] = this.image;
    data['filterStr'] = this.filterStr;
    data['firstIndex'] = this.firstIndex;
    return data;
  }
}