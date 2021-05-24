>## 管理员操作迁移代码手册
 （以下为使用ssh 登陆方式，从gitlab迁移代码到gerrit上，这个适用于一般没有gerrit server bash 登录操作权限的） ##

>1.本地生成ssh 公钥，添加到gerrit ,使用带有邮箱的参数. [具体的点击参考路径](https://?>www.cnblogs.com/horanly/p/6604104.html)

>```
> ssh-keygen -t rsa -C "xxx@deepblueai.com"
>```

>通过下面命令验证ssh公钥登陆是否成功

>```
> ssh -p 29418 xxx@192.168.16.81 (xxx为你gerrir用户名，邮箱前缀)
>```


>2.从gitlab 上把你要迁移的项目拉到本地,例如：

>```
>git clone ssh://wangyl@192.168.16.81:29418/xxx
>```


> 3.在gerrit 创建一个新的工程，这里管理员需要向运维部申请创建工程权限，这里有俩种选择:

> **第一种**
>```
>通过 Projects->Create New Project,去掉默认勾选Create initial empty commit选项，这样就没有master分支了，生成一个工程，到Projects->General拷贝工程对应的仓库原地址：**http://192.168.16.81:8080/xxx**
>```
>到gitlab 对应工程目录下面执行命令：
>```
> git remote -v （查看原有的远程仓库）
>```

>添加新建的远程gerrit仓库：
>```
> git remote add gerrit ssh://wangyl@192.168.16.81:29418/xxx
>```

>拉取远程gerrit仓库需要分支的代码，默认为master：
>```
> git fetch gerrit master
>```

>这里因为远程仓库没有master分支，所以可以直接向gerrit推送本地的代码。
>```
> git push gerrit current_branch:remote_branch (remote_branch 为你将要推送的远程分支，可以为master)
>```

>如上到这里第一种方式已经推送完成。


> **第二种**

>```
>通过 **Projects->Create New Project**,生成一个工程，到**Projects->General**拷贝工程对应的仓库原地址：**http://192.168.16.81:8080/xxx**
>```
>到gitlab 对应工程目录下面执行命令：
>```
> git remote -v （查看原有的远程仓库）
>```

>添加新建的远程gerrit仓库：
>```
> git remote add gerrit ssh://wangyl@192.168.16.81:29418/xxx
>```

>可以通过命令查询一下是否添加成功：
>```
> git remote -v （查看原有的远程仓库）
>```

>拉取远程gerrit仓库需要分支的代码，默认为master：
>```
> git fetch gerrit master
>```

>通过查看分支是否有未提交内容，来切换分支，如果未提交，先commit加到暂存区
>```
> git status 
>```

>这时候新的gerrit 远程是有master分支的，所以在本地要合并分支。切换到gerrit分支到本地。
>```
> git checkout -b new_branch gerrit/remote_branch (注意：new_branch 不要设置成跟origin之前有的本地分支同名)
>```

>此时可以通过本地分支关联远程分支是否正确。
>```
> git branch -vv 
>```


>合并要提交的gitlab上对应的本地分支，这里可以用rebase,merge
>```
> git rebase master (master 为你要提交合并的origin 仓库对应的master本地分支，一般由于创建的gerrit仓库只有一次初始commit，基本不会有冲突)
>```

>解决一下同步gerrit远程分支树的head
>```
> git pull --allow-unrelated-histories
>```

>最后提交本分支
>```
> git push gerrit current_branch:remote_branch (remote_branch 为你将要推送的远程分支，可以为master)
>```


>如上到这里第二种方式已经推送完成。


>4.不管是什么操作系统，都需要安装git-review 插件，windows 的最好安装python2.7的版本，不然有python 与git bash出现编码问题冲突。[参考路径](https://www.mediawiki.org/wiki/Gerrit/git-review#Windows)

>5.在本地添加个.gitreview文件push 到服务器上：

>```
>touch .gitreview
>```

>```
> echo >> [gerrit]
> echo >> host=192.168.16.81
> echo >> port=29418
> echo >> project=xxx.git  (xxx 为gerrit对应的仓库)
>```

>把这个文件push 到gerrit 服务器上。

>6.测试git review 

>```
>git add .     git commit -m ""  git review 
>```

> 管理员测试gerrit 完成。



>## 非管理员操作code review 手顺 ##

>按照管理员手顺，安装git-review 插件
>```
>到Projects->General拷贝工程对应的仓库原地址：**ssh://wangyl@192.168.16.81:29418/xxx**
>```

>```
>直接克隆代码：git clone ssh://wangyl@192.168.16.81:29418/xxx(使用从gitlab 拉取代码的员工可以直接用命令设置切换：git remote set-url gerrit ssh://wangyl@192.168.16.81:29418/xxx)
>```


>我们操作本地分支时，最好基于当前分支切换到开发分支上来，保留原来的master 或者dev分支只做 git pull 操作
>```
> git checkout -b dev_newfeature
>```

>修改本地分支代码，开发自己内容。完成之后，git add .   git commit -m ""到本地。然后：
>```
> 切换回基础分支 git pull，再回到开发分支，git rebase xxx 基础分支。解决冲突，再执行git rebase --continue.
>```

>最后执行:
>```
> git reivew xxx  (xxx 为到远程的分支名，不写默认是master)
>```


>在此过程中，可以通过下面命令查看你所操作的分支关联的远程分支:
>```
> git branch -vv 
>```

>本地命令操作完成。


>## gerrit上要注意的几点操作 ##

> gerrit 上面主要分为管理员通过 Protects->Access添加组的read 和push 权限,管理员有+2的权限，可以直接+2，然后确认进仓。 ##

> gerrit 非管理员My->Changes看到Outgoing reviews和Incoming reviews 看到自己推送的和即将参加的review，有+1权限##

> 当代码没通过review时，本地用户再进行提交时，不需要再次git commit -m 参数，直接

>```
> git commit --amend. 
>```

> 当review同时进行时，后一个被管理员同学summit 时发现冲突退回解决时,需要再基于主分支rebase一下，操作上面提交的流程。

> 点击每一个要review的文件，在需要修改的地方双击出现提示符，点击进入，注释你需要提示的修改点，点击save保存，discard放弃，最后在Changes界面上，有个reply按钮，可以post +1,-1处理。




