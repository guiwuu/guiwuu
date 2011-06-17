package com.guiwuu.design.ood.compose;

import java.io.IOException;

public abstract class FileToDisplayCopier {

    private FileReader file = new FileReader();
    private DisplayWriter display = new DisplayWriter();

    public void copy() throws IOException {
        int ch;
        while ((ch = file.read()) >= 0) {
            display.write((char) ch);
        }
    }
}