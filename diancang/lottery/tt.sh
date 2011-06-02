#!/bin/bash
host=$1
switches=($2)
value=$3

if [ -z $1 ];then
    echo "A script to show readable keno's timetask states and switch them in batch"
    echo ""
    echo "Usage:  ./tt.sh host [switches] [value]"
    echo "        ./tt.sh 10.232.13.210"
    echo "        ./tt.sh 10.232.13.210 stop_all true"
    echo "        ./tt.sh 10.232.13.210 'do_commission offer_prize' true"
    echo ""
    echo "Options:"
    echo "    - host      host of keno's serviceState.htm, eg: '10.232.13.210', 'v066043.cm3:7001'"
    echo "    - switches  one or many switches's keywords splited by blanks, eg: 'start_all', 'do_commission offer_prize'"
    echo "    - value     value of you specified switches, eg: 'true', 'false'"
    exit 0
fi

page="http://$host/keno/timetask.htm"
echo "page: $page"
pattern=`echo $page|sed -r "s:\/:\\\\\/:g"`

for switch in ${switches[@]};do
urls=(`curl -s $page|grep $switch|grep $value|awk -F? '{print $2}'|awk -F\" '{print $1}'|sed "s/^/$pattern?/g"`)
    for url in ${urls[@]};do
        echo $url
        curl -s $url > /tmp/ss
        #exit 0
    done
done

result=`curl -s $page |grep -v "href"|grep -v "title"|sed "s/<[^<>]*>//g"`
echo $result|sed "s/ /n/g"|sed "s/停止/n/g"|tr "n" "\n"|sed "s/^ //g"
