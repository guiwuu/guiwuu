package com.guiwuu.concurrent.cache;

import java.util.concurrent.FutureTask;
import com.guiwuu.concurrent.util.ConcurrentTestUtils;
import com.guiwuu.concurrent.util.ThreadWrapper;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class CacheTest {

    private static final Logger logger = Logger.getLogger(CacheTest.class.getName());
    private static final Random random = new Random();

    @Test
    public void testNoCache() throws Exception {
        int concurrent = 1000;
        final CostyEcho costyEcho = new CostyEcho();
        logger.log(Level.WARNING, "{0} no cache threads running concurrently...", concurrent);
        final ThreadWrapper[] threads = new ThreadWrapper[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new ThreadWrapper("no cache thread") {

                @Override
                protected boolean runTask() throws Exception {
                    int input = random.nextInt(25);
                    int output = costyEcho.echo(input);
                    return input == output;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        assertEquals(concurrent, result);
    }

    @Test
    public void testCache() throws Exception {
        int concurrent = 1000;
        final CostyEcho costyEcho = new CostyEcho();
        final BoundedCache<Integer, Integer> cache = new BoundedCache<Integer, Integer>();
        logger.log(Level.WARNING, "{0} cache threads running concurrently...", concurrent);
        final ThreadWrapper[] threads = new ThreadWrapper[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new ThreadWrapper("cache thread") {

                @Override
                protected boolean runTask() throws Exception {
                    final int input = random.nextInt(25);
                    Integer output = cache.get(input);
                    if (output == null) {// cache missing
                        FutureTask ft = new FutureTask(new Callable<Integer>() {

                            public Integer call() throws Exception {
                                return costyEcho.echo(input);
                            }
                        });
                        output = cache.put(input, ft);
                    }
                    return input == output;
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    class CostyEcho {

        @Cache
        synchronized int echo(int input) throws Exception {
            Thread.sleep(10);
            return input;
        }
    }
}
