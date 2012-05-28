package com.guiwuu.jpractice.conc.synchronze;

import com.guiwuu.jpractice.conc.synchronize.AppConfig;
import com.guiwuu.jpractice.conc.synchronize.VisibleAppConfig;
import com.guiwuu.jpractice.conc.synchronize.InvisibleAppConfig;
import com.guiwuu.jpractice.conc.util.BatchExecuteThread;
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
        final AppConfig appConfig = new VisibleAppConfig();
        int concurrent = 10;
        final BatchExecuteThread[] threads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            threads[i] = new BatchExecuteThread("app config detecting thread " + i) {
                private int loop = 0;

                @Override
                protected boolean runTask() throws Exception {
                    while (!appConfig.isUpdated()) {
                        loop++;
                    }
                    logger.log(Level.WARNING, "{0} detects config updated after {1} loops at {2}", new Object[]{getName(), loop, System.nanoTime()});
                    return true;
                }
            };
        }

        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, this + "error occurs", e);
                }
                appConfig.update();
                logger.log(Level.WARNING, "updated at {0}", System.nanoTime());
            }
        }.start();
        assertTrue(ConcurrentTestUtils.run(threads));
    }
}
