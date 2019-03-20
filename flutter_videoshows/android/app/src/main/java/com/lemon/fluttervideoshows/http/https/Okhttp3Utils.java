package com.lemon.fluttervideoshows.http.https;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.lemon.fluttervideoshows.http.httpModel.BaseJsonBean;
import com.lemon.fluttervideoshows.http.httpModel.BaseModel;
import com.lemon.fluttervideoshows.http.httpModel.BaseResultBean;
import com.lemon.fluttervideoshows.utils.FileHelper;
import com.lemon.fluttervideoshows.utils.SPUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp3的网络请求框架封装
 * <p>
 * <p>
 */

public class Okhttp3Utils {


    private static Okhttp3Utils mInstance;
    private final OkHttpClient mOkHttpClient;

    /**
     * 获取单例引用
     *
     * @return
     */
    public static Okhttp3Utils getInstance() {
        if (mInstance == null) {
            synchronized (Okhttp3Utils.class) {
                if (mInstance == null) {
                    mInstance = new Okhttp3Utils();
                }
            }
        }
        return mInstance;
    }

    private OkHttpClient getHttpClient() {
        if (mOkHttpClient != null) {
            return mOkHttpClient;
        } else {
//          强制初始化
            mInstance = null;
            getInstance();
            return getHttpClient();
        }
    }


    /**
     * 初始化RequestManager
     */
    public Okhttp3Utils() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(20, TimeUnit.SECONDS)//设置写入超时时间
                .addNetworkInterceptor(new LogInterceptor())//日志拦截器
                .build();
    }

    public void get(@NonNull final Context context, final BaseModel model, boolean isCacheModel, boolean isUserApi, final ResponseListener listener) {
        final WeakReference<Context> weakReference = new WeakReference<>(context);
        Request.Builder requestBuilder = new Request.Builder().url(model.toStringAllUrl());
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null).addHeader("Content-Type", "application/json");
        BaseJsonBean bean = null;
        if (isCacheModel) {
            //      添加
            String localData = FileHelper.readStringFromSDCardByUrlEnd(model.urlEnd);
            String localTS = "1234567";
            if (!TextUtils.isEmpty(localData)) {
                // TODO: 2017/7/2 这里的 T 泛型不一定能解析出对象来
                bean = JSON.parseObject(localData, BaseJsonBean.class);
                if (bean != null) {
                    localTS = bean.timestemp;
                }
            }
            requestBuilder.addHeader("If-Modified-Since", localTS);  //Wed, 22 Mar 2017 07:04:44 GMT
        }

        Request request = requestBuilder.build();
        request(model, listener, weakReference, bean, isCacheModel,isUserApi, request);
    }

    //这里的 T 泛型不一定能解析出对象来  针对这里解析不出来进行修改
//    public void getJson(@NonNull final Context context, final BaseModel model, boolean isCacheModel, boolean isUserApi, final ResponsePageListener listener) {
//        final WeakReference<Context> weakReference = new WeakReference<>(context);
//        Request.Builder requestBuilder = new Request.Builder().url(model.toStringAllUrl());
//        //可以省略，默认是GET请求
//        requestBuilder.method("GET", null).addHeader("Content-Type", "application/json");
//        BasePageJsonBean bean = null;
//        if (isCacheModel) {
//            //      添加
//            String localData = FileHelper.readStringFromSDCardByUrlEnd(model.urlEnd);
//            String localTS = "1234567";
//            if (!TextUtils.isEmpty(localData)) {
//                // TODO: 2017/7/2 这里的 T 泛型不一定能解析出对象来
//                bean = JSON.parseObject(localData, BasePageJsonBean.class);
//                if (bean != null) {
//                    localTS = bean.timestemp;
//                }
//            }
//            requestBuilder.addHeader("If-Modified-Since", localTS);  //Wed, 22 Mar 2017 07:04:44 GMT
//        }
//
//        Request request = requestBuilder.build();
//        request(model, listener, weakReference, bean, isCacheModel,isUserApi, request);
//    }





    public void post(@NonNull final Context context, final BaseModel model, boolean isCacheModel, boolean isUserApi, final ResponseListener listener) {
        final WeakReference<Context> weakReference = new WeakReference<>(context);
        FormBody.Builder builder = new FormBody.Builder();
//      如果是BaseModel 基内就不用添加
        if (!model.getClass().getSimpleName().equals(BaseModel.class.getSimpleName())) {
            //通过反射来获取字段名
            Field[] list = model.getClass().getDeclaredFields();
            for (Field field : list) {
                try {
                    Object object = field.get(model);
                    if (object != null && object instanceof String) {//这里为空的字段不用传到服务器
//                        Log.i("tag","field.getName():"+field.getName() +";field.get(model):"+field.get(model));
                        builder.add(field.getName(), (String) field.get(model));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        final FormBody body = builder.build();
        Request.Builder requestBuilder = new Request.Builder().url(model.toStringAllUrl()).addHeader("Content-Type", "application/json");
        BaseJsonBean bean = null;
        if (isCacheModel) {
            //      添加
            String localData = FileHelper.readStringFromSDCardByUrlEnd(model.urlEnd);
            String localTS = "1234567";
            if (!TextUtils.isEmpty(localData)) {
                bean = JSON.parseObject(localData, BaseJsonBean.class);
                if (bean != null) {
                    localTS = bean.timestemp;
                }
            }
            requestBuilder.addHeader("If-Modified-Since", localTS);  //Wed, 22 Mar 2017 07:04:44 GMT
        }
        Request request = requestBuilder.post(body).build();
        request(model, listener, weakReference, bean, isCacheModel, isUserApi, request);
    }

    //这个是不带sharePrefrence
    private void request(final BaseModel model, final ResponseListener listener, final WeakReference<Context> weakReference, final BaseJsonBean bean, final boolean isCacheModel, final boolean isUserApi, final Request request) {
        //      这里保证finalBean不为null
        final BaseJsonBean finalBean = bean == null ? new BaseJsonBean() : bean;
        getHttpClient().newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, final Response response) throws IOException {
                boolean ok = false;//response.isSuccessful();
                if (response.code() == 200) {
                    final String bodyStr = response.body().string();
                    if (isUserApi) {//如果是userApi，则用统一的json解析格式
                        final BaseResultBean resultBean = JSON.parseObject(bodyStr, BaseResultBean.class);
                        if (resultBean != null) {
                            if ("success".equals(resultBean.code)) {
//                      表示成功
                                finalBean.object = resultBean.data;
//                           做回调处理
                                callback(response, true, listener, weakReference, finalBean);
                            } else if ("access_denied".equals(resultBean.code)) {
//                        授权被拒绝,需重新登录
                                // TODO: 2017/7/4 做公共重新登录处理,暂时做为失败处理
                            } else {
//                           表示失败
//                           做回调处理
//                                callback(response, false, listener, weakReference, finalBean);
                                failCallback(bean,resultBean.msg,listener,weakReference);
                            }
                        }
                        return;
                    }
                    ok = true;
                    System.out.print(String.valueOf(response.code()));
                    finalBean.timestemp = response.header("Last-Modified", "");
                    finalBean.object = bodyStr;//JSONObject.parseObject(bodyStr, tClass);
                    System.out.print(finalBean.timestemp);
//                    String result = response.body().string();
                    if (isCacheModel) {
                        //同时也存一份到文件里面
                        FileHelper.writeConfigToSDCard(model.urlEnd, JSON.toJSONString(finalBean));
                    }
                } else if (response.code() == 304) {
                    //说明文件没有更新，那就读取本地文件
                    ok = true;
                }
//              做回调处理
                callback(response, ok, listener, weakReference, finalBean);
            }

            public void onFailure(Call call, final IOException e) {
                if (listener != null) {
                    final Context context = weakReference.get();
                    if (context != null) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {
//                                    在activity中在主线程中回调
                                    if (bean == null){
                                        listener.onFailure(new BaseJsonBean(),e.getMessage());
                                    }else{
                                        listener.onFailure(bean,e.getMessage());
                                    }
                                }
                            });
                        } else {
                            if (bean == null){
                                listener.onFailure(new BaseJsonBean(),e.getMessage());
                            }else{
                                listener.onFailure(bean,e.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

    private void callback(final Response response, boolean ok, final ResponseListener listener, WeakReference<Context> weakReference, final BaseJsonBean finalBean) {
        if (listener != null) {
            if (ok) {
                final Context context = weakReference.get();
//                  当当前请求的activity还存在时执行回调，否则不处理
                if (context != null) {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {
//                                  在activity中在主线程中回调
                                listener.onResponse(finalBean, response.code());
                            }
                        });
                    } else {
                        listener.onResponse(finalBean, response.code());
                    }
                }
            } else {
                failCallback(finalBean,response.code()+"", listener, weakReference);
            }
        }
    }


    private void failCallback(final BaseJsonBean finalBean, final String errmessage, final ResponseListener listener, WeakReference<Context> weakReference) {
        final Context context = weakReference.get();
        if (context != null) {
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {
//                       在activity中在主线程中回调
                        if (finalBean == null){
                            listener.onFailure(new BaseJsonBean(),errmessage );
                        }else{
                            listener.onFailure(finalBean,errmessage );
                        }
                    }
                });
            } else {
                if (finalBean == null){
                    listener.onFailure(new BaseJsonBean(),errmessage );
                }else{
                    listener.onFailure(finalBean,errmessage );
                }
            }
        }
    }

    /****
     *
     * @param context
     * @param model
     * @param isCacheModel
     * @param isUserApi
     * @param listener
     */

    public void post(@NonNull final Context context, final BaseModel model, boolean isCacheModel, boolean isShare , boolean isUserApi, final ResponseListener listener) {
        final WeakReference<Context> weakReference = new WeakReference<>(context);
        FormBody.Builder builder = new FormBody.Builder();
//      如果是BaseModel 基内就不用添加
        if (!model.getClass().getSimpleName().equals(BaseModel.class.getSimpleName())) {
            //通过反射来获取字段名
            Field[] list = model.getClass().getDeclaredFields();
            for (Field field : list) {
                try {
                    Object object = field.get(model);
                    if (object != null && object instanceof String) {//这里为空的字段不用传到服务器
//                        Log.i("tag","field.getName():"+field.getName() +";field.get(model):"+field.get(model));
                        builder.add(field.getName(), (String) field.get(model));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        final FormBody body = builder.build();
        Request.Builder requestBuilder = new Request.Builder().url(model.toStringAllUrl()).addHeader("Content-Type", "application/json");
        BaseJsonBean bean = null;
        if (isCacheModel) {
            //      添加
            String localData = FileHelper.readStringFromSDCardByUrlEnd(model.urlEnd);
            String localTS = "1234567";
            if (!TextUtils.isEmpty(localData)) {
                bean = JSON.parseObject(localData, BaseJsonBean.class);
                if (bean != null) {
                    localTS = bean.timestemp;
                }
            }
            requestBuilder.addHeader("If-Modified-Since", localTS);  //Wed, 22 Mar 2017 07:04:44 GMT
        }
        Request request = requestBuilder.post(body).build();
        request(model, listener, weakReference, bean, isCacheModel, isUserApi, request);
    }


    private void request(final BaseModel model, final ResponseListener listener, final WeakReference<Context> weakReference, final BaseJsonBean bean, final boolean isCacheModel, final boolean isNeedShare, final boolean isUserApi, Request request) {
        //      这里保证finalBean不为null
        final BaseJsonBean finalBean = bean == null ? new BaseJsonBean() : bean;
        getHttpClient().newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, final Response response) throws IOException {
                boolean ok = false;//response.isSuccessful();
                if (response.code() == 200) {
                    final String bodyStr = response.body().string();
                    if (isUserApi) {//如果是userApi，则用统一的json解析格式
                        BaseResultBean resultBean = JSON.parseObject(bodyStr, BaseResultBean.class);
                        if (resultBean != null) {
                            if ("success".equals(resultBean.code)) {
//                      表示成功
                                finalBean.object = resultBean.data;
//                           做回调处理
                                callback(response, true, listener, weakReference, finalBean);
                            } else if ("access_denied".equals(resultBean.code)) {
//                        授权被拒绝,需重新登录
                                // TODO: 2017/7/4 做公共重新登录处理,暂时做为失败处理
                                failCallback(bean,resultBean.msg,listener,weakReference);
                            } else {
//                           表示失败
//                           做回调处理
//                                callback(response, false, listener, weakReference, finalBean);
                                failCallback(bean,resultBean.msg,listener,weakReference);
                            }
                        }
                        return;
                    }
                    ok = true;
                    System.out.print(String.valueOf(response.code()));
                    finalBean.timestemp = response.header("Last-Modified", "");
                    finalBean.object = bodyStr;//JSONObject.parseObject(bodyStr, tClass);
                    System.out.print(finalBean.timestemp);
//                    String result = response.body().string();
                    if (isCacheModel) {
                        //同时也存一份到文件里面
                        FileHelper.writeConfigToSDCard(model.urlEnd, JSON.toJSONString(finalBean));
                    }
                    if (isNeedShare){
                        //当数据需要保存到share时
                        final Context context = weakReference.get();
                        if (context != null) {
                            if (context instanceof Activity) {
                                SPUtils.put(context,"model.urlEnd","");
                            }
                        }

                    }

                } else if (response.code() == 304) {
                    //说明文件没有更新，那就读取本地文件
                    ok = true;
                }
//              做回调处理
                callback(response, ok, listener, weakReference, finalBean);
            }
            public void onFailure(Call call, final IOException e) {
                if (listener != null) {
                    final Context context = weakReference.get();
                    if (context != null) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {
//                                    在activity中在主线程中回调
                                    if (bean == null){
                                        listener.onFailure(new BaseJsonBean(),e.getMessage());
                                    }else{
                                        listener.onFailure(bean,e.getMessage());
                                    }
                                }
                            });
                        } else {
                            if (bean == null){
                                listener.onFailure(new BaseJsonBean(),e.getMessage());
                            }else{
                                listener.onFailure(bean,e.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

    /***
     *   从sharePre获取缓存数据
     *
     */
    public void fromShareGet(@NonNull final Context context, final BaseModel model, boolean isCacheModel, boolean isUserApi, String sharePreKey, final ResponseListener listener) {
        final WeakReference<Context> weakReference = new WeakReference<>(context);
        Request.Builder requestBuilder = new Request.Builder().url(model.toStringAllUrl());
        //可以省略，默认是GET请求
        requestBuilder.method("GET", null).addHeader("Content-Type", "application/json");
        BaseJsonBean bean = null;
        if (isCacheModel) {
            //      添加
//            String localData = FileHelper.readStringFromSDCardByUrlEnd(model.urlEnd);
            String localData = (String) SPUtils.get(context,sharePreKey,"");
            String localTS = "1234567";
            if (!TextUtils.isEmpty(localData)) {
                // TODO: 2017/7/2 这里的 T 泛型不一定能解析出对象来
                bean = JSON.parseObject(localData, BaseJsonBean.class);
                if (bean != null) {
                    localTS = bean.timestemp;
                }
            }
            requestBuilder.addHeader("If-Modified-Since", localTS);  //Wed, 22 Mar 2017 07:04:44 GMT
        }

        Request request = requestBuilder.build();
        requestFromShare(model, listener, weakReference, bean, isCacheModel,isUserApi,sharePreKey, request );
    }


    /****
     * 这个附带保存数据到sharePrefrence   重要数据需要保存到share,就调用此接口
     * 处理政务号的订阅没
     */
    private void requestFromShare(final BaseModel model, final ResponseListener listener, final WeakReference<Context> weakReference,
                                  final BaseJsonBean bean, final boolean isCacheModel, final boolean isUserApi,
                                  final String sharePreKey , Request request) {
        //      这里保证finalBean不为null
        final BaseJsonBean finalBean = bean == null ? new BaseJsonBean() : bean;
        getHttpClient().newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, final Response response) throws IOException {
                boolean ok = false;//response.isSuccessful();
                if (response.code() == 200) {
                    final String bodyStr = response.body().string();
                    if (isUserApi) {//如果是userApi，则用统一的json解析格式
                        BaseResultBean resultBean = JSON.parseObject(bodyStr, BaseResultBean.class);
                        if (resultBean != null) {
                            if ("success".equals(resultBean.code)) {
//                      表示成功
                                finalBean.object = resultBean.data;
//                           做回调处理
                                callback(response, true, listener, weakReference, finalBean);
                            } else if ("access_denied".equals(resultBean.code)) {
//                        授权被拒绝,需重新登录
                                // TODO: 2017/7/4 做公共重新登录处理,暂时做为失败处理
                                failCallback(bean,resultBean.msg,listener,weakReference);
                            } else {
//                           表示失败
//                           做回调处理
//                                callback(response, false, listener, weakReference, finalBean);
                                failCallback(bean,resultBean.msg,listener,weakReference);
                            }
                        }
                        return;
                    }
                    ok = true;
                    System.out.print(String.valueOf(response.code()));
                    finalBean.timestemp = response.header("Last-Modified", "");
                    finalBean.object = bodyStr;//JSONObject.parseObject(bodyStr, tClass);
                    System.out.print(finalBean.timestemp);
//                    String result = response.body().string();
                    if (isCacheModel) {
                        //同时也存一份到文件里面
                        FileHelper.writeConfigToSDCard(model.urlEnd, JSON.toJSONString(finalBean));
                        final Context context = weakReference.get();
                        SPUtils.put(context,sharePreKey, JSON.toJSONString(finalBean));
                    }
//                    if (isNeedShare){
//                        //当数据需要保存到share时
//                        final Context context = weakReference.get();
//                        if (context != null) {
//                            if (context instanceof Activity) {
//                                SPUtils.put(context,"model.urlEnd","");
//                            }
//                        }
//
//                    }

                } else if (response.code() == 304) {
                    //说明文件没有更新，那就读取本地文件
                    ok = true;
                }
//              做回调处理
                callback(response, ok, listener, weakReference, finalBean);
            }
            public void onFailure(Call call, final IOException e) {
                if (listener != null) {
                    final Context context = weakReference.get();
                    if (context != null) {
                        if (context instanceof Activity) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {
//                                    在activity中在主线程中回调
                                    if (bean == null){
                                        listener.onFailure(new BaseJsonBean(),e.getMessage());
                                    }else{
                                        listener.onFailure(bean,e.getMessage());
                                    }
                                }
                            });
                        } else {
                            if (bean == null){
                                listener.onFailure(new BaseJsonBean(),e.getMessage());
                            }else{
                                listener.onFailure(bean,e.getMessage());
                            }
                        }
                    }
                }
            }
        });
    }

}