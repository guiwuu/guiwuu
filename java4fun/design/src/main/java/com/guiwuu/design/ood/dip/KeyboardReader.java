package com.guiwuu.design.ood.dip;

import java.io.IOException;

public class KeyboardReader implements Reader {

    @Override
    public int read() throws IOException {
        return System.in.read();
    }
}
