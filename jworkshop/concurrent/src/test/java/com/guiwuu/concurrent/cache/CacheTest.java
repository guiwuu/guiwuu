package com.guiwuu.concurrent.cache;

import com.guiwuu.concurrent.util.ConcurrentTestUtils;
import com.guiwuu.concurrent.util.ThreadWrapper;
import java.util.Random;
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
    public void testCache() throws Exception {
        int concurrent = 1000;
        final CostyEcho costyEcho = new CostyEcho();
        logger.log(Level.CONFIG, "%s threads running concurrently...", concurrent);
        final ThreadWrapper[] threads = new ThreadWrapper[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new ThreadWrapper("test run thread") {

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

    class CostyEcho {

        @Cache
        int echo(int input) throws Exception {
            Thread.sleep(random.nextInt(100));
            return input;
        }
    }
}
