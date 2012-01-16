#!/bin/bash
trunk=$1
branch_name=$2

#echo "your trunk: $trunk"
#echo "your branch_name: $branch_name"

if [ -z $trunk ];then
	echo "please specify a svn repository trunk, usage:"
	echo "           svn address  -  like: http://svn.taobao-develop.com/repos/lottery/trunk/lottery"
	echo "    svn working folder  -  like: /home/admin/work/lottery"
	echo "                 alias  -  like: lottery, keno, ilottery, lottery-service, lottery-timetask"
	exit
fi

if [ -z $branch_name ];then
	echo "please specify a branch name"
	exit
fi

if [ $trunk = "lottery" ];then
	trunk='http://svn.taobao-develop.com/repos/lottery/trunk/lottery'
elif [ $trunk = "keno" ];then
	trunk='http://svn.taobao-develop.com/repos/keno/trunk/keno'
elif [ $trunk = "ilottery" ];then
	trunk='http://svn.app.taobao.net/repos/ilottery/trunk/ilottery'
elif [ $trunk = "lottery-service" ];then
	trunk='http://svn.app.taobao.net/repos/lottery-service/trunk/lottery-service'
elif [ $trunk = "lottery-timetask" ];then
	trunk='http://svn.app.taobao.net/repos/lottery-timetask/trunk/lotterytimetask'
fi

trunk=`svn info $trunk | grep URL | awk '{print $2}'`

if [ -z $trunk ];then
	echo "svn repository trunk error!"
	exit
fi

svn info $trunk > /tmp/svninfo

project=`echo $trunk | sed "s/.*\///"`
today=`date '+%Y%m%d'`
version=`cat /tmp/svninfo | sed -n '5p' | awk '{print $2}'`
branch=`echo $trunk | sed "s/trunk.*/branches\/V${version}_${branch_name}_${today}\/$project/"`

echo "svn-flow is going to create this branch:"
echo "    $branch"
command="svn cp $trunk $branch -m '创建$name分支' --parents"
#echo "we will execute the command:"
#echo "    $command"

read -n1 -p "do you want to continue [y/n]?"
case $REPLY in
	Y | y) echo
		$command;;
	N | n) echo 
		echo "bye, bye...";;
esac
