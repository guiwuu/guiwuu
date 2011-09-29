import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

/**
*
* @author diancang
*/
@BTrace
public class Dpd {

    @TLS
    private static long decryptBeginTime = 0;

    @TLS
    private static long encryptBeginTime = 0;

    private static java.util.concurrent.atomic.AtomicLong decryptTotalCost = newAtomicLong(0);

    private static java.util.concurrent.atomic.AtomicInteger decryptCount = newAtomicInteger(0);

    private static java.util.concurrent.atomic.AtomicLong encryptTotalCost = newAtomicLong(0);

    private static java.util.concurrent.atomic.AtomicInteger encryptCount = newAtomicInteger(0);

    @OnMethod(clazz = "com.taobao.lottery.biz.dao.util.dpd.Dpd", method = "decrypt")
    public static void startDecrypt(String input) {
        decryptBeginTime = timeNanos();
    }

    @OnMethod(clazz = "com.taobao.lottery.biz.dao.util.dpd.Dpd", method = "decrypt", location = @Location(Kind.RETURN))
    public static void endDecrypt(String output) {
        int count = incrementAndGet(decryptCount);
        long cost = timeNanos() - decryptBeginTime;
        long totalCost = addAndGet(decryptTotalCost, cost);
        double averageCost = (double)totalCost / (double)count;
        print("cost: ");
        print(cost);
        print("ns, decrypt count: ");
        print(count);
        print(", decrypt total cost: ");
        print(totalCost);
        print("ns, decrypt average cost: ");
        print(averageCost);
        println("ns");
    }

    @OnMethod(clazz = "com.taobao.lottery.biz.dao.util.dpd.Dpd", method = "encrypt")
    public static void startEncrypt(String input) {
        encryptBeginTime = timeNanos();
    }

    @OnMethod(clazz = "com.taobao.lottery.biz.dao.util.dpd.Dpd", method = "encrypt", location = @Location(Kind.RETURN))
    public static void endEncrypt(String output) {
        int count = incrementAndGet(encryptCount);
        long cost = timeNanos() - encryptBeginTime;
        long totalCost = addAndGet(encryptTotalCost, cost);
        double averageCost = (double)totalCost / (double)count;
        long b = timeNanos();
        print("cost: ");
        print(cost);
        print("ns, encrypt count: ");
        print(count);
        print(", encrypt total cost: ");
        print(totalCost);
        print("ns, encrypt average cost: ");
        print(averageCost);
        println("ns");
    }
}
                                                                                                                                           70,1         µ×¶Ë
