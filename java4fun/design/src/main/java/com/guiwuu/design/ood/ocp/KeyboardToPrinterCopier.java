package com.guiwuu.design.ood.ocp;

public class KeyboardToPrinterCopier extends AbstractCopier {

    @Override
    protected int read() throws Exception {
        return System.in.read();
    }

    @Override
    protected void write(char ch) throws Exception {
        writeToPrinter(ch);
    }

    private void writeToPrinter(char ch) {
        // TODO Auto-generated method stub
    }
}
