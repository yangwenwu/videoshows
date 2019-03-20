package com.lemon.fluttervideoshows;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gyf.barlibrary.ImmersionBar;
import com.lemon.fluttervideoshows.application.BaseApp;
import com.lemon.fluttervideoshows.http.common.Commrequest;
import com.lemon.fluttervideoshows.http.httpModel.BaseJsonBean;
import com.lemon.fluttervideoshows.http.httpModel.LoginModel;
import com.lemon.fluttervideoshows.http.httpModel.RegisterModel;
import com.lemon.fluttervideoshows.http.httpModel.UserBean;
import com.lemon.fluttervideoshows.http.https.ResponseListener;
import com.lemon.fluttervideoshows.utils.NetWorkUtil;
import com.lemon.fluttervideoshows.utils.SPUtils;
import com.lemon.fluttervideoshows.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.google.GooglePlus;
import cn.sharesdk.twitter.Twitter;

public class LoginAndRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView sign_up, sign_in;
    private ImageView sign_up_img, sign_in_img, back;

    private View register_layout, login_layout;
    //******登录*****
    private EditText register_email_account, register_email_psw;
    private TextView forget_psw, login_bt;
    private ImageButton fb_login_btn, tt_login_btn, gg_login_btn, wechat;
    //******注册*****
    private EditText email_account, email_psw, email_confirm_psw, code;
    private TextView send_code, create_account;

    private Platform platform;

    private FrameLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).keyboardEnable(true).init();
        setContentView(R.layout.login_and_register_layout);
        initView();
        showTriangle(1);
    }

    private void initView() {
        AssetManager mgr = getAssets();
        //根据路径得到Typeface
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Lato-Black.ttf");
        Typeface tf2 = Typeface.createFromAsset(mgr, "fonts/Lato-Regular.ttf");
        Typeface tf3 = Typeface.createFromAsset(mgr, "fonts/Lato-Bold.ttf");

        sign_up = (TextView) findViewById(R.id.sign_up);
        sign_in = (TextView) findViewById(R.id.sign_in);
        back = (ImageView) findViewById(R.id.back);
        sign_up_img = (ImageView) findViewById(R.id.sign_up_img);
        sign_in_img = (ImageView) findViewById(R.id.sign_in_img);

        register_layout = findViewById(R.id.register_layout);
        login_layout = findViewById(R.id.login_layout);

        sign_up.setTypeface(tf);
        sign_in.setTypeface(tf);

        //******登录*****
        email_account = (EditText) findViewById(R.id.email_account);
        email_account.setTypeface(tf2);
        email_psw = (EditText) findViewById(R.id.email_psw);
        email_psw.setTypeface(tf2);

        forget_psw = (TextView) findViewById(R.id.forget_psw);
        forget_psw.setTypeface(tf2);
        login_bt = (TextView) findViewById(R.id.login_bt);
        login_bt.setTypeface(tf3);

        forget_psw.setOnClickListener(this);
        login_bt.setOnClickListener(this);

        //fb_login_btn,tt_login_btn,gg_login_btn,wechat
        fb_login_btn = (ImageButton) findViewById(R.id.fb_login_btn);
        tt_login_btn = (ImageButton) findViewById(R.id.tt_login_btn);
        gg_login_btn = (ImageButton) findViewById(R.id.gg_login_btn);
        wechat = (ImageButton) findViewById(R.id.wechat);

        fb_login_btn.setOnClickListener(this);
        tt_login_btn.setOnClickListener(this);
        gg_login_btn.setOnClickListener(this);
        wechat.setOnClickListener(this);
        //*******end*****

        loading = (FrameLayout) findViewById(R.id.loading);

        //******注册*****
        register_email_account = (EditText) findViewById(R.id.register_email_account);
        register_email_account.setTypeface(tf2);
        register_email_psw = (EditText) findViewById(R.id.register_email_psw);
        register_email_psw.setTypeface(tf2);
        email_confirm_psw = (EditText) findViewById(R.id.email_confirm_psw);
        email_confirm_psw.setTypeface(tf2);
        code = (EditText) findViewById(R.id.code);
        code.setTypeface(tf2);

        send_code = (TextView) findViewById(R.id.send_code);
        send_code.setTypeface(tf2);
        create_account = (TextView) findViewById(R.id.create_account);
        create_account.setTypeface(tf3);

        send_code.setOnClickListener(this);
        create_account.setOnClickListener(this);
        //******end******

        sign_up.setOnClickListener(this);
        sign_in.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void showTriangle(int type) {
        if (type == 1) {
            sign_up_img.setVisibility(View.VISIBLE);
            sign_in_img.setVisibility(View.GONE);
            sign_up.setTextColor(Color.parseColor("#ffffff"));
            sign_in.setTextColor(Color.parseColor("#33ffffff"));
        } else if (type == 2) {
            sign_up_img.setVisibility(View.GONE);
            sign_in_img.setVisibility(View.VISIBLE);
            sign_up.setTextColor(Color.parseColor("#33ffffff"));
            sign_in.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up:
                closeKeyboard();
                register_layout.setVisibility(View.VISIBLE);
                login_layout.setVisibility(View.GONE);
                showTriangle(1);
                break;
            case R.id.sign_in:
                closeKeyboard();
                register_layout.setVisibility(View.GONE);
                login_layout.setVisibility(View.VISIBLE);
                showTriangle(2);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.forget_psw:
//                Intent intent = new Intent(LoginAndRegisterActivity.this, ForgotPswActivity.class);
//                startActivity(intent);
                break;
            case R.id.login_bt:
                String account = email_account.getText().toString();
                String psw = email_psw.getText().toString();
                if (account != null && !account.equals("")) {
                    if (isEmail(account)) {
                        if (psw != null && !psw.equals("")) {
                            goLogin(account, psw);
                        } else {
                            ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter your password");
                        }
                    } else {
                        ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter correct email");
                    }
                } else {
                    ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter your email");
                }
                break;
            case R.id.send_code:
                String email = register_email_account.getText().toString();
                if (email != null && !email.equals("")) {
                    if (isEmail(email)) {
                        sendEmailCode(email);
                    } else {
                        ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.enter_correct_email));
                    }
                }

                break;
            case R.id.create_account:
                String emailaddress = register_email_account.getText().toString();
                String email_pswStr = register_email_psw.getText().toString();
                String email_confirm_pswStr = email_confirm_psw.getText().toString();
                String codeStr = code.getText().toString();
                if (emailaddress != null && !emailaddress.equals("")) {
                    if (isEmail(emailaddress)) {
                        if (email_pswStr != null && !email_pswStr.equals("") && email_pswStr.length() >= 8 && email_pswStr.length() <= 16) {
                            if (email_confirm_pswStr != null && !email_confirm_pswStr.equals("") && email_pswStr.length() >= 8 && email_pswStr.length() <= 16) {
                                if (email_pswStr.equals(email_confirm_pswStr)) {

                                    if (codeStr != null && !codeStr.equals("")) {
                                        String verifycode = (String) SPUtils.get(LoginAndRegisterActivity.this, "verifycode", "");
                                        if (codeStr.equals(verifycode)) {
                                            createAccount(emailaddress, email_pswStr);
                                        } else {
                                            ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter correct verification code");
                                        }
                                    } else {
                                        ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter verification code");
                                    }
                                } else {
                                    ToastUtils.showShort(LoginAndRegisterActivity.this, "Passwords don’t match");
                                }
                            } else {
                                ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter password again");
                            }

                        } else {
                            ToastUtils.showShort(LoginAndRegisterActivity.this, "Please enter 8-16 characters password");
                        }

                    } else {
                        ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.enter_correct_email));
                    }
                }
                break;
            ////fb_login_btn,tt_login_btn,gg_login_btn,wechat
            case R.id.fb_login_btn:
                if (NetWorkUtil.isNetworkAvailable(this)){
                    thirdLogin("facebook");
                }else{
                    ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.net_error));
                }
                break;
            case R.id.tt_login_btn:
                if (NetWorkUtil.isNetworkAvailable(this)){
                    thirdLogin("twitter");
                }else{
                    ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.net_error));
                }
                break;
            case R.id.gg_login_btn:
                if (NetWorkUtil.isNetworkAvailable(this)){
                    thirdLogin("google");
                }else{
                    ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.net_error));
                }
                break;
            case R.id.wechat:
//                thirdLogin("wexin");
//                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }

    private void setLoadingVisible() {
        loading.setVisibility(View.VISIBLE);
        //forget_psw  login_bt  fb_login_btn,tt_login_btn,gg_login_btn,wechat
        forget_psw.setClickable(false);
        login_bt.setClickable(false);
        fb_login_btn.setClickable(false);
        tt_login_btn.setClickable(false);
        gg_login_btn.setClickable(false);
        wechat.setClickable(false);
    }

    private void setLoadingGone() {
        loading.setVisibility(View.GONE);
        forget_psw.setClickable(true);
        login_bt.setClickable(true);
        fb_login_btn.setClickable(true);
        tt_login_btn.setClickable(true);
        gg_login_btn.setClickable(true);
        wechat.setClickable(true);
    }

    private void thirdLogin(String type) {
        setLoadingVisible();
        if (type.equals("facebook")) {
            platform = ShareSDK.getPlatform(Facebook.NAME);
        } else if (type.equals("twitter")) {
            platform = ShareSDK.getPlatform(Twitter.NAME);
        } else if (type.equals("google")) {
            platform = ShareSDK.getPlatform(GooglePlus.NAME);
        }

        if (platform.isAuthValid()) {
            platform.removeAccount(true);
        }
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (platform.getName().equals(Facebook.NAME)) {
                    String openid = null;
                    String id = hashMap.get("id").toString();//ID
                    String name = hashMap.get("name").toString();//用户名
                    String third_party_id = hashMap.get("third_party_id").toString();//UID

                    HashMap<String, Object> picture = (HashMap<String, Object>) hashMap.get("picture");
                    String url = "http://graph.facebook.com/" + id + "/picture?type=large";
                    String str = "ID: " + id + "用户名： " + name + "描述：" + url + "用户头像地址：" + openid;
                    System.out.println("用户资料: " + str);
                    Log.d("Sharesdk", hashMap.toString());
                    System.out.println("facebook——调用第三方接口就OK");
//                    thirdPlatLogin("Facebook",third_party_id,name,url);
                    thirdPlatLogin("2", third_party_id, name, url);

                }
//                else if (platform.getName().equals(Wechat.NAME)) {
//                    String unionid = hashMap.get("unionid").toString();//ID
//                    String nickname = hashMap.get("nickname").toString();//用户名
//                    String headimgurl = hashMap.get("headimgurl").toString();//头像链接
//                    String openid = hashMap.get("openid").toString();//UID
//                    String str = "ID: " + unionid + "用户名： " + nickname + "描述：" + headimgurl + "用户头像地址：" + openid;
//                    System.out.println("用户资料: " + str);
//                    Log.d("Sharesdk", unionid);
//                    Log.d("Sharesdk", nickname);
//                    Log.d("Sharesdk", headimgurl);
//                    Log.d("Sharesdk", openid);
//                    thirdPlatLogin("1", openid, nickname, headimgurl);
//
//                }
                else if (platform.getName().equals(Twitter.NAME)) {
                    String nickname = hashMap.get("name").toString();//用户名
                    String headimgurl = hashMap.get("profile_image_url").toString();//头像链接
                    String imageurl = headimgurl.replace("normal", "400x400");
                    String openid = hashMap.get("id_str").toString();//UID
                    String str = "ID: " + ";\n" + "用户名： " + nickname + ";\n" + "描述：" + headimgurl + ";\n" + "用户头像地址：" + openid;
                    System.out.println("用户资料: " + str);
                    System.out.println("调用第三方接口就OK");
//                    thirdPlatLogin("Twitter",openid,nickname,imageurl);
                    thirdPlatLogin("3", openid, nickname, imageurl);

                } else if (platform.getName().equals(GooglePlus.NAME)) {
                    String nickname = hashMap.get("DisplayName").toString();//用户名
                    String headimgurl = hashMap.get("image").toString();//头像链接
                    String openid = hashMap.get("id").toString();//UID
                    //头像用这个
                    //https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg?sz=200
//                    thirdPlatLogin("Google",openid,nickname,headimgurl);
                    thirdPlatLogin("4", openid, nickname, headimgurl);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                setLoadingGone();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                setLoadingGone();
            }
        });
        platform.SSOSetting(false);
        platform.showUser(null);
    }

    //改为get
    private void thirdPlatLogin(String type, String uid, String nickName, String headImg) {
        Commrequest.thirdLogin(LoginAndRegisterActivity.this, type, uid, nickName, headImg, new ResponseListener() {
            @Override
            public void onResponse(BaseJsonBean t, int code) {
                //{"resCode":"500","resMsg":"error","resObject":null}
                JSONObject jsonObject = JSON.parseObject(t.object);
                String resMsg = jsonObject.getString("resMsg");
                if (resMsg.equals("success")) {
                    setLoadingGone();
                    JSONObject resObject = jsonObject.getJSONObject("resObject");
                    UserBean userBean = JSON.parseObject(resObject.toJSONString(), UserBean.class);
                    SPUtils.put(LoginAndRegisterActivity.this, "user", resObject.toJSONString());
                    BaseApp.setLogin(true);
                    BaseApp.setToken(userBean.token);
                    BaseApp.setUserBean(userBean);
                    sendLoginStateBroadcast();
//                    ToastUtils.showShort(LoginAndRegisterActivity.this,"登录成功");
                    finish();
                } else {
                    ToastUtils.showShort(LoginAndRegisterActivity.this, "Login failed");
                }
            }

            @Override
            public void onFailure(BaseJsonBean t, String errMessage) {
                ToastUtils.showShort(LoginAndRegisterActivity.this, "Login failed");
            }
        });
    }

    private void sendEmailCode(String email) {
        Commrequest.sendEmailCode(LoginAndRegisterActivity.this, email, new ResponseListener() {
            @Override
            public void onResponse(BaseJsonBean t, int code) {
                JSONObject jsonObject = JSON.parseObject(t.object);
                String resMsg = jsonObject.getString("resMsg");
                if (resMsg.equals("success")) {
                    JSONObject resObject = jsonObject.getJSONObject("resObject");
                    String verifycode = resObject.getString("code");
                    SPUtils.put(LoginAndRegisterActivity.this, "verifycode", verifycode);
                    ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.code_send_success));
                } else {
                    ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.code_send_failed));
                }
            }

            @Override
            public void onFailure(BaseJsonBean t, String errMessage) {
                ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.code_send_failed));
            }
        });
    }

    /**
     * 注册
     *
     * @param email
     * @param psw
     */
    private void createAccount(String email, String psw) {
        RegisterModel model = new RegisterModel();
        model.account = email;
        model.password = psw;
        Commrequest.register(LoginAndRegisterActivity.this, model, new ResponseListener() {
            @Override
            public void onResponse(BaseJsonBean t, int code) {
                //{"resCode":"1001","resMsg":"邮箱已注册","resObject":null}
                JSONObject jsonObject = JSON.parseObject(t.object);
                String resCode = jsonObject.getString("resCode");
                if (resCode.equals("1001")) {
                    String resMsg = jsonObject.getString("resMsg");
                    ToastUtils.showShort(LoginAndRegisterActivity.this, resMsg);
                } else if (resCode.equals("200")) {
                    //保存用户信息
                    //{"resCode":"200","resMsg":"success","resObject":
                    // {"id":"d63d251b-b7b1-11e7-9872-00155d03d036","nickName":"124976507@qq.com","token":"624BAF3F264F48129CA87309D556D57D4BB8A5B2","headImage":null,"account":"124976507@qq.com"}}
                    JSONObject resObject = jsonObject.getJSONObject("resObject");
                    UserBean userBean = JSON.parseObject(resObject.toJSONString(), UserBean.class);
                    SPUtils.put(LoginAndRegisterActivity.this, "user", resObject.toJSONString());
                    BaseApp.setLogin(true);
                    BaseApp.setToken(userBean.token);
                    BaseApp.setUserBean(userBean);
                    sendLoginStateBroadcast();
                    finish();
                }
            }

            @Override
            public void onFailure(BaseJsonBean t, String errMessage) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isEmail(String eMAIL1) {
        Pattern pattern = Pattern
                .compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        Matcher mc = pattern.matcher(eMAIL1);
        return mc.matches();
    }

    private void goLogin(String email, String psw) {
        LoginModel model = new LoginModel();
        model.account = email;
        model.password = psw;
        Commrequest.login(LoginAndRegisterActivity.this, model, new ResponseListener() {
            @Override
            public void onResponse(BaseJsonBean t, int code) {

                JSONObject jsonObject = JSON.parseObject(t.object);
                String resCode = jsonObject.getString("resCode");
                if (resCode.equals("1002")) {
                    String resMsg = jsonObject.getString("resMsg");
                    ToastUtils.showShort(LoginAndRegisterActivity.this, resMsg);
                } else {
                    JSONObject resObject = jsonObject.getJSONObject("resObject");
                    UserBean userBean = JSON.parseObject(resObject.toJSONString(), UserBean.class);
                    SPUtils.put(LoginAndRegisterActivity.this, "user", resObject.toJSONString());
                    BaseApp.setLogin(true);
                    BaseApp.setToken(userBean.token);
                    BaseApp.setUserBean(userBean);
                    sendLoginStateBroadcast();
                    finish();
                }
            }

            @Override
            public void onFailure(BaseJsonBean t, String errMessage) {
                ToastUtils.showShort(LoginAndRegisterActivity.this, getResources().getString(R.string.net_error));
            }
        });
    }

    //登录成功发送广播
    private void sendLoginStateBroadcast() {
//        Intent it = new Intent(MineFragment.LOGIN_NOTICE);
//        sendBroadcast(it);
    }

//    private UMAuthListener umAuthListener = new UMAuthListener() {
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//            //授权开始的回调
//            //显示进度框
//            setLoadingVisible();
//        }
//
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
////            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
//            String uid = data.get("uid");
//            String name = data.get("name");
//            String img = data.get("iconurl");
//            String bindType = null;
//            if (platform.name().equals("QQ")) {
//                bindType = "qqId";
//            } else if (platform.name().equals("WEIXIN")) {
//                bindType = "weixinId";
//            } else if (platform.name().equals("SINA")) {
//                bindType = "weiboId";
//            }
//
//            thirdPlatLogin("1", uid, name, img);
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            setLoadingGone();
//            Toast.makeText(getApplicationContext(), "Authorization failure", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//            setLoadingGone();
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void closeKeyboard(){
        View view = getWindow().peekDecorView();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
