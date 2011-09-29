package com.guiwuu.design.ood.ocp;

public abstract class AbstractCopier {

    public void copy() throws Exception {
        int ch;
        while ((ch = read()) >= 0) {
            write((char) ch);
        }
    }

    protected abstract int read() throws Exception;

    protected abstract void write(char ch) throws Exception;
}