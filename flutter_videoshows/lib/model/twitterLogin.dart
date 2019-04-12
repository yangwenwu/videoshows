class TwitterLoginBean {
  String username;
  String userId;

  TwitterLoginBean(
      {this.username,  this.userId});

  TwitterLoginBean.fromJson(Map<String, dynamic> json) {
    username = json['username'];
    userId = json['userId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['username'] = this.username;
    data['userId'] = this.userId;
    return data;
  }
}
