package com.guiwuu.jpractice.ood.ood.compose;

import java.io.IOException;

public class KeyboardReader extends KeyboardToDisplayCopier {

    public int read() throws IOException {
        return System.in.read();
    }
}