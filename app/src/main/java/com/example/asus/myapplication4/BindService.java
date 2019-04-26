package com.example.asus.myapplication4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;



public class BindService extends Service {
    private int count;
    private boolean quit;
    // 定义onBind方法返回的对象
    private MyBinder myBinder = new MyBinder();
    public class MyBinder extends Binder {
        public int getCount() {
            // 获取Service的运行状态：count
            return count;
        }
    }

    // 必须实现的方法，绑定Service时回调该方法
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded");
        return myBinder;
    }

    // Service创建时回调该方法


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is onCreate");
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (!quit) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }.start();
    }

    // Service 被断开连接时回调该方法
    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service is onUnbind");
        return true;
    }

    // Service 被关闭之前回调该方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        System.out.println("Service is onDestroy");
    }
}