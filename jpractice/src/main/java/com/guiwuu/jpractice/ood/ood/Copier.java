package com.guiwuu.jpractice.ood.ood;

import java.io.IOException;

public class Copier {

    public static void main(String[] args) throws IOException {
        int ch;
        while ((ch = System.in.read()) >= 0) {
            System.out.print((char) ch);
        }
    }
}