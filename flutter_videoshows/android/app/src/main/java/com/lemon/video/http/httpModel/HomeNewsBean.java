package com.lemon.video.http.httpModel;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Staff on 2017/9/19.
 */

public class HomeNewsBean extends BaseBean implements MultiItemEntity {
//    "id": "0143fe08-3a6c-4a90-80a9-0559fac19035",
//            "title": "Turkey  sentences  UN  judge  to  7.5  yrs  on  'terrorism'  charges",
//            "titleImage": null,
//            "bigTitleImage": null,
//            "authorName": null,
//            "publishTime": "June 15, 2017",
//            "local": "/articles/173/54/4/1497521105874.html",
//            "jsonUrl": "/articles/216/38/54/1497521105887.json",
//            "murl": "/articles/156/108/71/1497521105894.html",
//            "description": "Judge  Aydin  Sedaf  Akay  has  been  held  since  September,  one  of  tens  of  thousands  of  Turkish  officials  arrested  in  a  crackdown.",
//            "dataId": "6393",
//            "dataType": 1,
//            "subjectName": "Asia News",
//            "subjectCode": "asia",
//            "state": 1,

    public String id;
    public String title;
    public String titleImage;
    public String bigTitleImage;
    public String authorName;
    public String publishTime;
    public String local;
    public String jsonUrl;
    public String murl;
    public String htmlUrl;
    public String description;
    public String dataId;
    public String dataType;
    public String subjectName;
    public String subjectCode;
    public int state;

    @Override
    public int getItemType() {
        return 0;
    }
}
