#!/bin/bash
export LANG=gbk
product="10: 12: 13: 14: 15: 17: 7: 9:13 9:15 9:18 9:20 9:21 9:6"
echo "++++++++++++++++++++"
echo "++                ++"
echo "++ keno038165.cm4 ++"
echo "++                ++"
echo "++++++++++++++++++++"
~/diancang/ss.sh "keno038165.cm4:7001" -a "$product" true
echo "++++++++++++++++++++"
echo "++                ++"
echo "++ keno038152.cm4 ++"
echo "++                ++"
echo "++++++++++++++++++++"
~/diancang/ss.sh "keno038152.cm4:7001" -a "$product" true
echo "+++++++++++++++++"
echo "++             ++"
echo "++ v076193.cm3 ++"
echo "++             ++"
echo "+++++++++++++++++"
~/diancang/ss.sh "v076193.cm3:7001" -a "12:8 12:11 12:12" true > /tmp/ss
~/diancang/ss.sh "v076193.cm3:7001" -a "9:8 9:11 9:12" -s "do_commission do_refund get_award_order get_issue get_lucky_number offer_prize prize_order send_goods" true
