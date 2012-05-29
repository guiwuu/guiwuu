package com.guiwuu.jpractice.conc.synchronze;

import com.guiwuu.jpractice.conc.synchronize.AppConfig;
import com.guiwuu.jpractice.conc.synchronize.InvisibleAppConfig;
import com.guiwuu.jpractice.conc.synchronize.VisibleAppConfig;
import com.guiwuu.jpractice.conc.util.BatchExecuteThread;
import com.guiwuu.jpractice.conc.util.ConcurrentTestUtils;
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
public class MemoryVisibilityTest {

    private static final Logger logger = Logger.getLogger(MemoryVisibilityTest.class.getName());

    @Test
    public void concurrentUpdateAppConfig() throws Exception {

        final AppConfig visibleAppConfig = new VisibleAppConfig();
        int concurrent = 10;

        final AppConfig invisibleAppConfig = new InvisibleAppConfig();
        final BatchExecuteThread[] invisibleAppConfigUpdateThreads = new BatchExecuteThread[concurrent];
        for (int i = 0; i < concurrent; i++) {
            invisibleAppConfigUpdateThreads[i] = new AppConfigUpdateThread("", invisibleAppConfig);
        }
        new AppConfigDetectThread("", invisibleAppConfig);
        assertTrue(ConcurrentTestUtils.run(invisibleAppConfigUpdateThreads));
    }

    class AppConfigUpdateThread extends BatchExecuteThread {

        private int loop = 0;
        private AppConfig appConfig;

        public AppConfigUpdateThread(String name, AppConfig appConfig) {
            super(name);
            this.appConfig = appConfig;
        }

        @Override
        protected boolean runTask() throws Exception {
            while (!appConfig.isUpdated()) {
                loop++;
            }
            logger.log(Level.WARNING, "{0} detects config updated after {1} loops at {2}", new Object[]{getName(), loop, System.nanoTime()});
            return true;
        }
    }

    class AppConfigDetectThread extends Thread{

        private AppConfig appConfig;

        public AppConfigDetectThread(String name, AppConfig appConfig){
            super(name);
            this.appConfig = appConfig;
        }

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
    }
}
