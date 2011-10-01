package com.guiwuu.jpractice.ood.ood.ocp;

public class KeyboardToDisplayCopier extends AbstractCopier {

    @Override
    protected int read() throws Exception {
        return System.in.read();
    }

    @Override
    protected void write(char ch) throws Exception {
        System.out.print(ch);
    }
}
