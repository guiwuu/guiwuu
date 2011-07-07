package com.guiwuu.concurrent.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guiwuu
 */
public abstract class AbstractConcurrentTest {

    private static final Logger logger = Logger.getLogger(AbstractConcurrentTest.class.getName());

    public boolean run(int concurrent, int loop) throws Exception {
        Object[] params = {concurrent, loop};
        logger.log(Level.WARNING, "{0} threads will concurrently run {1} loops...", params);
        final CyclicExecuteThread[] threads = new CyclicExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new CyclicExecuteThread("cyclic thread") {

                @Override
                protected boolean runTask() throws Exception {
                    try {
                        return doTest();
                    } catch (Exception e) {
                        return false;
                    }
                }
            };
        }
        return ConcurrentTestUtils.run(threads, loop);
    }

    public abstract boolean doTest() throws Exception;
}
