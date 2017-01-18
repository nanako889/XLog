package com.qbw.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author QBW
 * @createtime 2016/04/05 15:04
 */


public class XLog {

    private static boolean sEnabled = false;

    private static String sLogDirPath;

    private static String sFilterTag = "xlog";

    private static final int JSON_INDENT = 4;

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static boolean isEnabled() {
        return sEnabled;
    }

    public static void setEnabled(boolean enabled) {
        setEnabled(enabled, "");
    }

    public static void setEnabled(boolean enabled, String logDirPath) {
        sEnabled = enabled;
        sLogDirPath = logDirPath;
    }

    public static void setFilterTag(String filterTag) {
        XLog.sFilterTag = filterTag;
    }

    public static void v(String logFormat, Object... logParam) {
        l('v', logFormat, logParam);
    }

    public static void d(String logFormat, Object... logParam) {
        l('d', logFormat, logParam);
    }

    public static void i(String logFormat, Object... logParam) {
        l('i', logFormat, logParam);
    }

    public static void w(String logFormat, Object... logParam) {
        l('w', logFormat, logParam);
    }

    public static void w(Throwable e) {
        if (null != e) {
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message)) {
                l('w', message);
            }
        }
    }

    public static void e(String logFormat, Object... logParam) {
        l('e', logFormat, logParam);
    }

    public static void e(Throwable e) {
        if (null != e) {
            String message = e.getMessage();
            if (!TextUtils.isEmpty(message)) {
                l('e', message);
            }
        }
    }

    private static void l(char type, String logFormat, Object... logParam) {
        try {
            boolean isWriteToFile = !TextUtils.isEmpty(sLogDirPath);
            if (sEnabled || isWriteToFile) {
                String log = String.format(logFormat, logParam);
                String[] logs = createLog(log);
                if (sEnabled) {
                    log(type, logs[0], logs[1]);
                }
                if (isWriteToFile) {
                    writeToFile(logs[0], logs[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * when log is too long,split it
     *
     * @param level
     * @param tag
     * @param text
     */
    private static void log(char level, String tag, String text) {
        final int PART_LEN = 3000;

        do {
            int clipLen = text.length() > PART_LEN ? PART_LEN : text.length();
            String clipText = text.substring(0, clipLen);
            text = clipText.length() == text.length() ? "" : text.substring(clipLen);
            switch (level) {
                case 'i':
                    Log.i(tag, clipText);
                    break;
                case 'd':
                    Log.d(tag, clipText);
                    break;
                case 'w':
                    Log.w(tag, clipText);
                    break;
                case 'v':
                    Log.v(tag, clipText);
                    break;
                case 'e':
                    Log.e(tag, clipText);
                    break;
                default:
                    break;
            }
        } while (text.length() > 0);
    }

    /**
     * @param header you can set this value your http param
     * @param msg    json format string
     */
    public synchronized static void json(final String header, final String msg) {
        if (!sEnabled) {
            return;
        }

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        l('d',
          "╔═══════════════════════════════════════════════════════════════════════════════════════");
        message = header + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        StringBuffer linesBuffer = new StringBuffer();
        linesBuffer.append(LINE_SEPARATOR);
        for (String line : lines) {
            l('d', "║ " + line);
        }
        l('d',
          "╚═══════════════════════════════════════════════════════════════════════════════════════");
    }

    /**
     * 打印完整url
     *
     * @param url
     * @param mapParam
     */
    public static void url(String url, Map<String, String> mapParam) {
        if (!sEnabled) {
            return;
        }
        l('d', urlLog(url, mapParam));
    }

    public static void urlE(String url, Map<String, String> mapParam) {
        if (!sEnabled) {
            return;
        }
        l('e', urlLog(url, mapParam));
    }

    public static String urlLog(String url, Map<String, String> mapParam) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url + "?");
        if (null != mapParam && !mapParam.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = mapParam.entrySet();
            for (Map.Entry entry : entrySet) {
                stringBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
            }

        }
        url = stringBuilder.toString();
        return url.substring(0, url.length() - 1);
    }

    public static void line(boolean top) {
        if (top) {
            l('v',
              "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            l('v',
              "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }


    private static String[] createLog(String log) {
        if (null == log) {
            log = "";
        }
        String tag = sEnabled ? getFileNameMethodLineNumber(6) : "";
        if (null == tag) {
            tag = "";
        }

        tag = "[" + sFilterTag + "]" + tag;

        return new String[]{tag, log};
    }

    /**
     * @param tag
     * @param msg
     */
    private static void writeToFile(String tag, String msg) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Log.e("", "no external storage!!!");
            return;
        }

        Date date = Calendar.getInstance().getTime();
        final String logName = String.format("%1$04d%2$02d%3$02d.txt",
                                             date.getYear() + 1900,
                                             date.getMonth() + 1,
                                             date.getDate());
        File fLogDir = new File(sLogDirPath);
        if (!fLogDir.exists()) {
            if (!fLogDir.mkdirs()) {
                Log.e("", "create dir[" + sLogDirPath + "]failed!!!");
                return;
            }
        }

        try {
            File f = new File(sLogDirPath + File.separator + logName);
            if (!f.exists()) {
                if (!f.createNewFile()) {
                    Log.e("", "create file failed");
                    return;
                }
            }
            FileOutputStream fout = new FileOutputStream(f, true);
            OutputStreamWriter swriter = new OutputStreamWriter(fout);
            BufferedWriter bwriter = new BufferedWriter(swriter);
            bwriter.write(String.format("[%1$02d:%2$02d:%3$02d]%4$50s:%5$s\n",
                                        date.getHours(),
                                        date.getMinutes(),
                                        date.getSeconds(),
                                        tag,
                                        msg));
            bwriter.flush();
            bwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("exception", e.getMessage());
        }

    }

    /**
     * @param depth 2,the method it self;3,the method who call this method
     * @return filename + method name + line number
     */
    private static String getFileNameMethodLineNumber(int depth) {
        String info = new String("");
        try {
            StackTraceElement e = Thread.currentThread().getStackTrace()[depth];
            info = String.format("[%1$s,%2$s,%3$s]",
                                 e.getFileName(),
                                 e.getMethodName(),
                                 e.getLineNumber());
        } catch (Exception e) {
            Log.e("log", "get stack trace element failed!!!");
        }
        return info;
    }
}
