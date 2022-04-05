---
layout: wiki
title: AndroidStudio版本配置和常用快捷键
cate1: 软件快捷键
cate2:
description: AndroidStudio常用快捷键和使用技巧汇总
keywords: 版本配置, 常用快捷键, AndroidStudio
---

## 前言

> AndroidStudio版本、Gradle插件版本、Gradle版本、CMake版本、NDK版本有一定的对应关系，需要配置正确才能省去不少麻烦
>
> 多个按键的组合的快捷键可以实现某些快速操作，在大型的IDE中，熟练使用快捷键会极大的提高开发效率

## 版本配置

以下是个人最常用的两个版本配置，是能够正常运行的，可以在一台电脑上同时存在两个版本，只需要配置不同的SDK路径即可。

- AndroidStudio版本下载地址：

https://developer.android.com/studio/archive?hl=zh-cn

- Gradle插件（工程的build.gradle中设置版本自动下载）

- Gradle版本下载地址（工程的gradle-wrapper.properties中设置版本自动下载，如下载失败需要手动下载）：

 https://services.gradle.org/distributions/

- CMake（AS的SDK Manager中选取对应版本下载）

- NDK版本下载地址（AS的SDK Manager中选取对应版本下载，也可以手动下载）：

https://developer.android.com/ndk/downloads/revision_history?hl=zh-cn

第1个版本配置：

> AndroidStudio：3.0.1
>
> Gradle插件：3.0.1
>
> Gradle：gradle-4.1-all
>
> CMake：3.6.4111459
>
> NDK：16.1.4479499、18.1.5063045、19.2.5345600（三个版本都行）

第2个版本配置：

> AndroidStudio：Arctic Fox（2020.3.1）
>
> Gradle插件：4.1.3
>
> Gradle：gradle-7.0.2-bin
>
> CMake：3.10.2.4988404
>
> NDK：23.1.7779620  

## 快捷键设置

```
1.设置自动补全(改为Eclipse快捷键)
  --打开settings，搜索basic改成alt+/，搜索cyclic改成ctrl+space
2.设置自动导包
  --打开settings，搜索Auto Import，全部打勾
```

## 查找快捷键

```
Ctrl+F  查找当前文件中的文本
Ctrl+Shift+F  查找当前工程中的文本
Ctrl+R  替换当前文件中的文本
Ctrl+Shift+R  替换当前工程中的文本
Ctrl+N  查找类
Ctrl+Shift+N  查找文件
Ctrl+Shift+Alt+N  查找项目中的变量或方法
Ctrl+E  查找最近更改的文件
Ctrl+O  显示当前类中可以重写的方法或可以实现的接口		
Ctrl+F12  显示当前文件的所有变量和方法

Alt+F7
Ctrl+Alt+F7
Ctrl+Shift+Alt+F7  
--查找当前工程引用当前变量或方法的所有位置

Double Shift  
--搜索当前工程所有包含当前文本的文件名
--Search Everywhere，功能类似于Everything软件

Ctrl+Tab  快速切换上一个浏览过的文件
Ctrl+Alt+←  快速回退到上一个光标停过的位置
Ctrl+Alt+→  快速前进到下一个光标停过的位置
```

## 其他快捷键

```
Alt+Enter  错误提示
Ctrl+P  方法参数提示
Ctrl+W  选中光标所在处的代码
Ctrl+Alt+L  格式化代码
Ctrl+D  删除光标所在行的代码
Ctrl+X  剪切光标所在行的代码
```