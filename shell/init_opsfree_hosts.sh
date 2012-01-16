GROUP_NAMES=(pclottery waplottery keno ilottery lottery_service lotterytimetask lotteryad)

for group in ${GROUP_NAMES[@]};do
        opsfree -l -g $group -S working_online > $group
        echo init $group done
done
