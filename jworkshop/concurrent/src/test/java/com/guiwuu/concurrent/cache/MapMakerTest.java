package com.guiwuu.concurrent.cache;

import com.google.common.collect.MapMaker;
import java.util.concurrent.ConcurrentMap;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guiwuu
 */
public class MapMakerTest {

    @Test
    public void testKeyRef() throws Exception {
        String str1 = new StringBuilder().toString();
        String str2 = new StringBuilder().toString();
        assertEquals(str1, str2);
        assertNotSame(str1, str2);

        ConcurrentMap<String, Boolean> strongKeyMap = new MapMaker().makeMap();
        strongKeyMap.put(str1, true);
        assertNotNull(strongKeyMap.get(str2));

        ConcurrentMap<String, Boolean> softKeyMap = new MapMaker().softKeys().makeMap();
        softKeyMap.put(str1, true);
        softKeyMap.put(str1.intern(), true);
        assertNull(softKeyMap.get(str2));
        assertNotNull(softKeyMap.get(str2.intern()));

        ConcurrentMap<String, Boolean> weakKeyMap = new MapMaker().weakKeys().makeMap();
        weakKeyMap.put(str1, true);
        weakKeyMap.put(str1.intern(), true);
        assertNull(weakKeyMap.get(str2));
        assertNotNull(weakKeyMap.get(str2.intern()));
    }

}
