package com.guiwuu.design.ood.compose;

import java.io.IOException;

public abstract class FileToPrinterCopier {

    private FileReader file = new FileReader();
    private PrinterWriter printer = new PrinterWriter();

    public void copy() throws IOException {
        int ch;
        while ((ch = file.read()) >= 0) {
            printer.write((char) ch);
        }
    }
}