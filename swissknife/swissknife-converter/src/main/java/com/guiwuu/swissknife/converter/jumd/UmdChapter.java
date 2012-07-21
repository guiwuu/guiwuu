package com.guiwuu.swissknife.converter.jumd;

/**
 * UMD Chapter
 *
 * @author daijun
 * @since 2011-02-18
 * @version 2011-02-19
 */
public class UmdChapter {

    private String title;
    private byte[] content;

    public UmdChapter(String title) {
        this.title = title;
    }

    public UmdChapter(String title, byte[] content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
