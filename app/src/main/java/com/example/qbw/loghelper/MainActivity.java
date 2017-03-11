package com.example.qbw.loghelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.qbw.log.XLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String logDir = getExternalFilesDir("log").getAbsolutePath();
        Log.e("-----", logDir);
        XLog.setEnabled(true, logDir);
        XLog.setFilterTag("logtest");
        XLog.d(null);
    }

    @OnClick(R.id.l_btn)
    void line1() {
        XLog.line(true);
        XLog.i("content");
        XLog.line(false);

        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            XLog.w(e);
            XLog.e(e);
        }
        Map<String, String> map = new HashMap<>();
        map.put("key", "haha");
        map.put("key1", "haha1");
        XLog.url("http://www.baidu.com", map);
        XLog.urlE("http://www.baidu.com", map);
        XLog.urlD("http://www.baidu.com", map);
        XLog.urlI("http://www.baidu.com", map);
        XLog.urlW("http://www.baidu.com", map);
        XLog.urlV("http://www.baidu.com", map);
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

        XLog.jsonV("{\"code\":1,\"id\":2,\"age\":23,\"data\":[{\"code\":1},{\"code\":2}]}");
        XLog.jsonV("[{\"code\":1},{\"code\":2}]");
        XLog.jsonE("{\"code\":1,\"id\":2,\"age\":23}");

    }
}
