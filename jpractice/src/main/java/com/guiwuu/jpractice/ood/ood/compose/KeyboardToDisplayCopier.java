package com.guiwuu.jpractice.ood.ood.compose;

import java.io.IOException;

public abstract class KeyboardToDisplayCopier {

    private KeyboardReader keyboard = new KeyboardReader();
    private DisplayWriter display = new DisplayWriter();

    public void copy() throws IOException {
        int ch;
        while ((ch = keyboard.read()) >= 0) {
            display.write((char) ch);
        }
    }
}