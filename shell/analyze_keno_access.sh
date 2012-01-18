date=$1

if [ -z $date ];then
  date=`date '+%Y-%m-%d'`
fi

year=${date:0:4}
month=${date:5:2}
day=${date:8:2}

echo uniq gateway of $date:
tmp_file=/tmp/caipiaogateway
pgm -f /home/diancang/host/keno "cat /home/admin/cai/logs/cronolog/$year/$month/$date-taobao-access_log | grep gateway | awk '{a[\$7]++}END{for (i in a)print i}'" > $tmp_file
cat $tmp_file | grep http | awk '{a[$1]++}END{for (i in a)print i}' | sort
