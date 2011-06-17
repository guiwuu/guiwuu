package com.guiwuu.jumd;

import java.util.ArrayList;
import java.util.List;

/**
 * Umd file
 *
 * @author daijun
 * @since 2011-02-12
 * @version 2011-02-19
 */
public class Umd {

    public static final byte[] BEGIN = {(byte) 0x89, (byte) 0x9b, (byte) 0x9a, (byte) 0xde};
    public static final byte[] END = {(byte) 0x23, (byte) 0x0C, 0, (byte) 0x01, (byte) 0x09};
    private UmdHeader header = new UmdHeader();
    private List<UmdChapter> chapters = new ArrayList<UmdChapter>();
    private UmdCover cover = new UmdCover();

    public UmdHeader getHeader() {
        return header;
    }

    public void setHeader(UmdHeader header) {
        this.header = header;
    }

    public UmdCover getCover() {
        return cover;
    }

    public void setCover(UmdCover cover) {
        this.cover = cover;
    }

    public UmdChapter getChapter(int index) {
        return chapters.get(index);
    }

    public void addChapter(String title, byte[] content) {
        chapters.add(new UmdChapter(title, content));
    }

    public void addChapter(UmdChapter chapter) {
        chapters.add(chapter);
    }

    public int sizeOfChapters() {
        return chapters.size();
    }
}
