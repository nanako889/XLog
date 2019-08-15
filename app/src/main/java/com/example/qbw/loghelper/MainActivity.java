package com.example.qbw.loghelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.qbw.log.L;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    L mL = new L();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String logDir = getExternalFilesDir("log").getAbsolutePath();
        Log.e("-----", logDir);
        mL.setEnabled(true, logDir);
        mL.setFilterTag("[logtest]");
        mL.d(null);
        mL.v("1111");
        L.GL.setEnabled(true);
        L.GL.w("oh yeah");
    }

    void line1() {
        mL.line(true);
        mL.i("content");
        mL.line(false);

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            mL.w(e);
            mL.e(e);
        }
        Map<String, String> map = new HashMap<>();
        map.put("key", "haha");
        map.put("key1", "haha1");
        mL.urlE("http://www.baidu.com", map);
        mL.urlD("http://www.baidu.com", map);
        mL.urlI("http://www.baidu.com", map);
        mL.urlW("http://www.baidu.com", map);
        mL.urlV("http://www.baidu.com", map);
    }

    void infor() {
        mL.i("Hello L!");
    }

    void warn() {
        mL.w("list size is empth");
    }

    void error() {
        mL.e("wrong position[%d]", -1);
    }

    int j;

    void json1() {

        mL.jsonV("{\"code\":1,\"id\":2,\"age\":23,\"data\":[{\"code\":1},{\"code\":2}]}");
        mL.jsonV("[{\"code\":1},{\"code\":2}]");
        mL.jsonE("{\"code\":1,\"id\":2,\"age\":23}");
        mL.jsonD("����{\"code\":1}");
        mL.jsonI("{\"code\":1}����");
        mL.jsonW("����{\"code\":1}����");
        mL.jsonD("����[{\"code\":1},{\"code\":2}]");
        mL.jsonI("[{\"code\":1},{\"code\":2}]����");
        mL.jsonW("����[{\"code\":1},{\"code\":2}]����");

    }
}
