package com.guiwuu.concurrent.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class ConcurrentTestUtilsTest {

    private static final Logger logger = Logger.getLogger(ConcurrentTestUtilsTest.class.getName());

    @Test
    public void testConcurrentExecuteBatch() throws Exception {
        int concurrent = 10;
        logger.log(Level.WARNING, "{0} threads running concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new BatchExecuteThread("batch thread") {

                @Override
                protected boolean runTask() throws Exception {
                    return true;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        assertEquals(concurrent, result);
    }

    @Test
    public void testConcurrentExecuteCyclic() throws Exception {
        int concurrent = 10;
        int loop = 10;
        logger.log(Level.WARNING, "{0} threads running concurrently...", concurrent);
        final CyclicExecuteThread[] threads = new CyclicExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new CyclicExecuteThread("cyclic thread") {

                @Override
                protected boolean runTask() throws Exception {
                    return true;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads, loop);
        assertEquals(concurrent * loop, result);
    }
}
