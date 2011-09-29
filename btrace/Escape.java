import static com.sun.btrace.BTraceUtils.*;

import com.sun.btrace.annotations.*;

/**
*
* @author diancang
*/
@BTrace
public class Escape {

    @TLS
    private static String iEscapeHtml;

    @TLS
    private static String oEscapeHtml;

    @TLS
    private static String iEscapeJavaScript;

    @TLS
    private static String oEscapeJavaScript;

    @OnMethod(
        clazz = "com.alibaba.common.lang.StringEscapeUtil",
        method = "escapeHtml"
    )
    public static void startEscapeHtml(String input) {
        iEscapeHtml = input;
    }

    @OnMethod(
        clazz = "com.alibaba.common.lang.StringEscapeUtil",
        method = "escapeHtml",
        location = @Location(Kind.RETURN)
    )
    public static void endEscapeHtml(@Return String output) {
        oEscapeHtml = output;
        if(output != null && !compare(output, ""))
                return;
        print("EscapeHtml is null: ");
        print(iEscapeHtml);
        print(" -> ");
        println(output);
        jstack();
    }

    @OnMethod(
        clazz = "com.alibaba.common.lang.StringEscapeUtil",
        method = "escapeJavaScript"
    )
    public static void startEscapeJavaScript(String input) {
        iEscapeJavaScript = input;
    }

    @OnMethod(
        clazz = "com.alibaba.common.lang.StringEscapeUtil",
        method = "escapeJavaScript",
        location = @Location(Kind.RETURN)
    )
    public static void endEscapeJavaScript(@Return String output) {
        if(output != null && !compare(output, ""))
                return;
        print("EscapeJavaScript is null: ");
        print(iEscapeHtml);
        print(" -> ");
        print(oEscapeHtml);
        print(" -> ");
        print(iEscapeJavaScript);
        print(" -> ");
        println(output);
        jstack();
    }

    @OnMethod(
        clazz = "com.taobao.lottery.biz.dao.order.dataobject.LotteryPursueDO",
        method = "setTitle"
    )
    public static void setTitle(String input) {
        print(iEscapeHtml);
        print(" -> ");
        print(oEscapeHtml);
        print(" -> ");
        print(iEscapeJavaScript);
        print(" -> ");
        print(oEscapeJavaScript);
        print(" -> ");
        println(input);
        jstack();
    }

}
