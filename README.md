# XLog


轻量级的log工具类,可以输出文件名,函数名,行号,也可以输出json格式字符串.


Log level 'v' and 'd' 应当只出现在你调试代码的时候出现.

Log level 'i','w' and 'e' 可以出现在发布的正式程序中.

'setDebug(false)' 不会过滤 'i', 'w', 'e'.如果你不想输出任何log请调用 'setClose(true)';


![image](https://github.com/qbwjly/XLog/raw/master/screenshots/s1.png)


# Download


Gradle:


compile 'com.qbw.log:xlog:1.1.1'


# Author:


qbaowei@qq.com

