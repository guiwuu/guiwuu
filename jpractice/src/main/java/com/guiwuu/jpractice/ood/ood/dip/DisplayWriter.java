package com.guiwuu.jpractice.ood.ood.dip;

import java.io.IOException;

public class DisplayWriter implements Writer {

    @Override
    public void write(char ch) throws IOException {
        System.out.print(ch);
    }
}
