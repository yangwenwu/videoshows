package com.lemon.fluttervideoshows.http.httpModel;


/**
 * Created by liao on 2017/7/4.
 */

public class BaseResultBean extends BaseBean {

    public String code;//		接口调用结果状态。
    //    取值：success表示访问接口成功，并非完全指接口业务操作成功，通常情况下，业务操作成功要根据data属性的值来判断；
//    failed 表示接口调用失败或网络异常或业务数据验证失败抛出的业务异常或系统错误异常等，具体错误信息参考msg属性。
//    access_denied 表示接口接口授权被拒绝,需重新登录。	是
    public String data;//	object	接口执行成功后的返回值，任意数据类型或数据结构体。	否
    public String msg;//	string	表示失败后的错误消息。如果code=success,则此属性可以没有	否

}
