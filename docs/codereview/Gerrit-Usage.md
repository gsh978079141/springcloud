##Backend gerrit使用手册

### 目的
开发人员和管理人员只需操作gerrit 就能完成所有的代码管理，提高开发和上线效率，任何问题可以发邮件到hek@deepblueai.com

### 用户组分类
- backend-developers
- backend-leaders
> backend-developers组用户权限：
> 1, master分支代码查看，push, review +1 -2, abandon 自己的review。
> 2，自己定义分支全部控制权限

> backend-leaders组用户权限：
> 1 ,master分支代码查看，push, review +2 -2, abandon , submit,  
> 2, release 分支代码查看，push,  review +2 , -2, abandon, push merge, push without review
> 3，自己定义分支全部控制权限

### 分支策略
- master分支
- release分支
- 个人分支或者长期功能开发分支

1. master分支主要作为代码review, 代码开发主分支
1. release分支只对leaders可见，里面包含敏感的配置文件信息，release分支同时作为一个长期稳定的上线版本分支，每一次上线后都需要打一个tag
1. 一般开发任务不需要在gerrit上创建自己的分支，除非有长期的协同大功能开发。

### 开发人员前期环境准备
> 这里假定大家对git 常用命令 已经熟悉，这里只做gerrit部分的设置步骤

``` 
假定developer有了gerrit账号并设置了免密ssh,登录gerrit ，

1, 找到Projects->List->BKD_smartgate , 复制 项目clone命令：
> git clone ssh://xxx@192.168.16.81:29418/BKD_smartgate
> cd BKD_smartgate
> git remote update

clone下来的项目普通开发人员只能看到一个master分支，leaders可以看到master 和 relase分支.

2, 设置Alas
在~/.gitconfig文件中添加：
[alias]
push-release = push origin HEAD:refs/for/release
push-review = push origin HEAD:refs/for/master
unpushed = lo --branches --not --remotes --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit --date=relative
lg = log --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit --date=relative
ca = commit -a
ci = commit
st = status
co = checkout
br = branch
```

### commit message 格式
所有commit 格式都应该如下：
QUIX2-12: smartgate 后台系统设备管理，设备监控开发

Change-Id: I6be1be65a4d9839555a7c27a217cdd063d1ad99b

### 开发人员代码review步骤
```
开发人员：
>git remote update
>git co master
>git fetch && git rebase master
>git checkout -b bugfix-QUIX2-12
>git add src/com/deepblue/xxx/xxx/xxx1.java
>git add src/com/deepblue/xxx/xxx/xxx2.java
>git commit -m "QUIX2-12: smartgate 后台系统设备管理，设备监控开发"
>git push-review     （会自动提到gerrit的master分支）
如果有修改内容
>git add src/com/deepblue/xxx/xxx/xxx2.java
>git commit --amend
>git push-review

到gerrit web 界面要求相关开发进行代码review,也可以自己-2，这样可以不被submit. review完成后leader可以进行submit

如果是紧急hotfix, 在开发人员 master gerrit submit成功后，leader需要将这个commit 在gerrit上cherry-pick到release 分支并完成上线

```

### 稳定master代码同步到release分支
```
>git co master
>git fetch && git rebase
>git co release
>git merge master
>git push-release
添加相关人员代码review,然后submit到gitlab仓库
```

### 注意事项
- 禁止用  'git add .'   ， 必须用'git add src/xxx/xxx.xxx'
- 要修改新的代码前先更新master分支，然后通过master分支checkout 一个其他分支
- 尽量不要在本地的master上直接改代码，每次在改代码之前先更新本地master分支，然后  git checkout -b featrure-QUIX2-xxx, 在本地的feature-QUIX2-xxx 改完后再merge 到master 分支，如果在feature-QUIX2-xxx 分支有多次commit, merge 时如果想把所有commit 变成一个commit, 用git merge --squash feature-QUIX2-xxx , 否则正常merge 用 git merge --no-ff -m "merge with no-ff" feature-QUIX2-xxx ，或者直接在当前分支用git push-review 会自动推到gerrit 的master分支

### 常用命令
- git st 查看当前git 仓库状态
- git lg 查看commit  (经常用，结合git reset 来回切换历史commit)
- git unpushed 查看哪些还没有push到gitlab仓库,(用之前应该更新代码)
- git fetch && git rebase  master 更新代码及分支信息
- git reset --hard origin 还原成远端git库最新代码
- git reset --hard xxx 还原到指定commit 