# XLog


轻量级的log工具类,可以输出文件名,函数名,行号,也可以输出json格式字符串.


日志级别 'v' and 'd' 只应该出现在你的debug代码中.

日志级别 'i','w' and 'e' 可以出现在release代码中.

setDebug(false) 不会过滤 'i','w'和'e', 如果你不想输出任何log请调用 setClose(true).

setFilterTag("logtest"),传入的字符串会显示在日志面板的tag中,便于过滤日志,默认为'xlog'.

setSaveToFile("XLog"),日志保存在sd卡根目录指定的文件夹中.


![image](https://raw.githubusercontent.com/qbaowei/XLog/master/screenshots/s1.jpg)


# Download


Gradle:


compile 'com.qbw.log:xlog:1.2.0'


# Author:


qbaowei@qq.com

