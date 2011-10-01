package com.guiwuu.jpractice.conc.util;

import java.util.concurrent.atomic.AtomicInteger;
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
        assertTrue(ConcurrentTestUtils.run(threads));
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
        assertTrue(ConcurrentTestUtils.run(threads, loop));
    }
    
    @Test
    public void testAbstractConcurrentTest() throws Exception{
        AbstractConcurrentTest act = new AbstractConcurrentTest(){
            private AtomicInteger i = new AtomicInteger();

            @Override
            public boolean doTest() throws Exception {
                System.out.println(i.incrementAndGet());
                return true;
            }
            
        };
        assertTrue(act.run(10, 10));
    }
}
