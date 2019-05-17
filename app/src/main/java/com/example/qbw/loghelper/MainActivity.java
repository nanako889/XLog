package com.example.qbw.loghelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.qbw.log.XLog;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    XLog mXLog=new XLog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String logDir = getExternalFilesDir("log").getAbsolutePath();
        Log.e("-----", logDir);
        mXLog.setEnabled(true, logDir);
        mXLog.setFilterTag("[logtest]");
        mXLog.d(null);
        mXLog.v("1111");
        XLog.LOGGER.setEnabled(true);
        XLog.LOGGER.w("oh yeah");
    }

    void line1() {
        mXLog.line(true);
        mXLog.i("content");
        mXLog.line(false);

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            mXLog.w(e);
            mXLog.e(e);
        }
        Map<String, String> map = new HashMap<>();
        map.put("key", "haha");
        map.put("key1", "haha1");
        mXLog.urlE("http://www.baidu.com", map);
        mXLog.urlD("http://www.baidu.com", map);
        mXLog.urlI("http://www.baidu.com", map);
        mXLog.urlW("http://www.baidu.com", map);
        mXLog.urlV("http://www.baidu.com", map);
    }

    void infor() {
        mXLog.i("Hello XLog!");
    }

    void warn() {
        mXLog.w("list size is empth");
    }

    void error() {
        mXLog.e("wrong position[%d]", -1);
    }

    int j;

    void json1() {

        mXLog.jsonV("{\"code\":1,\"id\":2,\"age\":23,\"data\":[{\"code\":1},{\"code\":2}]}");
        mXLog.jsonV("[{\"code\":1},{\"code\":2}]");
        mXLog.jsonE("{\"code\":1,\"id\":2,\"age\":23}");
        mXLog.jsonD("¹þ¹þ{\"code\":1}");
        mXLog.jsonI("{\"code\":1}ÎûÎû");
        mXLog.jsonW("¹þ¹þ{\"code\":1}ÎûÎû");
        mXLog.jsonD("¹þ¹þ[{\"code\":1},{\"code\":2}]");
        mXLog.jsonI("[{\"code\":1},{\"code\":2}]ÎûÎû");
        mXLog.jsonW("¹þ¹þ[{\"code\":1},{\"code\":2}]ÎûÎû");

    }
}
