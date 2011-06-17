package com.guiwuu.jumd.util;

import static org.junit.Assert.*;

import com.guiwuu.jumd.Umd;
import com.guiwuu.jumd.UmdCover;
import com.guiwuu.jumd.UmdHeader;
import org.junit.Test;
import java.io.File;

/**
 * UmdParserTest
 *
 * @author daijun
 * @since 2011-02-20
 * @version 2011-02-22
 */
public class UmdWriterTest {

    @Test
    public void testConvert() throws Exception {
        System.out.println("convert...");
        Umd umd = new Umd();
        umd.getHeader().setTitle("title");
        byte[] content = UmdUtil.unicode("content");
        umd.addChapter("chapter", content);
        UmdCover cover = new UmdCover(UmdUtil.genImage("cover", 120, 80));
        umd.setCover(cover);
        File txt = new File("D:\\downloads\\convert.txt");
        UmdWriter writer = new UmdWriter(umd);
        writer.convertToTxt(txt);
    }

    @Test
    public void testReadToConvert() throws Exception {
        System.out.println("read...");
        File in = new File("D:\\downloads\\test.umd");
        Umd expected = new UmdReader(in).read();

        System.out.println("convert...");
        File test = new File("D:\\downloads\\test.txt");
        UmdWriter write = new UmdWriter(expected);
        write.convertToTxt(test);
    }

    @Test
    public void testExtract() throws Exception {
        System.out.println("extract...");
        Umd umd = new Umd();
        umd.getHeader().setTitle("title");
        byte[] content = UmdUtil.unicode("content");
        umd.addChapter("chapter", content);
        UmdCover cover = new UmdCover(UmdUtil.genImage("cover", 120, 80));
        umd.setCover(cover);
        File out = new File("D:\\downloads\\extract");
        UmdWriter writer = new UmdWriter(umd);
        writer.extractToFolder(out);
    }

    @Test
    public void testReadToExtract() throws Exception {
        System.out.println("read...");
        File in = new File("D:\\downloads\\test.umd");
        Umd expected = new UmdReader(in).read();

        System.out.println("extract...");
        File test = new File("D:\\downloads\\test");
        UmdWriter write = new UmdWriter(expected);
        write.extractToFolder(test);
    }

    @Test
    public void testWrite() throws Exception {
        System.out.println("write...");
        Umd umd = new Umd();
        umd.getHeader().setTitle("title");
        byte[] content = UmdUtil.unicode("content");
        umd.addChapter("chapter", content);
        UmdCover cover = new UmdCover(UmdUtil.genImage("cover", 120, 80));
        umd.setCover(cover);
        File out = new File("D:\\downloads\\out.umd");
        UmdWriter writer = new UmdWriter(umd);
        writer.writeToUmd(out);
        byte[] bytes = UmdUtil.readFile(out);
        assertEquals(1121, bytes.length);
    }

    @Test
    public void testReadToWrite() throws Exception {
        System.out.println("read...");
        File in = new File("D:\\downloads\\test.umd");
        Umd expected = new UmdReader(in).read();

        System.out.println("write...");
        File out = new File("D:\\downloads\\actual.umd");
        UmdWriter write = new UmdWriter(expected);
        write.writeToUmd(out);

        System.out.println("read again...");
        Umd actual = new UmdReader(out).read();
        UmdHeader expedtedHeader = expected.getHeader();
        UmdHeader actualHeader = actual.getHeader();
        assertEquals(expedtedHeader.getTitle(), actualHeader.getTitle());
        assertEquals(expedtedHeader.getAuthor(), actualHeader.getAuthor());
        assertEquals(expedtedHeader.getDay(), actualHeader.getDay());
        assertEquals(expedtedHeader.getYear(), actualHeader.getYear());
        assertEquals(expedtedHeader.getMonth(), actualHeader.getMonth());
        assertEquals(expedtedHeader.getContentSize(), actualHeader.getContentSize());
        assertEquals(expedtedHeader.getDocType(), actualHeader.getDocType());
        assertEquals(expedtedHeader.getGender(), actualHeader.getGender());
        assertEquals(expedtedHeader.getPublisher(), actualHeader.getPublisher());
        assertEquals(expedtedHeader.getVendor(), actualHeader.getVendor());
        assertArrayEquals(expedtedHeader.getLicense(), actualHeader.getLicense());
        assertEquals(expected.sizeOfChapters(), actual.sizeOfChapters());
        for (int i = 0; i < expected.sizeOfChapters(); i++) {
            assertEquals(expected.getChapter(i).getTitle(), actual.getChapter(i).getTitle());
            assertArrayEquals(expected.getChapter(i).getContent(), actual.getChapter(i).getContent());
        }
        assertArrayEquals(expected.getCover().getData(), actual.getCover().getData());
    }
}