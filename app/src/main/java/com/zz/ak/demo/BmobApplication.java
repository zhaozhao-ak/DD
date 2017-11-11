package com.zz.ak.demo;

import android.app.Application;

import com.zz.ak.demo.bean.PersonMsg;
import com.zz.ak.demo.bean._User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class BmobApplication extends Application {
    /**
     * SDK初始化放到Application中
     */
    public static String APPID = "8b12dcc162977b2aab1b79f510b67e4c";
    public static List<_User> personList = new ArrayList<>();
    public static List<PersonMsg> personMsgList = new ArrayList<>();
    public static BmobUser myUser;
    public static _User UserMsg;
    public static int UpNum = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        //提供以下两种方式进行初始化操作：
//		//第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
//		BmobConfig config =new BmobConfig.Builder(this)
//		//设置appkey
//		.setApplicationId(APPID)
//		//请求超时时间（单位为秒）：默认15s
//		.setConnectTimeout(30)
//		//文件分片上传时每片的大小（单位字节），默认512*1024
//		.setUploadBlockSize(1024*1024)
//		//文件的过期时间(单位为秒)：默认1800s
//		.setFileExpiration(5500)
//		.build();
//		Bmob.initialize(config);
        //第二：默认初始化
        Bmob.initialize(this, APPID);
    }

    public void getAllPerson(){


    }


}