package com.guiwuu.design.container;

import com.guiwuu.design.ood.dip.KeyboardReader;
import com.guiwuu.design.ood.dip.DisplayWriter;
import com.guiwuu.design.ood.dip.Reader;
import com.guiwuu.design.ood.dip.Writer;
import org.junit.Test;
import static org.junit.Assert.*;

public class CopierContainerTest {

    private CopierContainer container = new CopierContainer();

    @Test
    public void testNewCopier() {
        System.out.println("newCopier");
        Reader reader = new KeyboardReader();
        Writer writer = new DisplayWriter();
        assertNotNull(container.newCopier(reader, writer));
    }

    @Test
    public void testNewCopierByReflect() throws Exception {
        System.out.println("newCopierByReflect");
        String readerName = "com.guiwuu.ood.dip.KeyboardReader";
        String writerName = "com.guiwuu.ood.dip.DisplayWriter";
        assertNotNull(container.newCopierByReflect(readerName, writerName));
    }

    @Test
    public void testNewCopierByServiceLocator() throws Exception {
        System.out.println("newCopierByServiceLocator");
        assertNotNull(container.newCopierByServiceLocator("keyboardReader", "displayWriter"));
    }

    @Test
    public void testNewCopierBySimpleIoc() throws Exception {
        System.out.println("newCopierBySimpleIoc");
        assertNotNull(container.newCopierBySimpleIoc());
    }

    @Test
    public void testNewCopierByGuice() throws Exception {
        System.out.println("newCopierByGuice");
        assertNotNull(container.newCopierByGuice());
    }
}