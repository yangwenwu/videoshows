package com.lemon.video.http.httpModel;

/**
 * Created by liao on 2017/7/2.
 */

public class BaseModel {

    public String urlPrefix;//url请求头地址
    public String urlEnd;//url请求尾部



    public String toStringAllUrl() {
        return urlPrefix + urlEnd;
    }
}
