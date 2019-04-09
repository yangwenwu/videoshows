package com.lemon.video.http.common;

import android.content.Context;

import com.lemon.video.http.httpModel.BaseModel;
import com.lemon.video.http.httpModel.CommentModel;
import com.lemon.video.http.httpModel.FeedbackModel;
import com.lemon.video.http.httpModel.ListPageModel;
import com.lemon.video.http.httpModel.LoginModel;
import com.lemon.video.http.httpModel.RegisterModel;
import com.lemon.video.http.httpModel.ThirdLoginModel;
import com.lemon.video.http.httpModel.UserImageModel;
import com.lemon.video.http.https.Okhttp3Utils;
import com.lemon.video.http.https.ResponseListener;

public class Commrequest {

    public static void getPageData(Context context, ListPageModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList";
        postNoCache(context, model, false, listener);
    }

    //首页获取数据接口
    public static void getDataList(Context context, String code, int page, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList?subjectCode=" +code+ "&currentPage=" + page+"&dataType=1";
        getNoCache(context, listener, false, model);
    }
    //latest
    public static void getLatestDataList(Context context, String code, int page, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList?subjectCode=" +code+ "&currentPage=" + page+"&dataType=3";
        getNoCache(context, listener, false, model);
    }

    //获取视频栏目列表
    //http://203.186.80.109/hknews-api/selectNewsList?subjectCode=hong_hong_by_night&currentPage=1&dataType=3
    public static void getVideoTypeList(Context context, String code, int page, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList?subjectCode=" +code+ "&currentPage=" + page+"&dataType=3";
        getNoCache(context, listener, false, model);
    }

    //home主页  http://203.186.80.109/hknews-api/homeDataNewsList
    public static void getHomeDataList(Context context, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "homeDataNewsList";
        getNoCache(context, listener, false, model);
    }

    //top首页数据
    //http://203.186.80.109/hknews-api/selectVideoHome
    public static void getHomeTopList(Context context, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectVideoHome";
        getNoCache(context, listener, false, model);
    }

    //视频分类
    //https://api.cdeclips.com/hknews-api/selectSubjectList?parentCode=video
    //http://203.184.141.249/hknews-api/selectSubjectList?parentCode=video
    public static void getVideoCategory(Context context, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
//        model.urlPrefix = "http://210.5.58.206/hknews-api/";
        model.urlEnd = "selectSubjectList?parentCode=video";
        getNoCache(context, listener, false, model);
    }

    //视频video 栏目
    //http://203.186.80.109/hknews-api/selectNewsList?currentPage="+ page+"&dataType=3
    //subjectCode
    public static void getVideoDataList(Context context, int page, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList?currentPage="+ page+"&dataType=3";
        getNoCache(context, listener, false, model);
    }

    // 注册发送验证码邮件
    //http://203.186.80.109 /hknews-api/sendEmail?account=532073954@qq.com
    public static void sendEmailCode(Context context, String email, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "sendEmail?account="+ email;
        getNoCache(context, listener, false, model);
    }


    //找回密码
    //http://203.186.80.109/hknews-api/retrievePassword
    //参数  account
    public static void sendCode(Context context, String email, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "retrievePassword?account="+ email;
        getNoCache(context, listener, false, model);
    }

    //注册接口
    //http://203.186.80.109/hknews-api/register
    public static void register(Context context, RegisterModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "register";
        postNoCache(context, model, false, listener);
    }

    //第三方登录
    //http://203.186.80.109/hknews-api/otherLogin?otherAccount=12345678&nickname=test&headImage=222.jsp
    public static void thirdLogin(Context context, String type, String uid, String nickName, String headImage, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "otherLogin?otherAccount="+ uid +"&nickname="+nickName +"&headImage="+headImage +"&otherType="+type;
        getNoCache(context, listener, false, model);
    }

    //第三方登录   舍弃
    //http://203.186.80.109/hknews-api/otherLogin
    public static void thirdPartLogin(Context context, ThirdLoginModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "otherLogin";
        postNoCache(context, model, false, listener);
    }

    //第三方头像
    //http://203.186.80.109/hknews-api/modifyHeadImg
    public static void loadPic(Context context, ThirdLoginModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "modifyHeadImg";
        postNoCache(context, model, false, listener);
    }


    //用户登录接口
    //http://203.186.80.109/hknews-api/login
    public static void login(Context context, LoginModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "login";
        postNoCache(context, model, false, listener);
    }

    //修改用户昵称
    //http://203.186.80.109/hknews-api/modifyNickName?userId=1&nickname=testnickname
    //get  http://203.186.80.109/hknews-api/modifyNickName
    //参数  userId  nickname
    public static void changeNickName(Context context, String id, String nickname, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "modifyNickName?userId="+ id +"&nickname="+nickname;
        getNoCache(context, listener, false, model);
    }

    //修改用户密码
    //http://203.186.80.109/hknews-api/modifyPassWord?userId=1&password=123&oldPassword=1111
    public static void changePsw(Context context, String id, String newPsw, String oldPsw, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "modifyPassWord?userId="+ id +"&password="+newPsw+"&oldPassword="+oldPsw;
        getNoCache(context, listener, false, model);
    }

    //首页获取焦点图数据接口
    public static void getFocusList(Context context, String code , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList?subjectCode=" +code+"&dataType=2";
        getNoCache(context, listener, false, model);
    }

    //查询点赞数 http://203.186.80.109/hknews-api/searchLike?newsId=dataId
    public static void getGoodCount(Context context, String newsId , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "searchLike?newsId=" +newsId;
        getNoCache(context, listener, false, model);
    }
    //点赞  http://203.186.80.109/hknews-api/like?newsId=dataId
    public static void setGood(Context context, String newsId , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "like?newsId=" +newsId;
        getNoCache(context, listener, false, model);
    }

    //获取腾讯视频地址接口  http://203.186.80.109/hknews-api/selectCloudInfo?id=9031868223277417589
    //返回结果  {"resCode":"200","resMsg":"success","resObject":{"id":"9031868223277417589","url":"http://1252065688.vod2.myqcloud.com/d7dc3e4avodgzp1252065688/56b9f8919031868223277417589/Cpc5jIWeADcA.mp4"}}
    public static void getQCloudUrl(Context context, String newsId , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectCloudInfo?id=" +newsId;
        getNoCache(context, listener, false, model);
    }

    //获取新闻详情接口
    public static void getNewsDetail(Context context, String dataId, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selecNewsDetail?dataId=" + dataId;
        getNoCache(context, listener, false, model);
    }

    //获取视频接口详情 http://203.186.80.109/hknews-api/selecNewsDetail?dataId=3942
    //接口同上

    //获取json文件通用入口   除动态接口外的接口
    public static void getCommonJson(Context context, String jsonUrl , ResponseListener listener ) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.SERVICEURL;
        model.urlEnd = jsonUrl;
        getCache(context, listener, false, model);
    }

    //用户反馈
    //http://203.186.80.109/hknews-api/addFeedback?content=test&email=5345353453@qq.com&type=2  1是news2是video
    //http://203.186.80.109/hknews-api/addFeedback?content=test&email=5345353453@qq.com&type=2
    public static void submitFeedBack(Context context, String content, String contact, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "addFeedback?content=" + content+"&email=" + contact+"&type=2";
        getNoCache(context, listener, false, model);
    }

    //post feedback
    //http://203.186.80.109/hknews-api/ addFeedback
    public static void submitFeedBack1(Context context, FeedbackModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "addFeedback";
        postNoCache(context, model, false, listener);
    }

    //post 上传图片
    //http://203.186.80.109/hknews-api/modifyHeadImg
    public static void modifyImage(Context context, UserImageModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "modifyHeadImg";
        postNoCache(context, model, false, listener);
    }

    //查询用户收藏列表
    //http://203.186.80.109/hknews-api/searchCollect?userId=1&type=2
    //http://203.186.80.109/hknews-api/searchCollect?userId=a92af950-bb1f-11e7-9872-00155d03d036&type=2&currentPage=1
    public static void queryCollectionList(Context context, String id, int page, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "searchCollect?userId=" + id+"&type=2"+"&currentPage="+page;
        getNoCache(context, listener, false, model);
    }

    //查询该视频是否已收藏
    //http://203.186.80.109/hknews-api/isCollect?userId=1&newsId=1234
    public static void qureyIsCollect(Context context, String userId, String dataId, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "isCollect?userId=" + userId+"&newsId=" + dataId;
        getNoCache(context, listener, false, model);
    }

    //添加收藏
    //http://203.186.80.109/hknews-api/addCollect?userId=1&newsId=1&type=2
    public static void addCollection(Context context, String id, String newsId, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "addCollect?userId=" + id+"&type=2"+"&newsId="+ newsId;
        getNoCache(context, listener, false, model);
    }
    //取消删除收藏
    //http://203.186.80.109/hknews-api/delCollect?userId=1&newsId=12436&type=2
    public static void cancelCollection(Context context, String id, String newsId , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "delCollect?userId=" + id+"&type=2"+"&newsId="+ newsId;
        getNoCache(context, listener, false, model);
    }

    //收藏中删除一个或者多个视频  https://api.cdeclips.com/hknews-api/delCollects?userId=29e722ff-73cb-11e8-a6b6-00155d033e1b&newsIds=39349,39896&type=2
    //http://203.186.80.109/hknews-api/delCollects
    //http://203.186.80.109/hknews-api/delCollects?newsIds=123321,234234&userId=2&type=2
    public static void deLCollectionList(Context context, String id, String newsId , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "delCollects?userId=" + id+"&newsIds="+ newsId+"&type=2";
        getNoCache(context, listener, false, model);
    }

    //查询视频评论列表
    //http://203.186.80.109/hknews-api/searchComment?newsId=17418&type=2
    //https://api.cdeclips.com/hknews-api/searchComment?newsId=62177&type=2&currentPage=1
    public static void queryCommentList(Context context, String newsId, int page, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "searchComment?type=2" + "&newsId="+ newsId+"&currentPage="+page;
        getNoCache(context, listener, false, model);
    }

    //评论
    //http://203.186.80.109/hknews-api/addComment
    //userId  newsId  content  type

    public static void addComment(Context context, CommentModel model, ResponseListener listener) {
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "addComment";
        postNoCache(context,model , false, listener);
    }

    ////http://203.186.80.109/hknews-api/addComment?userId=1&newsId=1&content=test&type=2
    public static void comment(Context context, String newsId , String id , String content, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "addComment?type=2" + "&newsId="+ newsId +"&userId="+id +"&content="+ content;
        getNoCache(context, listener, false, model);
    }

    //查询评论数量
    //http://203.186.80.109/hknews-api/countComment?newsId=12346&type=2
    public static void queryCommentCount(Context context, String newsId , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "countComment?type=2" + "&newsId="+ newsId;
        getNoCache(context, listener, false, model);
    }

    //版本更新
    //http://203.186.80.109/hknews-api/getVersion?versionPackage=com.lemon.video
    public static void queryVersion(Context context, ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "getVersion?versionPackage=com.lemon.video";
        getNoCache(context, listener, false, model);
    }

    //搜索视频  有分页
    //http://203.186.80.109/hknews-api/selectNewsList?dataType=3&title=tradition
    public static void queryVideoList(Context context, String title, int page , ResponseListener listener) {
        BaseModel model = new BaseModel();
        model.urlPrefix = HttpConstants.STATICURL;
        model.urlEnd = "selectNewsList?dataType=3"+"&currentPage="+page + "&title="+ title;
        getNoCache(context, listener, false, model);
    }

    //根据id查询视频信息
    public static void getVideoInfoData(Context context, String url, ResponseListener listener){
        BaseModel model = new BaseModel();
        model.urlPrefix = "https://vod.api.qcloud.com/v2/index.php?";
        model.urlEnd = url;
        getNoCache(context, listener, false, model);
    }

    //获取ip,判断区域地址   http://ip.taobao.com/service/getIpInfo.php?ip=myip
    public static void getIPRegion(Context context, ResponseListener listener){
        BaseModel model = new BaseModel();
        model.urlPrefix = "http://ip.taobao.com/service/";
//        model.urlEnd = "getIpInfo.php?ip=myip" ;
        model.urlEnd = "getIpInfo.php?ip=220.112.18.1" ;
        getNoCache(context, listener, false, model);
    }

    private static void getCache(Context context, ResponseListener listener, boolean isUserApi, BaseModel model) {
        Okhttp3Utils.getInstance().get(context, model, true, isUserApi, listener);
    }

    private static void getNoCache(Context context, ResponseListener listener, boolean isUserApi, BaseModel model) {
        Okhttp3Utils.getInstance().get(context, model, false, isUserApi, listener);
    }

    private static void postNoCache(Context context, BaseModel model, boolean isUserApi, ResponseListener listener) {
        Okhttp3Utils.getInstance().post(context, model, false, isUserApi, listener);
    }

    private static void postCache(Context context, BaseModel model, boolean isUserApi, ResponseListener listener) {
        Okhttp3Utils.getInstance().post(context, model, true, isUserApi, listener);
    }

    /********政务号焦点图   保存到sharepre*****/
    private static void getSharePreCache(Context context, ResponseListener listener, boolean isUserApi, String sharePKey, BaseModel model) {
        Okhttp3Utils.getInstance().fromShareGet(context, model, true, isUserApi,sharePKey,listener);
    }

}
