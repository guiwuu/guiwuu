package com.guiwuu.concurrent.cache;

import java.io.BufferedReader;
import com.guiwuu.concurrent.util.ConcurrentTestUtils;
import com.guiwuu.concurrent.util.BatchExecuteThread;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class CryptCacheTest {

    private static final Logger logger = Logger.getLogger(CryptCacheTest.class.getName());
    private static final int concurrent = 2;
    private static final Crypt crypt = new Crypt();
    private static BufferedReader encryptFile;
    private static BufferedReader decryptFile;
    private static Computable<String, String> decryptCompute;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        decryptCompute = new Computable<String, String>() {

            @Override
            public String compute(String input) throws Exception {
                if ("null".equals(input)) {
                    return crypt.decrypt(null);
                } else {
                    return crypt.decrypt(input);
                }
            }
        };
    }

    @Before
    public void setUp() throws Exception {
        encryptFile = new BufferedReader(new FileReader("D:/Downloads/tmp/dpd/es.log"));
        decryptFile = new BufferedReader(new FileReader("D:/Downloads/tmp/dpd/ds.log"));
    }

    @Test
    @Ignore
    public void testNoCacheDecrypt() throws Exception {
        logger.log(Level.WARNING, "{0} no cache threads decrypting concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new BatchExecuteThread("no cache decrypt thread") {

                @Override
                protected boolean runTask() throws Exception {
                    try {
                        String input = decryptFile.readLine();
                        while (input != null) {
                            decryptCompute.compute(input);
                            input = decryptFile.readLine();
                        }
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            };
        }
        int result = ConcurrentTestUtils.run(threads);
        assertEquals(concurrent, result);
    }

    @Test
    public void testDummyCacheDecrypt() throws Exception {
        logger.log(Level.WARNING, "{0} dummy cache threads decrypting concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        final ComputingCache<String, String> cache = new DummyComputingCache<String, String>(decryptCompute);
        cache.setMaxSize(10000);
        cache.setSwapSize(1000);
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new DecryptThreadWrapper("no cache decrypt thread", cache);
        }
        int result = ConcurrentTestUtils.run(threads);
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    @Test
    public void testFifoCacheDecrypt() throws Exception {
        logger.log(Level.WARNING, "{0} fifo cache threads decrypting concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        final ComputingCache<String, String> cache = new FifoComputingCache<String, String>(decryptCompute);
        cache.setMaxSize(10000);
        cache.setSwapSize(1000);
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new DecryptThreadWrapper("no cache decrypt thread", cache);
        }
        int result = ConcurrentTestUtils.run(threads);
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    @Test
    public void testLruCacheDecrypt() throws Exception {
        logger.log(Level.WARNING, "{0} lru cache threads decrypting concurrently...", concurrent);
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        final ComputingCache<String, String> cache = new LruComputingCache<String, String>(decryptCompute);
        cache.setMaxSize(10000);
        cache.setSwapSize(1000);
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new DecryptThreadWrapper("no cache decrypt thread", cache);
        }
        int result = ConcurrentTestUtils.run(threads);
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    static class Crypt {

        synchronized String encrypt(String input) throws Exception {
            Thread.sleep(1);
            return input;
        }

        synchronized String decrypt(String input) throws Exception {
            Thread.sleep(1);
            return input;
        }
    }

    class DecryptThreadWrapper extends BatchExecuteThread {

        private ComputingCache c;

        public DecryptThreadWrapper(String name, ComputingCache c) {
            super(name);
            this.c = c;
        }

        @Override
        protected boolean runTask() throws Exception {
            try {
                String input = decryptFile.readLine();
                while (input != null) {
                    c.compute(input);
                    input = decryptFile.readLine();
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
