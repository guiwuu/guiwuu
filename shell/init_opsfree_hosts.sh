GROUP_NAMES=(pclottery waplottery keno ilottery lottery_service lotterytimetask lotteryad lotteryraffle)

for group in ${GROUP_NAMES[@]};do
        opsfree -l -g $group -S working_online > $group
        num=`cat $group | wc -l`
        echo init $group done: $num
done
