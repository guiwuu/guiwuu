package com.guiwuu.jvm.gc;

import java.util.Random;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class ReferenceTest {

    @Test
    public void testReferenceBasics() throws Exception {
        Object strong = new Object();
        assertNotNull(strong);

        SoftReference soft = new SoftReference(new Object());
        assertNotNull(soft.get());

        WeakReference weak = new WeakReference(new Object());
        assertNotNull(weak.get());

        // try best to invoke gc
        for (int i = 0; i < 100; i++) {
            System.gc();
        }

        assertNotNull(soft.get());
        assertNull(weak.get());

        ReferenceQueue referenceQueue = new ReferenceQueue();
        PhantomReference phantom = new PhantomReference(new Object(), referenceQueue);
        assertNull(phantom.get());
    }

    @Test
    public void testSoftReference() throws Exception {
        SoftReference soft = new SoftReference(new Object());
        assertNotNull(soft.get());

        // try best to invoke gc
        for (int i = 0; i < 100; i++) {
            System.gc();
        }

        assertNotNull(soft.get());

        // try best to invoke oom
        Random r;
        for (int i = 0; i < 100000; i++) {
            r = new Random();
        }
        assertNotNull(soft.get());
    }
}
