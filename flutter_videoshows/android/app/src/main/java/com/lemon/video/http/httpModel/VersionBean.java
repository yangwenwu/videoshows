package com.lemon.video.http.httpModel;

import java.io.Serializable;

/**
 * Created by Staff on 2017/10/23.
 */

public class VersionBean implements Serializable {
    public String id;
    public String versionPackage;
    public String versionCode;
    public String versionName;
    public String versionDesc;
    public String type;
    public String version_path;
    public String apkPath;      //下载地址
    public String versionDate;
    public String updateType;  //更新方式


}
