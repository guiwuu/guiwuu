#!/bin/bash
ns=$1
no=$2
lotteryservice=("lotteryservice013108.cm3" "lotteryservice014119.cm3" "lotteryservice038065.cm4" "lotteryservice038105.cm4")

if [ "$ns" = "k" ];then
    servers=("keno025113.cm3" "keno024089.cm3" "keno025112.cm3" "keno031109.cm4" "keno038165.cm4" "keno038152.cm4" "keno008085.cm4" "v076193.cm3")
elif [ "$ns" = "l" ];then
    servers=("v024058.cm3" "v017068.cm4")
elif [ "$ns" = "ls" ];then
    servers=("lotteryservice013108.cm3" "lotteryservice014119.cm3" "lotteryservice038065.cm4" "lotteryservice038105.cm4")
elif [ "$ns" = "i" ];then
    servers=("pclottery024086.cm3")
else
    echo "options: k, l, ls, i"
    exit 0
fi

i=0
if [ -z $no ];then
    for server in ${servers[@]};do
        let i=$i+1
        echo "$i   $server"
    done
    exit 0
fi

let no=$no-1
#echo $no
#echo ${servers[$no]}
#exit 0
ssh "admin@${servers[$no]}"
