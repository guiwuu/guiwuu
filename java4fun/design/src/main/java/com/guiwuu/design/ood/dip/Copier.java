package com.guiwuu.design.ood.dip;

import java.io.IOException;

public class Copier {

    private Reader reader;
    private Writer writer;

    public Copier() {
    }

    public Copier(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void copy() throws IOException {
        int ch;
        while ((ch = reader.read()) >= 0) {
            writer.write((char) ch);
        }
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}