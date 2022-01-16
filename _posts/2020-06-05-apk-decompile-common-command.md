---
layout: post
title: Apk反编译常用工具和方法
categories: Apk反编译
description: some word here
keywords: Apk反编译
---

这篇文章主要介绍Apk反编译常用工具和方法总结

## 简介

> 直接修改APK的后缀名，改成xxx.zip，解压能够获取一些png或者jpg的位图资源，如果是xml资源，打开是乱码，并且java代码也无法看到，因为都被打包到class.dex文件中。
>
> 通过一些工具进行反编译，可以查看java代码、xml资源和图片资源，以下是几种常用的工具和方法
>

## 工具和使用方法

### 1.apktool

工具介绍：apktool反编译APK获得图片与XML资源

工具地址：[https://github.com/iBotPeaches/Apktool](https://github.com/iBotPeaches/Apktool)

```
使用：需进入到apktool文件夹内
如果只有一个apktool.jar,则使用命令：java -jar apktool.jar d -f base.apk
如果有.bat .jar两个文件,则使用命令：apktool d -f base.apk
```

### 2.dex2jar

工具介绍：dex2jar将classes.dex转换成jar文件

工具地址：[https://github.com/pxb1988/dex2jar](https://github.com/pxb1988/dex2jar)

```
使用：需进入到dex2jar文件夹内
apk解压后的classes.dex转jar包，cmd中执行命令：d2j-dex2jar.bat classes.dex
apk中的classes.dex中提取jar包，cmd中执行命令：d2j-dex2jar.bat -f base.apk
```

### 3.jd-gui

工具介绍：jd-gui查看jar包中的Java代码

工具地址：[https://github.com/java-decompiler/jd-gui](https://github.com/java-decompiler/jd-gui)

```
使用：可直接打开jar包文件
```

### 4.jadx

工具介绍：jadx查看apk、jar包中的Java代码

工具地址：[https://github.com/skylot/jadx](https://github.com/skylot/jadx)

```
使用：直接运行jadx-gui.bat,然后打开apk或jar包即可
```

### 5.Apk Extractor

工具介绍：Apk Extractor-Apk提取手机工具

工具地址：网上直接搜索下载

### 6.Root Explorer

工具介绍：Root Explorer-文件查看手机工具

工具地址：网上直接搜索下载