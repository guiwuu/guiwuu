package com.guiwuu.concurrent.cache;

import com.guiwuu.concurrent.util.ConcurrentTestUtils;
import com.guiwuu.concurrent.util.BatchExecuteThread;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class ComputingCacheTest {

    private static final Logger logger = Logger.getLogger(ComputingCacheTest.class.getName());
    private static final Random random = new Random();
    private static final int concurrent = 100;
    private static final int loop = 100;
    private static final CostyEcho costyEcho = new CostyEcho();
    private static final int[] inputs = new int[concurrent * loop];

    @BeforeClass
    public static void setUpClass() throws Exception {
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = random.nextInt(25);
        }
    }

    @Test
    @Ignore
    public void testNoCache() throws Exception {
        logger.log(Level.WARNING, "{0} no cache threads running concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new LoopThreadWrapper("no cache thread", i) {

                @Override
                protected boolean runTask() throws Exception {
                    boolean result = true;
                    for (int j = 0; j < loop; j++) {
                        if (costyEcho.echo(inputs[num * loop + j]) == null) {
                            result = false;
                        }
                    }
                    return result;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        assertEquals(concurrent, result);
    }

    @Test
    public void testDummyCache() throws Exception {
        final ComputingCache<Integer, Integer> cache = new DummyComputingCache<Integer, Integer>(new Computable<Integer, Integer>() {

            @Override
            public Integer compute(Integer input) throws Exception {
                return costyEcho.echo(input);
            }
        });
        cache.setMaxSize(20);
        cache.setSwapSize(5);
        logger.log(Level.WARNING, "{0} dummy cache threads running concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new LoopThreadWrapper("dummy cache thread", i) {

                @Override
                protected boolean runTask() throws Exception {
                    boolean result = true;
                    for (int j = 0; j < loop; j++) {
                        if (cache.compute(inputs[num * loop + j]) == null) {
                            result = false;
                        }
                    }
                    return result;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    @Test
    public void testFifoCache() throws Exception {
        final ComputingCache<Integer, Integer> cache = new FifoComputingCache<Integer, Integer>(new Computable<Integer, Integer>() {

            @Override
            public Integer compute(Integer input) throws Exception {
                return costyEcho.echo(input);
            }
        });
        cache.setMaxSize(20);
        cache.setSwapSize(5);
        logger.log(Level.WARNING, "{0} fifo cache threads running concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new LoopThreadWrapper("fifo cache thread", i) {

                @Override
                protected boolean runTask() throws Exception {
                    boolean result = true;
                    for (int j = 0; j < loop; j++) {
                        if (cache.compute(inputs[num * loop + j]) == null) {
                            result = false;
                        };
                    }
                    return result;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    abstract class LoopThreadWrapper extends BatchExecuteThread {

        protected int num;

        public LoopThreadWrapper(String name, int num) {
            super(name);
            this.num = num;
        }
    }

    static class CostyEcho {

        @Cache
        synchronized Integer echo(Integer input) throws Exception {
            Thread.sleep(6);
            return input;
        }
    }
}
