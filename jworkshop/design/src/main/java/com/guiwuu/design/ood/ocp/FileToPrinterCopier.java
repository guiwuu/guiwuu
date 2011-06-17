package com.guiwuu.design.ood.ocp;

public class FileToPrinterCopier extends AbstractCopier {

    @Override
    protected int read() throws Exception {
        return readFromFile();
    }

    private int readFromFile() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected void write(char ch) throws Exception {
        writeToPrinter(ch);
    }

    private void writeToPrinter(char ch) {
        // TODO Auto-generated method stub
    }
}
