package com.guiwuu.jpractice.ood.ood.compose;

import java.io.IOException;

public abstract class KeyboardToPrinterCopier {

    private KeyboardReader keyboard = new KeyboardReader();
    private PrinterWriter printer = new PrinterWriter();

    public void copy() throws IOException {
        int ch;
        while ((ch = keyboard.read()) >= 0) {
            printer.write((char) ch);
        }
    }
}