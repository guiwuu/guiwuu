package com.guiwuu.concurrent.cache;

import java.io.BufferedReader;
import com.guiwuu.concurrent.util.ConcurrentTestUtils;
import com.guiwuu.concurrent.util.ThreadWrapper;
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
    private static final int concurrent = 1;
    private static final Crypt crypt = new Crypt();
    private static BufferedReader encryptFile;
    private static BufferedReader decryptFile;

    @Before
    public void setUpClass() throws Exception {
        encryptFile = new BufferedReader(new FileReader("D:/Downloads/tmp/dpd/es.log"));
        decryptFile = new BufferedReader(new FileReader("D:/Downloads/tmp/dpd/ds.log"));
    }

    @Test
    public void testNoCacheDecrypt() throws Exception {
        logger.log(Level.WARNING, "{0} no cache threads decrypting concurrently...", concurrent);
        final ThreadWrapper[] threads = new ThreadWrapper[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new ThreadWrapper("no cache decrypt thread", i) {

                @Override
                protected boolean runTask() throws Exception {
                    try {
                        String input = decryptFile.readLine();
                        while (input != null) {
                            if ("null".equals(input)) {
                                crypt.decrypt(null);
                            } else {
                                crypt.decrypt(input);
                            }
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
        final ThreadWrapper[] threads = new ThreadWrapper[concurrent];
        final ComputingCache<String, String> cache = new DummyComputingCache<String, String>(new Computable<String, String>() {

            @Override
            public String compute(String input) throws Exception {
                if ("null".equals(input)) {
                    return crypt.decrypt(null);
                } else {
                    return crypt.decrypt(input);
                }
            }
        });
        cache.setMaxSize(10000);
        cache.setSwapSize(1000);
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new ThreadWrapper("no cache decrypt thread", i) {

                @Override
                protected boolean runTask() throws Exception {
                    try {
                        String input = decryptFile.readLine();
                        while (input != null) {
                            cache.compute(input);
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
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    @Test
    public void testFifoCacheDecrypt() throws Exception {
        logger.log(Level.WARNING, "{0} fifo cache threads decrypting concurrently...", concurrent);
        final ThreadWrapper[] threads = new ThreadWrapper[concurrent];
        final ComputingCache<String, String> cache = new FifoComputingCache<String, String>(new Computable<String, String>() {

            @Override
            public String compute(String input) throws Exception {
                if ("null".equals(input)) {
                    return crypt.decrypt(null);
                } else {
                    return crypt.decrypt(input);
                }
            }
        });
        cache.setMaxSize(10000);
        cache.setSwapSize(1000);
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new ThreadWrapper("no cache decrypt thread", i) {

                @Override
                protected boolean runTask() throws Exception {
                    try {
                        String input = decryptFile.readLine();
                        while (input != null) {
                            cache.compute(input);
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
        logger.log(Level.WARNING, cache.toString());
        assertEquals(concurrent, result);
    }

    static class Crypt {

        synchronized String encrypt(String input) throws Exception {
            //Thread.sleep(6);
            return input;
        }

        synchronized String decrypt(String input) throws Exception {
            Thread.sleep(1);
            return input;
        }
    }
}
