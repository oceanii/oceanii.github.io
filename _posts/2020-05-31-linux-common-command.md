---
layout: post
title: linux常用命令
categories: 常用命令
description: some word here
keywords: 常用命令, linux
---

linux常用命令总结

## 常用命令说明

> Linux命令是对Linux系统进行管理的命令
>
> Linux区分大小写，Windows不区分大小写
>
> 以下是最常用的linux命令，其他更多的linux命令详见网页：linux常用命令

## 文件目录

```
cd f:/test    打开f盘test文件夹
cd ..    返回上一层文件夹
ls    查看当前文件夹下的文件名称
pwd    查看当前目录
explorer .    在文件管理器界面打开当前文件夹
```

## 文件查看编辑

```
cat test.txt
--查看文本文件

vim test.txt    
--查看编辑文本文件，vim有三种模式：查看，编辑，命令
--insert进入编辑模式
--: 进入命令模式，wq保存退出
--esc退出当前模式，进入查看模式
```

## 文件和文件夹创建

```
mkdir test    在当前文件夹下建立test文件夹
touch test    在当前文件夹下建立test文件
```

## 文件和文件夹删除

```
rm -i(-f) test.txt    删除文件，-i代表询问
rm -rf tmp/    删除文件夹，-r代表递归，-f代表不询问
rm -rf tmp/*  删除tmp文件夹里的所有文件, 但是不删除tmp文件夹
rm -rf ./*  删除当前文件夹下的所有文件，但是不删除当前文件夹
```

## 文件和文件夹复制剪切

```
cp test.txt ./tmp/    将当前文件夹下的test.txt复制到tmp文件夹下
cp -r test/ ./tmp/    将当前test文件夹复制到tmp文件夹下，-r代表递归
mv test.txt ~/tmp/    将当前文件夹下的test.txt剪切到tmp文件夹下，同样也适用于文件夹剪切
```

## 文件夹压缩解压

```
tar -zxvf test.tar.gz    将test压缩包解压到当前文件夹下
tar -zcvf test.tar.gz tmp/    将tmp文件夹打包到test压缩包
```