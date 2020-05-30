---
layout: post
title: libyuv-NDK工具编译Android平台静态库和动态库
categories: NDK编译链接库
description: some word here
keywords: NDK编译链接库, libyuv
---

使用NDK工具编译第三方源码获取Android平台的静态库.a和动态库.so，以libyuv源码库为例。

## 文章目的

> 使用NDK工具编译第三方源码获取Android平台的静态库.a和动态库.so，以libyuv源码库为例。
>
> 本次使用的ndk版本是r18b，如果需要armeabi库要使用r16b或更低版本编译。
>
> 注意：
>
> 1.ndk17及以后不再支持armeabi库
>
> 2.ndk18及以后不再支持gnustl和stlport，libc++成为ndk中唯一可用的STL，动态库为libc++shared.so，静态库为libc++static.a，c++标准库默认使用libstdc++，如果改为libc++，需要在Application.mk或者build.gradle的cmake中指定，例：
>
> `APP_STL := c++_shared` 、`arguments "-DANDROID_STL=c++_static`
>
> 3.ndk19及以后不再支持gcc编译，全面切换为clang编译

## 1.libyuv介绍

libyuv是谷歌开源的实现YUV与RGB之间的数据转换，以及YUV数据的裁剪、缩放、旋转、镜像的跨平台库，可在Windows、Linux、Mac、Android等操作系统使用，x86、x64、arm架构上进行编译运行，支持AVX2、NEON 等SIMD指令加速，性能非常强。

源码地址：

> <https://chromium.googlesource.com/libyuv/libyuv>
>
> <https://github.com/lemenkov/libyuv>

## 2.NDK工具编译步骤

### 1.修改Android.mk

​	在Android.mk文件中注释掉与jpeg相关的编译，修改后的内容如下：

```makefile
# This is the Android makefile for libyuv for NDK.
LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_CPP_EXTENSION := .cc

LOCAL_SRC_FILES := \
    source/compare.cc           \
    source/compare_common.cc    \
    source/compare_gcc.cc       \
    source/compare_mmi.cc       \
    source/compare_msa.cc       \
    source/compare_neon.cc      \
    source/compare_neon64.cc    \
    source/convert.cc           \
    source/convert_argb.cc      \
    source/convert_from.cc      \
    source/convert_from_argb.cc \
    source/convert_to_argb.cc   \
    source/convert_to_i420.cc   \
    source/cpu_id.cc            \
    source/planar_functions.cc  \
    source/rotate.cc            \
    source/rotate_any.cc        \
    source/rotate_argb.cc       \
    source/rotate_common.cc     \
    source/rotate_gcc.cc        \
    source/rotate_mmi.cc        \
    source/rotate_msa.cc        \
    source/rotate_neon.cc       \
    source/rotate_neon64.cc     \
    source/row_any.cc           \
    source/row_common.cc        \
    source/row_gcc.cc           \
    source/row_mmi.cc           \
    source/row_msa.cc           \
    source/row_neon.cc          \
    source/row_neon64.cc        \
    source/scale.cc             \
    source/scale_any.cc         \
    source/scale_argb.cc        \
    source/scale_common.cc      \
    source/scale_gcc.cc         \
    source/scale_mmi.cc         \
    source/scale_msa.cc         \
    source/scale_neon.cc        \
    source/scale_neon64.cc      \
    source/video_common.cc

common_CFLAGS := -Wall -fexceptions
#ifneq ($(LIBYUV_DISABLE_JPEG), "yes")
#LOCAL_SRC_FILES += \
#    source/convert_jpeg.cc      \
#    source/mjpeg_decoder.cc     \
#    source/mjpeg_validate.cc
#common_CFLAGS += -DHAVE_JPEG
#LOCAL_SHARED_LIBRARIES := libjpeg
#endif

LOCAL_CFLAGS += $(common_CFLAGS)
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include
LOCAL_EXPORT_C_INCLUDE_DIRS := $(LOCAL_PATH)/include

LOCAL_MODULE := libyuv_static
LOCAL_MODULE_TAGS := optional

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_WHOLE_STATIC_LIBRARIES := libyuv_static
LOCAL_MODULE := libyuv
#ifneq ($(LIBYUV_DISABLE_JPEG), "yes")
#LOCAL_SHARED_LIBRARIES := libjpeg
#endif

#生成静态库还是动态库
#BUILD_STATIC_LIBRSRY 静态库
#BUILD_SHARED_LIBRARY 动态库
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := libyuv_static
LOCAL_SHARED_LIBRARIES := libjpeg
LOCAL_MODULE_TAGS := tests
LOCAL_CPP_EXTENSION := .cc
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include
LOCAL_SRC_FILES := \
    unit_test/unit_test.cc        \
    unit_test/basictypes_test.cc  \
    unit_test/color_test.cc       \
    unit_test/compare_test.cc     \
    unit_test/convert_test.cc     \
    unit_test/cpu_test.cc         \
    unit_test/cpu_thread_test.cc  \
    unit_test/math_test.cc        \
    unit_test/planar_test.cc      \
    unit_test/rotate_argb_test.cc \
    unit_test/rotate_test.cc      \
    unit_test/scale_argb_test.cc  \
    unit_test/scale_test.cc       \
    unit_test/video_common_test.cc

LOCAL_MODULE := libyuv_unittest
include $(BUILD_NATIVE_TEST)

```

### 2.新建Application.mk

​	在Android.mk所在文件夹新建Application.mk文件，指定编译的架构平台和Android版本，内容如下：

```makefile
APP_ABI := armeabi-v7a arm64-v8a x86 x86_64
APP_PLATFORM := android-18
```

### 3.libyuv文件夹名修改为jni

​	将libyuv文件夹，也就是Android.mk所在的文件夹名修改为jni，这是ndk-build命令默认指定的文件夹。NDK_PROJECT_PATH是编译工程所在的路径，此参数可省略，命令行内容如下：

```sh
ndk-build NDK_PROJECT_PATH=./
```

### 4.ndk-build命令编译

编译前要确认已经配置了NDK环境，然后使用命令行进入到jni所在的文件夹，如果要清理生成的二进制文件和目标文件，先执行`ndk-build clean`清理缓存，再执行`ndk-build`进行编译库，编译完成后，在jni所在的文件夹会生成两个文件夹，libs文件夹中有`libyuv.so`动态库，obj文件夹中有`libyuv_static.a`静态库。可以在Android.mk中配置生成动态库还是静态库。