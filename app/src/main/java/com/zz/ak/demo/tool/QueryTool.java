package com.zz.ak.demo.tool;

import android.content.Context;
import android.util.Log;

import com.zz.ak.demo.BmobApplication;
import com.zz.ak.demo.bean.PersonMsg;
import com.zz.ak.demo.bean._User;
import com.zz.ak.demo.interfaceview.TimeInterface;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/5.
 */

public class QueryTool {

    public static String TAG = "zhao--QueryTool";

    private Context context;
    private TimeInterface timeInterface;
    public QueryTool(Context mContext, TimeInterface timeInterface) {
        this.context = mContext;
        this.timeInterface = timeInterface;
    }

    /**
     * 根据表名查询多条数据
     * 表：Person  更具最新更新时间
     */
    public void queryAllPerson(){
        System.out.println("zhao--yyyyyyyyyyyy------");
        final BmobQuery<_User> bmobQuery = new BmobQuery<_User>();
        bmobQuery.order("updatedAt");
        //先判断是否有缓存
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//		observable形式
        bmobQuery.findObjectsObservable(_User.class)
                .subscribe(new Subscriber<List<_User>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loge(e);
                        timeInterface.getNewDataError();
                    }
                    @Override
                    public void onNext(List<_User> user) {
                        log("zhao-----查询Person成功：共"+user.size()+"条数据。");
                        BmobApplication application = new BmobApplication();
                        application.personList = user;
                        System.out.println("zhao--666------");
                        for (_User msg :user){
                            String p = msg.getObjectId().toString().trim();
                            String U = application.myUser.getObjectId().toString().trim();
                            if (p.equals(U)){
                                application.UserMsg = msg;
                            }
                        }
                        timeInterface.getNewData();
                    }
                });
    }


    /**
     * 根据表名查询多条数据
     * 表：PersonMsg  更具最新更新时间
     * 第一次初始化
     */
    public void queryAllPersonMsg(){
        final BmobQuery<PersonMsg> bmobQuery = new BmobQuery<PersonMsg>();
        bmobQuery.order("-updatedAt");
        bmobQuery.setLimit(50);
        //先判断是否有缓存
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//		observable形式
        bmobQuery.findObjectsObservable(PersonMsg.class)
                .subscribe(new Subscriber<List<PersonMsg>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loge(e);
                    }
                    @Override
                    public void onNext(List<PersonMsg> personMsgs) {
                        log("zhao-----查询PersonMsg成功：共"+personMsgs.size()+"条数据。");
                        BmobApplication application = new BmobApplication();
                        application.personMsgList = personMsgs;
                    }
                });
    }


    /**
     * 根据表名查询多条数据
     * 表：PersonMsg  更具最新更新时间
     * 接口回掉
     */
    public void queryAllPersonMsgUp(){
        final BmobQuery<PersonMsg> bmobQuery = new BmobQuery<PersonMsg>();
        bmobQuery.order("-updatedAt");
        //先判断是否有缓存
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//		observable形式
        bmobQuery.findObjectsObservable(PersonMsg.class)
                .subscribe(new Subscriber<List<PersonMsg>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loge(e);
                        timeInterface.getNewDataError();
                    }
                    @Override
                    public void onNext(List<PersonMsg> personMsgs) {
                        log("zhao-----查询PersonMsg成功：共"+personMsgs.size()+"条数据。");
                        BmobApplication application = new BmobApplication();
                        application.personMsgList = personMsgs;
                        timeInterface.getNewData();
                    }
                });
    }


    /**
     * 获取所有信息
     */
    public void queryAllM(){

        final BmobQuery<_User> bmobQuery = new BmobQuery<_User>();
        bmobQuery.order("updatedAt");
        //先判断是否有缓存
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//		observable形式
        bmobQuery.findObjectsObservable(_User.class)
                .subscribe(new Subscriber<List<_User>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loge(e);
                        timeInterface.getNewDataError();
                    }
                    @Override
                    public void onNext(List<_User> user) {
                        log("zhao-----查询Person成功：共"+user.size()+"条数据。");
                        BmobApplication application = new BmobApplication();
                        application.personList = user;
                        System.out.println("zhao--666------");
                    }
                });

        final BmobQuery<PersonMsg> bmobQuery1 = new BmobQuery<PersonMsg>();
        bmobQuery1.order("-updatedAt");
        //先判断是否有缓存
        bmobQuery1.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
//		observable形式
        bmobQuery1.findObjectsObservable(PersonMsg.class)
                .subscribe(new Subscriber<List<PersonMsg>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loge(e);
                        timeInterface.getNewDataError();
                    }
                    @Override
                    public void onNext(List<PersonMsg> personMsgs) {
                        log("zhao-----查询PersonMsg成功：共"+personMsgs.size()+"条数据。");
                        BmobApplication application = new BmobApplication();
                        application.personMsgList = personMsgs;
                    }
                });
        timeInterface.getNewData();
    }





    public  void log(String msg) {
        Log.i(TAG, "===============================================================================");
        Log.i(TAG, msg);
    }

    public  void loge(Throwable e) {
        Log.i(TAG, "===============================================================================");
        if (e instanceof BmobException) {
            Log.e(TAG, "错误码：" + ((BmobException) e).getErrorCode() + ",错误描述：" + ((BmobException) e).getMessage());
        } else {
            Log.e(TAG, "错误描述：" + e.getMessage());
        }
    }
}
