---
layout: post
title: libpng-NDK工具编译Android平台静态库
categories: 编译三方库
description: some word here
keywords: 编译三方库, libpng, libz
---

简介：使用NDK工具编译第三方库libpng和zlib源码获取Android平台的静态库.a

## 环境配置说明

> 自带ndk版本：r18b
>
> libpng版本：1.6.37
>
> zlib版本：1.2.11
>
> 注1：使用ndk工具编译Android平台库，另一种方式android配合cmake工具也可以，需要手动写个cmakelist文件，自带的cmakelist文件不行
>
> 注2：libpng库的使用依赖zlib库，其他平台需要单独编译这个库，Android环境中本身提供了这个库，就像使用log库一样，但本文也会提供zlib库的编译方法

## 1.源码库介绍

### 1.libpng介绍

libpng是一套免费的、公开源代码的程序库，支持对PNG图形文件的创建、读写等操作。

源码地址：

> [http://www.libpng.org/pub/png/libpng.html](http://www.libpng.org/pub/png/libpng.html)
>
> [https://github.com/glennrp/libpng](https://github.com/glennrp/libpng)

### 2.zlib介绍

zlib是通用的压缩库，提供了一套内存内压缩和解压函数，并能检测解压出来的数据的完整性。

源码地址：

> [https://zlib.net/](https://zlib.net/)
>
> [https://github.com/madler/zlib](https://github.com/madler/zlib)

## 2.NDK工具编译步骤

### 1.libpng编译

#### 1.新建Android.mk

在源码文件夹中新建Android.mk文件，添加如下内容：

```makefile
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
    png.c \
    pngerror.c \
    pngget.c \
    pngmem.c \
    pngpread.c \
    pngread.c \
    pngrio.c \
    pngrtran.c \
    pngrutil.c \
    pngset.c \
    pngtrans.c \
    pngwio.c \
    pngwrite.c \
    pngwtran.c \
    pngwutil.c

LOCAL_MODULE := libpng
LOCAL_LDLIBS := -lz
#LOCAL_STATIC_LIBRARIES := cpufeatures

#如果cpu类型为armeabi-v7a或arm64-v8a，开启png的neon支持
ifneq (, $(filter $(TARGET_ARCH_ABI), armeabi-v7a arm64-v8a))
    #如果cpu类型为armeabi-v7a，在使用neon时需要手动添加
    ifeq ($(TARGET_ARCH_ABI), armeabi-v7a)
        LOCAL_ARM_NEON := true
        #LOCAL_CFLAGS := -DHAVE_NEON=1
    endif
    LOCAL_SRC_FILES += \
        arm/arm_init.c \
        arm/filter_neon.S \
        arm/filter_neon_intrinsics.c \
        arm/palette_neon_intrinsics.c
        
    #mk打开变量开关
    LOCAL_CFLAGS += -DPNG_ARM_NEON_OPT=2
    #LOCAL_CFLAGS += -DPNG_ARM_NEON_CHECK_SUPPORTED

    #Cmake打开变量开关
    #add_definitions(-DPNG_ARM_NEON_OPT=2) 
endif

#include $(BUILD_SHARED_LIBRARY)
include $(BUILD_STATIC_LIBRARY)
```

#### 2.新建Application.mk

在Android.mk所在文件夹新建Application.mk文件，指定编译的架构平台和Android版本，Android版本可以不指定，编译时会自动采用支持的最小Android版本，内容如下：

```makefile
APP_ABI := armeabi-v7a arm64-v8a
APP_PLATFORM := android-18
```

#### 3.源码文件夹名修改为jni

将libpng文件夹，也就是Android.mk所在的文件夹名修改为jni，这是ndk-build编译命令默认指定的文件夹。

#### 4.ndk-build命令编译

编译前要确认已经配置了NDK环境，然后使用命令行进入到jni所在的文件夹，执行编译命令:

```
ndk-build
```

编译完成后，在jni所在的文件夹会生成obj文件夹，里面有`libpng.a`静态库。可以在Android.mk中配置生成动态库还是静态库。

注：使用静态库时，还需要导入源码的全部头文件：

```
png.h
pngconf.h
pngdebug.h
pnginfo.h
pnglibconf.h
pngpriv.h
pngstruct.h
```

### 2.zlib编译

#### 1.新建Android.mk

在源码文件夹中新建Android.mk文件，添加如下内容：

```makefile
LOCAL_PATH:= $(call my-dir)  

include $(CLEAR_VARS)  

LOCAL_SRC_FILES := \
    adler32.c \
    compress.c \
    crc32.c \
    deflate.c \
    gzclose.c \
    gzlib.c \
    gzread.c \
    gzwrite.c \
    infback.c \
    inffast.c \
    inflate.c \
    inftrees.c \
    trees.c \
    uncompr.c \
    zutil.c  

LOCAL_MODULE:= libz  

include $(BUILD_STATIC_LIBRARY)
```

#### 2.新建Application.mk

在Android.mk所在文件夹新建Application.mk文件，指定编译的架构平台和Android版本，Android版本可以不指定，编译时会自动采用支持的最小Android版本，内容如下：

```makefile
APP_ABI := armeabi-v7a arm64-v8a
APP_PLATFORM := android-18
```

#### 3.源码文件夹名修改为jni

将zlib文件夹，也就是Android.mk所在的文件夹名修改为jni，这是ndk-build编译命令默认指定的文件夹。

#### 4.ndk-build命令编译

编译前要确认已经配置了NDK环境，然后使用命令行进入到jni所在的文件夹，执行编译命令:

```
ndk-build
```

编译完成后，在jni所在的文件夹会生成obj文件夹，里面有`libz.a`静态库。可以在Android.mk中配置生成动态库还是静态库。