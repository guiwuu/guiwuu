package com.guiwuu.design.ood.dip;

import java.io.IOException;

public class FileReader implements Reader {

    @Override
    public int read() throws IOException {
        return readFromFile();
    }

    private int readFromFile() {
        // TODO Auto-generated method stub
        return 0;
    }
}
