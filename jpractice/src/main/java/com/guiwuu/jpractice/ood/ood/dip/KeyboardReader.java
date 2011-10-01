package com.guiwuu.jpractice.ood.ood.dip;

import java.io.IOException;

public class KeyboardReader implements Reader {

    @Override
    public int read() throws IOException {
        return System.in.read();
    }
}
