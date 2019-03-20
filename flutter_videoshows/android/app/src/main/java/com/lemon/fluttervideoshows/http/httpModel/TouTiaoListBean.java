package com.lemon.fluttervideoshows.http.httpModel;



import java.io.Serializable;

public class TouTiaoListBean implements Serializable {
    public String id ;
    public String dataId;
    public String title ;       //标题
    public String title_image ; //图片
    public String author_name ;  //作者
    public String local ;        //本地html
    public String publishTime ;  //发布时间
    public String jsonUrl ;      //json详情
    public String description ; //描述
    public String big_title_image ; //app专用大图
    public String mUrl ; //app专用详情
    //    "subjectName": "Hong Kong",
//             "subjectCode": "hong_kong",
    public String listType;  //1.普通新闻 2.Opinion 3.视频
    public String subjectName;
    public String subjectCode;
    public boolean isSelected;//是否选中，本地业务逻辑字段
    //收藏添加的字段
    public String collectionId;

    //没有图片新添加的字段  bigTitleImage titleImage
    public String bigTitleImage;
    public String titleImage;

    public String getBigTitleImage() {
        return bigTitleImage;
    }

    public void setBigTitleImage(String bigTitleImage) {
        this.bigTitleImage = bigTitleImage;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_image() {
        return title_image;
    }

    public void setTitle_image(String title_image) {
        this.title_image = title_image;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBig_title_image() {
        return big_title_image;
    }

    public void setBig_title_image(String big_title_image) {
        this.big_title_image = big_title_image;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

}
