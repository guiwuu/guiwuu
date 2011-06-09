log_file=$1
issue_id=$2

if [ -z $log_file ];then
        echo "please specifc a bjtc log file"
        exit 0
fi

if [ -z $issue_id ];then
        issue_id='WARN'
fi

tb_order_num=`grep 出票调用 $log_file | grep $issue_id | wc -l`
echo "订单总数：$tb_order_num"

ll_order_num=`grep 出票调用 $log_file | grep $issue_id | awk -F， '{print $3}' | sed "s/共//g" | sed "s/票//g" | awk '{a+=$1}END{print a}'`
echo "彩票总数：$ll_order_num"

agent_total_ms=`grep 出票调用 $log_file |  grep $issue_id | awk -F， '{print $4}' | sed "s/耗时 //g" | sed "s/ms//g" | awk '{a+=$1}END{print a}'`
total_ms=`grep executed $log_file | grep $issue_id | awk '{print $8}' | sed 's/ms//g' | awk '{a+=$1}END{print a}'`

agent_cost_per_tb_order=`expr $agent_total_ms / $tb_order_num`
agent_cost_per_ll_order=`expr $agent_total_ms / $ll_order_num`
cost_per_tb_order=`expr $total_ms / $tb_order_num`

echo "代理商订单平均耗时：${agent_cost_per_tb_order}ms"
echo "代理商彩票平均耗时：${agent_cost_per_ll_order}ms"
echo "订单平均耗时：${cost_per_tb_order}ms"
