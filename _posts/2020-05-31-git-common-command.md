---
layout: post
title: git常用命令
categories: 常用命令
description: some word here
keywords: 常用命令, git
---

git介绍以及常用命令总结

## git介绍

> git是一种分布式的版本管理系统
> git的作用是对代码文件进行版本管理，方便将不同时间段或不同的分支之间的不同版本进行切换修改

## 代码提交---基本操作

适用于一个人开发的情况

```
git add .
git commit -m "AD-20190101-修改记录"
git push origin master
```

## 代码提交---完整操作

适用于多人协同开发的情况

```
git pull    更新远程仓库代码
git stash    先将本地修改暂存起来
git stash list    查看暂存的信息	
git pull    再次更新远程仓库代码
git stash pop stash@{0}    还原暂存的内容，如果有冲突，要解决冲突
git add .
git commit -m "AD-20190101-修改记录"
git push origin master
```

## 代码提交追加

提交一笔修改之后，如果还没有合并到仓库，再次修改可以进行追加

```
git add .
git commit --amend  进入vim后，直接输入  :wq
git push origin master
```

## 本地仓库管理流程

```
git init    初始化文件夹，生成.git文件夹
git add .
git commit -m "AD-20190101-修改记录"
```

## 远程仓库管理流程

第一次提交：远程服务器先创建一个仓库，然后链接到本地工程，链接时有两种方法。

（1） 如果仓库可以clone到本地，说明仓库里含有.git文件夹，无需在本地初始化工程，直接clone到本地使用，方法如下：

```
git clone https://xxx.com/yyy/Test.git
git add .
git commit -m "first commit"
git push origin master
```

（2）如果仓库不能clone到本地，说明仓库里没有.git文件夹，需要在本地工程文件夹中初始化，并关联远程仓库，方法如下：

```
git init
git add .
git commit -m "first commit"
git remote add origin https://xxx.com/yyy/Test.git
--此处有时会提示错误，网上搜索下提示信息就可以解决
git push origin master
```

## 仓库分支相关命令

```
git branch    查看当前分支
git branch -al    查看仓库的所有分支
git branch -v    查看当前分支的当前提交信息
git checkout master    切换到master分支
git checkout .    放弃当前分支的所有修改
git checkout Demo/com/example/HelloWorld/MainActivity.java
--放弃当前分支中此文件的修改
```

## git push命令

```
git push origin master
git push origin HEAD:refs/for/master
```

## git其他常用命令

```
git status
--查看文件修改状态

git diff
--查看所有文件与上次的区别

git diff Demo/com/example/HelloWorld/MainActivity.java
--查看此文件与上次的区别

git log
--查看所有已经提交的版本历史

git reflog
--查看所有分支的所有操作记录和提交的版本历史

git reset --hard HEAD^    
--回退到历史某个版本(HEAD^参数是历史版本的commit id)

git reset HEAD
--整体回到上一次操作，撤销所有文件的add

git reset HEAD Demo/com/example/HelloWorld/MainActivity.java
--撤销某一个文件的add
```

