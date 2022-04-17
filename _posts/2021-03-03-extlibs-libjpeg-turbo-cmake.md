---
layout: post
title: libjpeg-turbo-CMake工具编译Android平台静态库
categories: 编译三方库
description: some word here
keywords: 编译三方库, libjpeg-turbo
---

简介：使用AndroidStudio自带的CMake工具编译第三方库libjpeg-turbo源码获取Android平台的静态库.a

## 环境配置说明

> AndroidStudio版本：3.0.1
>
> 自带CMake版本：3.6.4111459
>
> 自带ndk版本：r18b
>
> libjpeg-turbo版本：2.0.6
>
> 注：使用AndroidStudio配合自带CMake工具编译Android平台库，可以少设置很多参数，其他方式会出现各种问题

## 1.libjpeg-turbo介绍

libjpeg-turbo图像编解码器，使用了SIMD指令（MMX，SSE2，NEON，AltiVec）来加速x86，x86-64，ARM和PowerPC系统上的JPEG压缩和解压缩。在这样的系统上，libjpeg-turbo的速度通常是libjpeg的2-6倍，其他条件相同。在其他类型的系统上，凭借其高度优化的霍夫曼编码，libjpeg-turbo仍然可以大大超过libjpeg。在许多情况下，libjpeg-turbo的性能可以与专有的高速JPEG编解码器相媲美。

源码地址：

> [https://libjpeg-turbo.org/](https://libjpeg-turbo.org/)
>
> [https://github.com/libjpeg-turbo/libjpeg-turbo](https://github.com/libjpeg-turbo/libjpeg-turbo)

## 2.AndroidStudio-CMake工具编译步骤

### 1.新建工程

在AndroidStudio中新建带有CPP的工程

### 2.复制libjpeg-turbo源码

直接将libjpeg-turbo源码文件夹复制到AndroidStudio工程目录的cpp文件夹下，注意源码文件夹中的”-“改为“_“，即libjpeg_turbo_2_0_6，防止特殊符号不识别

### 3.修改CMakeLists.txt路径

将app的build.gradle中的cmake path 改成libjpeg-turbo的CMakeLists.txt路径

```groovy
android {
    ...
    externalNativeBuild {
        cmake {
            //path "CMakeLists.txt"
            path "src/main/cpp/libjpeg_turbo_2_0_6/CMakeLists.txt"
        }
    }
}
```

### 4.编译获取静态库

1.选择release版本：Build->Select Build Variant->release

2.编译：Build->Make Project

3.获取静态库

静态库在build/intermediates/cmake/release文件夹

动态库在.externalNativeBuild/cmake/release文件夹

注：使用静态库时只需要libturbojpeg.a，动态库未尝试，并至少导入以下头文件：

```c
jconfig.h
jerror.h
jmorecfg.h
jpeglib.h
turbojpeg.h
```
