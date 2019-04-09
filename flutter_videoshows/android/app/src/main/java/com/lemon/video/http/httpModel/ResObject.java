package com.lemon.video.http.httpModel;

import java.io.Serializable;

/**
 * Created by Staff on 2017/9/30.
 */

public class ResObject implements Serializable {
//            "sourceId": "2558",
//            "subjectName": "Hong Kong Enquirer",
//            "subjectCode": "hong_kong_enquirer",
//            "type": null,
//            "headImage": "/attachments/image/47/158/143/30129_29052/30129_29052_800_500_jpg.jpg",
//            "title": "Organic  Wordplay  |  2015  Aug",
//             "description":
//            "jsonUrl": "/articles/191/29/84/1495079083174.json",
//            "publishTime": "May 18, 2017",
//            "fullPublishTime": "2017-05-18 14:09:20",
//            "author": "Andrea Deng",
//            "dataId": "3942",
//            "dataType": 3,
//            "state": 1,
//            "videoOuterLink":"{\"inner\":{\"type\":\"\",\"url\":\"\"},\"local\":{\"type\":\"\",\"url\":\"\"},\"outer\":{\"type\":\"ytb\",\"url\":\"7ZWHcZv2BaU\"}}"}"

    public String sourceId;
    public String subjectName;
    public String subjectCode;
    public String type;
    public String headImage;
    public String title;
    public String description;
    public String jsonUrl;
    public String publishTime;
    public String fullPublishTime;
    public String author;
    public String dataId;
    public String dataType;
    public int state;
//    public VideoLink videoOuterLink;
    public String videoOuterLink;
    public String txyUrl;
    public String ytbUrl;
}
