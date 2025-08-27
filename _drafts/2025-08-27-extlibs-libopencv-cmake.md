---
layout: post
title: OpenCV-CMake工具编译Android平台静态库
categories: 编译三方库
description: some word here
keywords: 编译三方库, libopencv
---

简介：使用CMake-GUI工具编译第三方库OpenCV源码获取Android平台的静态库.a

## 环境配置说明

> JAVA版本：JDK8，配置到环境变量
>
> AndroidSDK版本：3.0.1（通过AndroidStudio3.0.1下载的SDK）
>
> CMake-GUI版本：3.18.6
>
> ndk版本：r18b
>
> OpenCV版本：4.5.5
>
> MinGW：MinGW-w64或者MSYS2，配置到环境变量（自带MinGW工具）
>
> Apache Ant：1.10.15，配置到环境变量（用于java封装，如果只编译.so或.a，不编译JAVA接口则不需要）
>
> 注：CMake编译时，如果在同一个build文件夹先编译64位，在编译32位库时，有64位的CMakeCache.txt缓存，会报neon支持和float精度类型错误。经验证，同一个构建文件夹不能同时编译多个目标平台，一次只能编译一个平台的库，所以如果要编译32位和64位的库，需要在两个build文件夹进行构建编译。

## OpenCV介绍

如果使用so的话可以直接使用下载包里的，还有java封装，也可以自己编译

源码地址：

> [https://opencv.org/releases/](https://opencv.org/releases/)

## MinGW介绍

MinGW工具地址：

> [https://www.mingw-w64.org/downloads/#mingw-w64-builds](https://www.mingw-w64.org/downloads/#mingw-w64-builds)

MSYS2工具地址：

> [https://www.mingw-w64.org/downloads/#msys2](https://www.mingw-w64.org/downloads/#msys2)

## CMake-GUI工具编译步骤

### 1.输入源码路径和编译输出路径

![](..\images\post\extlibs_libopencv_input_ouput.jpg)

### 2.Add Entry配置环境变量属性

点击Add Entry，添加以下几个属性（添加完会出现红色，不是报错，代表新增）：

- ANDROID_SDK，类型为PATH，填入Android SDK的路径
- ANDROID_NDK，PATH，填入NDK的路径
- ANDROID_ABI，STRING，设置要编译的平台，不填默认为armeabi-v7a（CMake-GUI工具一次只能编译一个平台，如果要同时编译armeabi-v7a和arm64-v8a两个平台，需要分别配置两次，然后合并生成）
- ANDROID_NATIVE_API_LEVEL，STRING，默认API为21
- ANDROID_STL，STRING，根据需求写入 `c++_static` 或 `c++_shared` ，默认是gnustl_static
- ANT_EXECUTABLE，PATH，填入ANT路径下的bin路径

![](..\images\post\extlibs_libopencv_add_entry.jpg)

### 3.首次Configure，获取OpenCV参数选项

首次点击Configure，编译器的选择如下：

![](..\images\post\extlibs_libopencv_configure_1.jpg)

编译工具链的选择如下（用其他的方式可能会出现各种问题）：

![](..\images\post\extlibs_libopencv_configure_2.jpg)

等待Configure完成，会出现Configure done。

### 4.参数调整，再次Configure，然后Generate

以下参数可以根据需求调整（尤其是需要裁剪OpenCV库的体积时可选取最少数量的模块进行编译）：

- BUILD_ANDROID_PROJECTS = OFF（不需要示例项目可取消）
- BUILD_ANDROID_EXAMPLES = OFF（不需要示例项目可取消）
- BUILD_PERF_TESTS = OFF（不需要示例项目可取消）
- BUILD_TESTS = OFF（不需要示例项目可取消）
- BUILD_opencv_world = OFF（如果为ON生成libopencv_world.so或者libopencv_world.a打包库）
- BUILD_SHARED_LIBS = OFF（如果是ON则只编译so库）
- WITH_CUDA = OFF（CUDA是NVidia推出的并行计算架构，编译非安卓SDK时建议添加）
- WITH_OPENCL = ON（编译安卓SDK，建议添加移动端的并行架构支持）
- WITH_OPENCL_SVM = ON（建议开启共享虚拟内存）


如果只想使用OpenCV的基础库（Mat、图像处理算法、并且打包在一起），使最后的库体积尽可能小，可参考以下编译选项的修改方法：

- `BUILD_` 开头的选项只保留BUILD_opencv_core、BUILD_opencv_imgproc、BUILD_opencv_world，其他全部选OFF；

- `WITH_` 开头的选项只保留WITH_PTHREADS_PF，其他全部选OFF（WITH_CPUFEATURES、WITH_OPENCL、WITH_OPENCL_SVM默认或者看个人情况）；

- 其他开头的选项可以保持默认，编出来的静态库大概100-110MB左右。

调整完成后再次点击Configure，完成后点击Generate。

### 5.命令行执行编译生成静态库

最后通过命令行工具进入到build输出文件夹。

输入命令：mingw32-make.exe

如果没有出现报错，再输入：mingw32-make.exe install（也可以直接输入这行命令）

编译完成后，从build/include文件夹获取头文件，从build/install/sdk/native/staticlibs获取静态库。