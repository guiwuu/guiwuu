package com.guiwuu.jpractice.jvm;

/**
 * @author guiwuu
 */
public class FinallyPuzller {

    /**
     * 0:   iconst_1
     * 1:   istore_0
     * 2:   iload_0
     * 3:   ireturn
     * @return 
     */
    public static int returnDirectly() {
        int i = 1;
        return i;
    }

    /**
     * 0:   iconst_1
     * 1:   istore_0
     * 2:   iload_0
     * 3:   istore_1
     * 4:   iconst_5
     * 5:   istore_0
     * 6:   iload_1
     * 7:   ireturn
     * 8:   astore_2
     * 9:   iconst_5
     * 10:  istore_0
     * 11:  aload_2
     * 12:  athrow
     * @return 
     */
    public static int returnBeforeFinally() {
        int i = 1;
        try {
            return i;
        } finally {
            i = 5;
        }
    }
}
