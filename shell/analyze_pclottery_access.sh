date=$1

if [ -z $date ];then
  date=`date '+%Y-%m-%d'`
fi

year=${date:0:4}
month=${date:5:2}
day=${date:8:2}

echo pv of $date:
tmp_file=/tmp/caipiaopv
pgm -f /home/diancang/host/pclottery "cat /home/admin/cai/logs/cronolog/$year/$month/$date-taobao-access_log | wc -l" > $tmp_file
cat $tmp_file | grep -v SUCESS | awk '{a=a+$1}END{print a}'

echo uniq ip of $date:
tmp_file=/tmp/caipiaoip
pgm -f /home/diancang/host/pclottery "cat /home/admin/cai/logs/cronolog/$year/$month/$date-taobao-access_log | awk '{a[\$1]++}END{for (i in a)print i}'" > $tmp_file
cat $tmp_file | grep -v SUCESS | awk '{a[$1]++}END{for (i in a)print i}' | wc -l
