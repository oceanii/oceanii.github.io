---
layout: post
title: adb常用命令
categories: 常用命令
description: some word here
keywords: 常用命令, adb
---

adb常用命令总结

## 常用命令说明

> dumpsys命令：侧重于显示信息
>
> pm命令：侧重于安装包的信息查看与处理
>
> am命令：侧重于与app的直接交互，不常用
>
> 以下是最常用的adb命令，其他更多的adb命令详见网页：android adb常用命令收集

## 常用命令

```
adb devices  
--显示手机与电脑是否连接成功，如果成功，会显示端口号
--如果第一次一直连不上，可以安装豌豆荚或360手机调试助手，会自动配置手机和电脑的连接

adb shell
--进入命令行模式

adb install f:/imageprocess.apk
--电脑上的apk直接安装在手机上

adb shell dumpsys activity | grep(findstr) "mResumedActivity"
--显示当前手机窗口上的App包名和Activity名称

adb shell pm list packages
--列出安装的所有应用包名

adb pull sdcard/test/. f:/tmp/
adb pull storage/emulated/0/test/. f:/tmp/
--内存卡中test目录下的所有文件pull到电脑的F盘中的tmp文件夹

adb shell dumpsys meminfo com.example.demo
--查看app运行时的内存信息
```

## 常用命令组合

### 1.获取手机中的软件安装包和应用数据

```
1.adb shell dumpsys activity | grep(findstr) "mResumedActivity"
  adb shell dumpays activity top
  --查看当前包名和页面的Activity名称
2.adb shell pm path com.tencent.qq
  --找到该包名的apk位置
3.adb pull data/app/com.example.demo-1/base.apk f:/apkDir/ 
  --将手机中的apk文件pull到电脑上
4.adb pull data/data/com.example.demo/files/ f:/apkDir/
  --将手机中的app数据pull到电脑上
```

### 2.替换手机中已安装好的apk的so文件

```
1.adb shell
  --确保手机已经root
2.cd data/app
  --找到apk的包名
3.adb push f:/cjson.so /data/app/com.example.demo/lib/arm
  --将so文件push到apk中，替换原有的同名so文件
4.替换时如果遇到只读权限错误，则执行：
  (1) adb reboot remount
  (2) adb remount
```

## 打印log常用命令

```
adb logcat  打印应用所有log，无过滤
adb logcat -s TAG  过滤出所有标签是TAG的log
adb logcat *:E  Android SDK用来查看调试信息的
adb logcat -s AndroidRuntime > f:/log.txt  打印运行时crash的log并输出到文本
adb logcat | grep _oceanii  过滤_image字符串的log
adb logcat | grep -e _oceanii -e _video  过滤多个字符串的log
adb logcat | grep -i _oceanii  过滤_oceanii字符串的log，并忽略大小写
adb logcat -s TAG | grep _oceanii   过滤TAG以及包含_oceanii字符串的log
```

## 查看so信息常用命令

```
readelf -d f:/cjson.so  查看当前so文件依赖了哪些so
nm -D cjson.so > listfunc.txt  列出so的所有函数以及地址输出到文件中
objdump -tT cjson.so > listfunc.txt  列出so的所有函数以及地址输出到文件中
```