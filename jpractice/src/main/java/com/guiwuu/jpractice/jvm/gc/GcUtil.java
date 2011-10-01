package com.guiwuu.jpractice.jvm.gc;

public class GcUtil {

    private static final Runtime rt = Runtime.getRuntime();

    /**
     * 
     * 计算实例的大小（字节数）
     * 
     * @return 实例所占内存的字节数
     * @throws Exception
     */
    public static long sizeOf(SizeOf obj, int count) {
        GcUtil.runGC();
        Object[] objects = new Object[count];

        // 实例化前堆已使用大小
        long before = GcUtil.usedMemory();
        // 多实例化一个对象
        for (int i = -1; i < count; ++i) {
            Object object = obj.newInstance();

            if (i >= 0) {
                objects[i] = object;
            } else {
                object = null;// 释放第一个对象
                GcUtil.runGC();// 垃圾收集
                before = GcUtil.usedMemory();// 实例化之前堆已使用大小
            }
        }

        GcUtil.runGC();
        // 实例化之后堆已使用大小
        long end = GcUtil.usedMemory();
        final int size = Math.round(((float) (end - before)) / count);

        // 释放内存
        for (int i = 0; i < count; ++i) {
            objects[i] = null;
        }
        objects = null;

        return size;
    }

    public static void runGC() {
        // 执行多次以使内存收集更有效
        for (int r = 0; r < 4; ++r) {
            long used1 = usedMemory();
            long used2 = Long.MAX_VALUE;
            for (int i = 0; (used1 < used2) && (i < 500); ++i) {
                rt.runFinalization();
                rt.gc();
                Thread.yield();
                used2 = used1;
                used1 = usedMemory();
            }
        }
    }

    /**
     * 
     * 堆中已使用内存
     * 
     * @return 堆中已使用内存
     */
    public static long usedMemory() {
        return rt.totalMemory() - rt.freeMemory();
    }
}
