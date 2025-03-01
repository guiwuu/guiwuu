# linux notes

## basic
```
basic
	version
		cat /etc/issue
		cat /etc/lsb-release
			 lsb_release -a    
		cat /etc/redhat-release
		uname -a
		cat /proc/version
		cat /etc/system-release
	spec
		cpu
			cat /proc/cpuinfo
				core number = processor no. + 1
		memory
			top
		disk
			df -h
	ops
		which zsh
	sudo	
		sudo -n
			no password
	clear	
		clear screen
	posix
		vi /etc/passwd
		chown bob:group2 file2
	不显示任何输出
		echo log > /dev/null 2>&1
	out redirect
		bb 1>/tmp/log 2>/tmp/err
```

## stat

* iostat

```
每秒刷新
	iostat -t 1
读写速率的单位KB(或MB)
	iostat -k(-m)
```

```
stat
	strace
	sar & ksar
	netstat
	top
		top -H
		top -p <pid> -H
	ps
	lsof
		按文件查找
			lsof/lsof file1 file2
		按目录查找
			lsof +D /usr/lib
			lsof | grep '/usr/lib'
				速度更快
		按用户查找(-u)
			lsof -u root/lsof -u root,admin/lsof -u root -u admin
			lsof -u ^root
				列出除root用户外的所有用户打开的文件
		按进程名查找(-c)
			lsof -c apache/lsof -c apache -c python
				只列出以apache开头的进程打开的文件
		按pid查找(-p)
			lsof -p 1/lsof -p 1 -p 2/lsof -p 1,2
				列出所有由某个PID对应的进程打开的文件
			lsof -p ^1
				列出除root用户外的所有用户打开的文件
		按pgid查找(-g)
			lsof -g 1234
				查找所有PGID为1234的进程打开的文件
		按网络查找(-i)
			lsof -i
				所有打开了网络套接字（TCP和UDP）的进程
			lsof -i tcp/udp
				只列出打开TCP或UDP sockets的进程
			lsof -i :25
				占用TCP或UDP的25端口的进程
			lsof -i :smtp
				使用/etc/services中制定的端口名称来代替端口号
			lsof -i tcp:53
				找到使用某个tcp端口的进程
		按描述符查找(-d)
			lsof -d 2/lsof -d 0-2
				列出所有以描述符2或0,1,2打开的文件
			lsof -d mem
				列出所有内存映射文件
			lsof -d txt
				列出所有加载在内存中并正在执行的进程
		其它
			ls -t -i
				仅输出打开网络连接的进程的PID
			lsof -r 1
				每秒钟重复打印一次
			lsof -N
				列出所有NFS（网络文件系统）文件
			lsof -U
				列出所有UNIX域Socket文件
		选项组合
			或
				lsof -u pkrumins -c apache
					列出pkrumins或apache打开的文件
			与
				lsof -a -u pkrumins -c bash
					-a参数可以将多个选项的组合条件由或变为与
	netcat
	vmstat
		每隔1s刷新一次上下文切换
			vmstat -1
```
* pidstat

```
pidstat -d 1 # disk usage with 1s auto refresh
pidstat -u # cpu usage
```

## net
```
	ssh
		用keychain实现ssh免登录
			/usr/local/bin/keychain $HOME/.ssh/id_dsa
			source $HOME/.keychain/login1.cm4-sh
		ssh -i xxx.pem 123.123.123.13
		ssh -L 8888:172.16.1.210:8081 ubuntu@35.154.24.173
		ssh -vvv
			debug
				or ssh -v
		ssh -t nick@host "sudo ls"
	curl
		curl -v
		出口ip
			curl -m 10 'http://www.taobao.com/help/info.php' 2>/dev/null | tail -5 |grep -Po '\d+.\d+.\d+.\d+'
				最后一位换成.0/24
	wget
	iptables
		nat
			sudo iptables -t nat -I PREROUTING -d 172.16.0.21 -p tcp --dport 7070 -j DNAT --to 192.168.21.10:7001
			sudo iptables -t nat -I POSTROUTING -d 172.16.0.21 -p tcp --dport 7070 -j SNAT --to 192.168.21.10
		show
			sudo iptables -L -n --line-number
		save
			sudo iptables-save
	nat
		cat /etc/resolv.conf
	nc
		nc localhost 2977
			like telnet
	host
		host www.google.com
			CNAME
		host -a www.google.com
	dig
		dig www.google.com
		dig www.google.com NS +short
	nslookup
	    nslookup www.google.com
```

## filesystem

```
filesystem
	find
		find * | grep ".*\\..*"
		find * -name "Dockerfile*"
		find ./ -name fileName
	du
		du -sh *
		sudo du -sk * | sort -n -r 
	file
		file README.md
			README.md: UTF-8 Unicode text, with CRLF line terminators
		file -i README.md
			README.md: text/plain; charset=utf-8
	rsync
		rsync -vzrtopgu -progress /local/path/to/src/ /local/path/to/desc/
	df
		df -h
		df -i
		df -i /local
			df -i /local | grep -o "[0-9]\+%" | awk 'NR == 1 { print $1 }' | sed 's/%//'
	ls
		ls -l --time-style long-iso
	time dd if=/dev/zero of=/tmp/test bs=8k count=1000000
	chmod
		chmod 777 filename
		chmod +w filename
	chown
		chown admin filename
	chgrp
		chgrp admin filename
	ln
		ln -s /path/to/file /path/to/link
	tee
		read stdout then write to stdout and files
	stat
		stat *
```

## text

```
text
	sed
		读取第五行
			cat /tmp/svninfo |sed -n '5p'
		按行号截取
			sed -n '1000,2000p' app.log > /tmp/seg.log
		正则替换
			sed -r 's/^\? +//' access.log
		行号删除
			sed '1d;$d' 2013-06-08-access_log > access.log
			sed '2,5d' datafile 
				删除第二到第五行  
		正则删除
			sed '/My/,/You/d' datafile  
				删除包含"My"的行到包含"You"的行之间的行
			sed '/My/,10d' datafile  
				删除包含"My"的行到第十行的内容
			sed -i '' '/pattern to match/d' ./infile
				MAC & direct Edit
		保存到原文件
			sed -i datafile
		docs换行符转linux并保存
			sed -i -e 's/.$//' *.sh
	head
	tail
	awk
		sum
			{a+=$1}END{print a}
		hash
			{a[$1]++}END{for(i in a)print i}
		substr
			{print substr($2, 1, 20)}
		lower
			awk '{print tolower($0)}'
	diff
		并列
			diff -y
		指定并列宽度
			diff -W 100
	grep
		grep -R keyWord .
			exclude by keyWords
		grep -n
			show line number
		grep --no-filename
		grep -o " [a-zA-Z0-9.]\+Exception"
		grep -E "foo|bar"
			grep -v -e ' foo-*' -e ' bar-' xyz.log
		grep -nr '20G' .
		grep -i -r foo *
			grep -i -r foo .
	sort
		sort -k 5
	tr
		tr -d winFee:
		tr '[:upper:]' '[:lower:]'
	uniq
		uniq -c
	iconv
		iconv -f gbk -t utf-8 README.md
			如果stderr输出"cannot convert"，源文件则不是gbk编码
	seq
		 seq 30001 40000 > yace.daily.140129
	cut
		cut -d ":" -f 1,2,3
	jq
		jq '.[]'
		jq '.Name'
```

## shell

```
shell
	loop
		for
			for ((i=1; i<=10; i++)); do echo $i; done
			for KEY in "${KEYS[@]}"; do
                echo "    $KEY"
            done
			for dir in *; do (cd "$dir" &&  git commit --amend -m "Upgrade to Ruby27"); done
		while
			i=0; while [ $i -lt 10 ]; do echo $i; i=$(($i+1)); done
			cat file.txt | while read line; do echo $line; done
			while read line; do echo $line; done < file.txt
	read
		read -p"Confirm(y/n)?" 
        echo
        if [[ $REPLY =~ ^[Yy]$ ]]; then
        fi
	if
		if [ "$FOO" = "bar" ]; then
          echo 'Restart agent...'
          ...
        fi
		and
			if [[ "$foo" = "foo" && "$bar" = "bar" ]]; then
              element_exists="false"
            fi
	which?
		echo $SHELL
		echo $0
```

## daemon

```
deamon
	nohup your/command > log &
	screen
		screen -r
			restore
		screen -D
			detach
```

## xargs

```
xargs
	usage
		batch_curl
			cat ids.txt | xargs -i curl http://localhost:7001/timetask/redotask.jsp?id={}
		并发
			xargs -i -P 6 ./master.sh {}
```

## time

```
time
	time pwd
	time -p pwd
	export TIMEFORMAT="real: %E, user: %U, sys: %S"
	w
		系统启动时间
	date	
		date -d "1 day ago" +"%Y-%m-%d
		date -d "2 second" +"%Y-%m-%d %H:%M:%S"
		date -d "1970-01-01 1234567890 seconds" +"%Y-%m-%d %H:%m:%S"
		date +%s
```

## de/compress
```
	tar zcvf target.tar.gz one two three/
	tar zxvf source.tar.gz
	gunzip *.gz
	gzip -d *.gz
	base64 -di
```

## expect

```
expect
	spawn ssh -t nick@host "sudo"
	#!/usr/bin/expect
	interact
	args
		spawn ssh -t [lindex $argv 0]
	matching
		expect   "*yes/no" { send "yes\r"; exp_continue }
		expect eof
		expect "1\r"
```

## rpm

```
rpm
	rpm -qip foo.x86_64.rpm
```

## paste

```
paste
	% sort <<EOF
    heredoc> b
    heredoc> c
    heredoc> a
    heredoc> EOF
    a
    b
    c
```

## vim

```
vim
	set nu
		set nonu
	color pablo
		colorscheme pablo
			vi ~/.vimrc
```
