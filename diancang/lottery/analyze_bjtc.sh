log_file=$1
issue_ids=($2)

if [ -z $log_file ];then
        echo "please specifc a bjtc log file"
        exit 0
fi

if [ -z $issue_id ];then
        issue_ids=`grep executed $log_file | awk '{print $5}' | awk -F_ '{print $1}' | sed 's/batchId://g' | awk '!a[$0]++' | sort`
        issue_ids=(`echo $issue_ids`)
fi

for issue_id in ${issue_ids[@]};do
        echo "issue_id: $issue_id"

        tb_order_num=`grep executed $log_file | grep $issue_id | wc -l`
        if [ -z $tb_order_num ];then
                tb_order_num="NA"
        fi
        echo "订单总数：$tb_order_num"

        ll_order_num=`grep 出票调用 $log_file | grep $issue_id | awk -F， '{print $3}' | sed "s/共//g" | sed "s/票//g" | awk '{a+=$1}END{print a}'`
        if [ -z $ll_order_num ];then
                ll_order_num="NA"
        fi
        echo "彩票总数：$ll_order_num"

        agent_total_ms=`grep 出票调用 $log_file |  grep $issue_id | awk -F， '{print $4}' | sed "s/耗时 //g" | sed "s/ms//g" | awk '{a+=$1}END{print a}'`
        if [ -z $agent_total_ms ];then
                agent_total_ms="NA"
        fi

        total_ms=`grep executed $log_file | grep $issue_id | awk '{print $8}' | sed 's/ms//g' | awk '{a+=$1}END{print a}'`
        if [ -z $total_ms ];then
                total_ms="NA"
        fi

        if [ $agent_total_ms != "NA" -a $tb_order_num != "NA" ];then
                agent_cost_per_tb_order="`expr $agent_total_ms / $tb_order_num`ms"
        else
                agent_cost_per_tb_order="NA"
        fi

        if [ $agent_total_ms != "NA" -a $ll_order_num != "NA" ];then
                agent_cost_per_ll_order="`expr $agent_total_ms / $ll_order_num`ms"
        else
                agent_cost_per_ll_order="NA"
        fi

        if [ $total_ms != "NA" -a $tb_order_num != "NA" ];then
                cost_per_tb_order="`expr $total_ms / $tb_order_num`ms"
        else
                cost_per_tb_order="NA"
        fi

        echo "代理商订单平均耗时：${agent_cost_per_tb_order}"
        echo "代理商彩票平均耗时：${agent_cost_per_ll_order}"
        echo "订单平均耗时：${cost_per_tb_order}"
        echo ""
done
