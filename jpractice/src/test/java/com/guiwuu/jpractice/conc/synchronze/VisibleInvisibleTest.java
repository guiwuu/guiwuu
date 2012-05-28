package com.guiwuu.jpractice.conc.synchronze;

import com.guiwuu.jpractice.conc.synchronize.VisibleAppConfig;
import com.guiwuu.jpractice.conc.synchronize.InvisibleAppConfig;
import com.guiwuu.jpractice.conc.util.ConcurrentTestUtils;
import com.guiwuu.jpractice.conc.util.CyclicExecuteThread;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Add class description here
 *
 * @author diancang
 * @since 2012-05-26
 */
public class VisibleInvisibleTest {

    private static final Logger logger = Logger.getLogger(VisibleInvisibleTest.class.getName());

    @Test
    public void concurrentUpdateAppConfig() throws Exception {
        final VisibleAppConfig visibleAppConfig = new VisibleAppConfig();
        final InvisibleAppConfig invisibleAppConfig = new InvisibleAppConfig();

        int concurrent = 100;
        int loop = 10000;
        logger.log(Level.WARNING, "{0} threads begin to run {1} times concurrently...", new Object[]{concurrent, loop});
        final CyclicExecuteThread[] threads = new CyclicExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new CyclicExecuteThread("thread " + i) {

                @Override
                protected boolean runTask() throws Exception {
                    invisibleAppConfig.update();
                    visibleAppConfig.update();
                    return true;
                }
            };
        }

        assertTrue(ConcurrentTestUtils.run(threads, loop));
        String format = "%s, %s";
        System.out.println("total => " + concurrent * loop);
        System.out.println("invisible primitive int => " + invisibleAppConfig.i);
        System.out.println("invisible object reference => " + invisibleAppConfig.j);
        System.out.println("volatile primitive int => " + visibleAppConfig.i);
        System.out.println("atomic object reference => " + visibleAppConfig.j);
    }
}
