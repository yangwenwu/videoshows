import 'dart:async';

class DataResult {
//  var data;
  dynamic data;
  bool result;
  Future next;

  DataResult(this.data, this.result, {this.next});
}