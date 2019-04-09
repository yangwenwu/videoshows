package com.lemon.video.utils;

/**
 * Created by Staff on 2017/5/30.
 */

public class StringUrlUtil {

    /**
     * 去掉开头的“/”分隔符
     * @param jsonUrl
     * @return
     */
    public static String checkSeparator(String jsonUrl){
        String url = null;
        if (jsonUrl.startsWith("/")){
            url = jsonUrl.substring(1,jsonUrl.length());
        }else{
            url = jsonUrl;
        }
        return url;
    }

    //文件名
    /**
     *
     * @param jsonUrl  源路径 比如 "article/1/2/3/132982e.json";
     * @return  返回文件名
     */
    public static String getFileName(String jsonUrl){
        //"article/1/2/3/132982e.json";
        String[] strs = jsonUrl.split("/");
        String fileName =  strs[strs.length-1];
        //fileName = 132982e.json
        return  fileName;
    }


    /**
     *  获取文件的目录 ，开头做了去“/”处理
     * @param jsonUrl
     * @return
     */
    public static String getFilePath(String jsonUrl){
        //"article/1/2/3/132982e.json";
        String[] strs = jsonUrl.split("/");
        //要得到最后的文件名132982e.json
        //fileName = 132982e.json
        String fileName =  strs[strs.length-1];
        //然后再取前面的目录
        //dir = article/1/2/3
        String dir= jsonUrl.replace("/"+fileName,"");
        return checkSeparator(dir);
    }

    //这个是文件路径上一级目录
    /**
     *   获取文件的路径的上一级目录，开头处做了去“/”处理
     * @param jsonUrl
     * @return
     */
    public static String getUpFilePath(String jsonUrl){
        String upDir = jsonUrl.substring(0, jsonUrl.lastIndexOf("/"));
        return checkSeparator(upDir.substring(0, upDir.lastIndexOf("/")));
    }


    public static int strNoToInt(String versionCode){
        int code = 1;
        String str =  versionCode.replace(".","");
        if (str.length() == 3){
            code = Integer.parseInt(str);
            return  code;
        }else if (str.length() < 3){
            str = str +"0";
            code = Integer.parseInt(str);
            return  code;
        }else{
            return  code;
        }
    }
}
