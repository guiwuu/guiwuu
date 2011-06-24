#!/bin/bash
host=$1
value=${!#}

if [ "$2" = "-a" ];then
    agents=($3);
    option="-a";
fi

if [ "$2" = "-s" ];then
    switches=($3);
    option="-s";
fi

if [ "$4" = "-s" ];then
    switches=($5);
    option="-s";
fi

if [ -z $host ] || [ "$option" != "" -a "$option" != "-a" -a "$option" != "-s" ];then
    echo "Show readable keno's service states and switch them in batch"
    echo ""
    echo "Usage:  ./ss.sh host [-a agents] [-s switches] [value]"
    echo "        ./ss.sh 10.232.13.210"
    echo "        ./ss.sh 10.232.13.210 -a '12:8 12:11' true"
    echo "        ./ss.sh 10.232.13.210 -s 'do_commission offer_prize' true"
    echo "        ./ss.sh 10.232.13.210 -a '12:8 12:11' -s 'book_order do_commission' true"
    echo ""
    echo "Options:"
    echo "    - host      host of keno's serviceState.htm, eg: '10.232.13.210', 'v066043.cm3:7001'"
    echo "    - switches  one or many switches's keywords splited by blanks, eg: 'start_all', 'do_commission offer_prize'"
    echo "    - value     value of you specified switches, eg: 'true', 'false'"
    exit 0
fi

if [ -z $agents ];then
    agents=("?")
fi

if [ -z $switches ];then
    switches=("agent_lottery_start")
fi

if [ "$value" != "true" -a "$value" != "false" ];then
    value="??"
fi

page="http://$host/keno/serviceState.htm"
echo "page: $page"
pattern=`echo $page|sed -r "s:\/:\\\\\/:g"`

for agent in ${agents[@]};do
    for switch in ${switches[@]};do
        urls=(`curl -s $page|grep $agent|grep $switch|grep $value|awk -F? '{print $2}'|awk -F\" '{print $1}'|sed "s/^/$pattern?/g"`)
        for url in ${urls[@]};do
            echo $url
            curl -s $url > /tmp/ss
        done
    done
done

result=`curl -s $page |grep -v "href"|grep -v "title"|sed "s/<[^<>]*>//g"`
echo $result|sed "s/操作:/n/g"|sed "s/)/)n/g"|tr "n" "\n"|sed "s/^ //g"
