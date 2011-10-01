package com.guiwuu.jpractice.ood.ood.dip;

import java.io.IOException;

public class PrinterWriter implements Writer {

    @Override
    public void write(char ch) throws IOException {
        writeToPrinter(ch);
    }

    private void writeToPrinter(char ch) {
        // TODO Auto-generated method stub
    }
}
