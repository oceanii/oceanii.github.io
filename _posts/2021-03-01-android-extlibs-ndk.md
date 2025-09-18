---
layout: post
title: Android-NDK版本说明
categories: 编译Android三方库
description: some word here
keywords: 编译三方库, ndk
---

简介：Android NDK的主要版本的变化和目前常用的版本

## NDK版本说明

> ndk版本下载链接及详细变化说明：
>
> [https://developer.android.com/ndk/downloads/revision_history?hl=zh-cn](https://developer.android.com/ndk/downloads/revision_history?hl=zh-cn)



### ndk版本主要变化

- ndk17及以后不再支持armeabi库
- ndk18及以后不再支持gnustl和stlport，libc++成为ndk中唯一可用的STL，动态库为libc++shared.so，静态库为libc++static.a，c++标准库默认使用libstdc++，如果改为libc++，需要在Application.mk或者build.gradle的cmake中指定，例：`APP_STL := c++_shared` 、`arguments "-DANDROID_STL=c++_static`
- ndk19及以后不再支持gcc编译，全面切换为clang编译
- ndk22开始支持std::filesystem，但有些属性在旧设备上可能不受支持

### 常用的NDK版本（已验证可用）

ndk16、ndk18、ndk19、ndk21