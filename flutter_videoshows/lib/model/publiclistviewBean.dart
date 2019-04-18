class PublicListViewBean {
  String resCode;
  String resMsg;
  ResObject resObject;

  PublicListViewBean({this.resCode, this.resMsg, this.resObject});

  PublicListViewBean.fromJson(Map<String, dynamic> json) {
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
  List<DateList> dateList;
  int pageSize;
  int totalPage;
  int pagecode;
  int currentPage;
  int totalCount;
  int offset;
  int firstIndex;

  ResObject(
      {this.dateList,
        this.pageSize,
        this.totalPage,
        this.pagecode,
        this.currentPage,
        this.totalCount,
        this.offset,
        this.firstIndex});

  ResObject.fromJson(Map<String, dynamic> json) {
    if (json['dateList'] != null) {
      dateList = new List<DateList>();
      json['dateList'].forEach((v) {
        dateList.add(new DateList.fromJson(v));
      });
    }
    pageSize = json['pageSize'];
    totalPage = json['totalPage'];
    pagecode = json['pagecode'];
    currentPage = json['currentPage'];
    totalCount = json['totalCount'];
    offset = json['offset'];
    firstIndex = json['firstIndex'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.dateList != null) {
      data['dateList'] = this.dateList.map((v) => v.toJson()).toList();
    }
    data['pageSize'] = this.pageSize;
    data['totalPage'] = this.totalPage;
    data['pagecode'] = this.pagecode;
    data['currentPage'] = this.currentPage;
    data['totalCount'] = this.totalCount;
    data['offset'] = this.offset;
    data['firstIndex'] = this.firstIndex;
    return data;
  }
}

class DateList {
  Null dateList;
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

  DateList(
      {this.dateList,
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

  DateList.fromJson(Map<String, dynamic> json) {
    dateList = json['dateList'];
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
    data['dateList'] = this.dateList;
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