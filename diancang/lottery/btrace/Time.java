import static com.sun.btrace.BTraceUtils.name;
import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.probeClass;
import static com.sun.btrace.BTraceUtils.probeMethod;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.timeMillis;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.TLS;

import com.taobao.lottery.biz.dao.lotteryinfo.dataobject.LotteryIssueDO;

/**
* 监控方法耗时
*
* @author jerry
*/
@BTrace
public class Time {

    /**
      * 开始时间
      */
    @TLS
    private static long sysCalStartTime = 0;

    private static long num = 0;

    /**
      * 方法开始时调用
      */
    @OnMethod(clazz = "com.taobao.lottery.biz.core.order.number.PrizeCalculatorManager", method = "calIssuePrize")
    public static void startMethod(LotteryIssueDO issueDO) {
        sysCalStartTime = timeMillis();
        print("start[");
        print(issueDO);
        println("]");
     }

    /**
      * 方法结束时调用<br>
      * Kind.RETURN这个注解很重要
      */
    @SuppressWarnings("deprecation")
    @OnMethod(clazz = "com.taobao.lottery.biz.core.order.number.PrizeCalculatorManager", method = "calIssuePrize", location = @Location(Kind.RETURN))
    public static void endMethod() {

         print(strcat(strcat(name(probeClass()), "."), probeMethod()));
         print("   [");
         print(strcat("Time taken : ", str(timeMillis() - sysCalStartTime)));
         print("]:");
         println(++num);
    }

}
