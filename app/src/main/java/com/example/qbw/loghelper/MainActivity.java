package com.example.qbw.loghelper;

import android.app.Activity;
import android.os.Bundle;

import com.qbw.log.XLog;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //XLog.setClose(true);//close all log
        XLog.setDebug(true);//only close 'v','d'
        XLog.setFilterTag("logtest");
        XLog.setSaveToFile("XLog");
    }

    @OnClick(R.id.l_btn)
    void line1() {
        XLog.line(true);
        XLog.i("content");
        XLog.line(false);
    }

    @OnClick(R.id.i_btn)
    void infor() {
        XLog.i("Hello XLog!");
    }

    @OnClick(R.id.w_btn)
    void warn() {
        XLog.w("list size is empth");
    }

    @OnClick(R.id.e_btn)
    void error() {
        XLog.e("wrong position[%d]", -1);
    }

    int j;
    @OnClick(R.id.json_btn)
    void json1() {
        for (int i = 0; i < 5; i++) {
            new Thread(){
                @Override
                public void run() {
                    XLog.json("you can set http url and param here", String.format("{code:%d,msg:'success'}", j++));
                }
            }.start();
        }
    }
}
