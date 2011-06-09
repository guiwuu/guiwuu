log_file=$1

if [ -z $log_file ];then
        echo "please specifc a bjtc log file"
        exit 0
fi

tb_order_num=`grep 出票调用 $log_file | wc -l`
echo "订单总数：$tb_order_num"

ll_order_num=`grep 出票调用 $log_file | awk -F， '{print $3}' | sed "s/共//g" | sed "s/票//g" | awk '{a+=$1}END{print a}'`
echo "彩票总数：$ll_order_num"

agent_total_ms=`grep 出票调用 $log_file | awk -F， '{print $4}' | sed "s/耗时 //g" | sed "s/ms//g" | awk '{a+=$1}END{print a}'`
cost_per_tb_order=`expr $agent_total_ms / $tb_order_num`
cost_per_ll_order=`expr $agent_total_ms / $ll_order_num`

echo "订单平均耗时：${cost_per_tb_order}ms"
echo "彩票平均耗时：${cost_per_ll_order}ms"
